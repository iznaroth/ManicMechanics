package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.client.capability.EnergyStorageWrapper;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.EnergySyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ProgressSyncS2CPacket;
import com.iznaroth.manicmechanics.recipe.SealingChamberRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class SealingChamberBlockEntity extends BlockEntity implements IItemHandler, MenuProvider {

    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    int progress = 0;

    private ItemStackHandler itemHandler = createHandler();

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);


    private EnergyStorageWrapper energyStorage = createStorage(0);

    private LazyOptional<IEnergyStorage> storage = LazyOptional.of(() -> energyStorage);

    private int counter;

    public SealingChamberBlockEntity(BlockPos pos, BlockState state) {
        super(MMBlockEntities.SEALER_TILE.get(), pos, state);
        this.capacity = 40000;
        this.energy = 0;
        //No throttling at this stage of development - will be related to machine casing later.
        this.maxExtract = 0;
        this.maxReceive = 10000;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }


    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        counter = tag.getInt("counter");
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("counter", counter);
        super.saveAdditional(tag);
    }

    //TODO - Check if it is acceptable to deprecate this and just implement IItemHandler
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {

                if(slot == 0 && !stack.getItem().equals(MMItems.TUBE_HOUSING.get())){
                    return stack;
                }

                if(slot == 1 && !stack.getItem().equals(MMItems.SEALANT.get())){
                    return stack;
                }

                if(slot == 2 && !stack.getItem().equals(MMBlocks.TRANSPORT_TUBE.get().asItem())){ //TODO - This slot should be a part of a separate ItemHandler that does not respond to player input.
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private EnergyStorageWrapper createStorage(int startingCharge){
        return new EnergyStorageWrapper(40000, 1000, 0, startingCharge){
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                if (!canReceive())
                    return 0;

                int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
                if (!simulate){
                    energy += energyReceived;
                    onEnergyChanged();
                }

                return energyReceived;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate)
            {
                if (!canExtract())
                    return 0;

                int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
                if (!simulate) {
                    energy -= energyExtracted;
                    onEnergyChanged();
                }

                return energyExtracted;
            }

            @Override
            public int getEnergyStored() {
                return energy;
            }

            @Override
            public int getMaxEnergyStored() {
                return capacity;
            }

            @Override
            public boolean canExtract()
            {
                return this.maxExtract > 0;
            }

            @Override
            public boolean canReceive()
            {
                return this.maxReceive > 0;
            }

            @Override
            public void energyOperation(int tickAmount){
                super.energyOperation(tickAmount);
                onEnergyChanged();
            }

            public void onEnergyChanged(){
                setChanged();
                System.out.println("CREATE PACKET ---------- ENERGY CHANGED ----------- UPDATE SCREEN");
                MMMessages.sendToClients(new EnergySyncS2CPacket(this.getEnergyStored(), getBlockPos()));
            }
        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return storage.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return null;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 0;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return false;
    }

    public void setEnergy(int to){
        this.energyStorage.setEnergy(to);
    }


    public static void craft(SealingChamberBlockEntity pEntity){
        SimpleContainer inv = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i = 0; i < pEntity.itemHandler.getSlots(); i++){
            inv.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<SealingChamberRecipe> recipe = pEntity.level.getRecipeManager()
                .getRecipeFor(SealingChamberRecipe.Type.INSTANCE, inv, pEntity.level);

        recipe.ifPresent(iRecipe -> {

            if(pEntity.itemHandler.getStackInSlot(2).getCount() >= pEntity.itemHandler.getSlotLimit(2)){
                return; //Can't perform the craft, slot is full.
            }

            if(pEntity.progress < 80){ //Only increment progress if we can take energy.
                if(pEntity.energyStorage.getEnergyStored() > 0) { //gotta double-nest since prog. is a hard requirement
                    pEntity.progress++;
                    MMMessages.sendToClients(new ProgressSyncS2CPacket(pEntity.progress, pEntity.getBlockPos()));
                    pEntity.energyStorage.energyOperation(4);
                }
            } else {
                System.out.println("Progress hit " + pEntity.progress + ", crafting.");

                ItemStack output = iRecipe.getResultItem();

                pEntity.craftTheItem(output);

                pEntity.setChanged();

                pEntity.progress = 0;
                MMMessages.sendToClients(new ProgressSyncS2CPacket(pEntity.progress, pEntity.getBlockPos()));
            }
        });
    }

    private void craftTheItem(ItemStack output){
        itemHandler.extractItem(0, 1, false);
        itemHandler.extractItem(1, 1, false);
        itemHandler.insertItem(2, output, false);
    }

    public int getCraftProgress(){
        return this.progress;
    }

    public void setProgress(int progress){ //For clientside tile update.
        this.progress = progress;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SealingChamberBlockEntity pEntity) {
        if(level.isClientSide)
                return;

        pEntity.craft(pEntity);
    }


    @Override
    public Component getDisplayName() {
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return null;
    }
}
