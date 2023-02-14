package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.blockentity.interfaces.IHasCyclableButton;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasCraftProgress;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasEnergyStorage;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasInvHandler;
import com.iznaroth.manicmechanics.client.capability.EnergyStorageWrapper;
import com.iznaroth.manicmechanics.menu.AssemblerBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.EnergySyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.FluidSyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ItemStackSyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ProgressSyncS2CPacket;
import com.iznaroth.manicmechanics.recipe.AssemblerRecipe;
import com.iznaroth.manicmechanics.util.NonItemRecipeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class AssemblerBlockEntity extends BlockEntity implements IHasInvHandler, IHasEnergyStorage, IHasCraftProgress, MenuProvider {


    int progress = 0;

    private final ItemStackHandler itemHandler = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                MMMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) { //For later
                default -> super.isItemValid(slot, stack);
            };
        }
    };


    private EnergyStorageWrapper energyStorage = new EnergyStorageWrapper(40000, 1000, 0, 0){
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
            MMMessages.sendToClients(new EnergySyncS2CPacket(this.getEnergyStored(), getBlockPos()));
        }
    };


    protected final ContainerData data;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    private int counter;

    public AssemblerBlockEntity(BlockPos pos, BlockState state) {
        super(MMBlockEntities.ASSEMBLER_BE.get(), pos, state);

        this.data = new ContainerData() { //For passing any important info through to the Menu on creation.
            @Override
            public int get(int p_39284_) {
                return 0;
            }

            @Override
            public void set(int p_39285_, int p_39286_) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
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



    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }

        return super.getCapability(cap, side);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }



    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    public void setEnergy(int to){
        this.energyStorage.setEnergy(to);
    }


    public static void craft(AssemblerBlockEntity pEntity){


        SimpleContainer inv = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i = 0; i < pEntity.itemHandler.getSlots(); i++){
            inv.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        System.out.println("Ticking for craft in assembler");

        //TODO - add support for vanilla recipes
        //Optional<CraftingRecipe> recipe = pEntity.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, new CraftingContainer(), pEntity.level);

        Optional<AssemblerRecipe> recipe = NonItemRecipeHelper.getAssemblerRecipeFor(inv, pEntity.level, pEntity);

        recipe.ifPresent(iRecipe -> {

            System.out.println("Recipe works.");

            if(pEntity.itemHandler.getStackInSlot(10).getCount() >= pEntity.itemHandler.getSlotLimit(10) || (!pEntity.itemHandler.getStackInSlot(10).getItem().equals(iRecipe.getResultItem().getItem()) && !pEntity.itemHandler.getStackInSlot(10).isEmpty())){
                System.out.println("No room.");
                return; //Can't perform the craft, slot is full or holds a different itemstack.
            }

            if(pEntity.progress < 120){ //Only increment progress if we can take energy.
                if(pEntity.energyStorage.getEnergyStored() > 0) { //gotta double-nest since prog. is a hard requirement
                    pEntity.progress++;
                    MMMessages.sendToClients(new ProgressSyncS2CPacket(pEntity.progress, pEntity.getBlockPos()));
                    pEntity.energyStorage.energyOperation(4);
                }
            } else {
                System.out.println("Progress hit " + pEntity.progress + ", crafting.");

                ItemStack output = iRecipe.getResultItem();

                pEntity.craftTheItem(output, iRecipe.getQuantities());

                pEntity.setChanged();

                pEntity.progress = 0;
                MMMessages.sendToClients(new ProgressSyncS2CPacket(pEntity.progress, pEntity.getBlockPos()));
            }
        });

    }



    private void craftTheItem(ItemStack output, int[] quantities){

        itemHandler.extractItem(1, quantities[0], false);
        itemHandler.extractItem(2, quantities[1], false);
        itemHandler.extractItem(3, quantities[2], false);
        itemHandler.extractItem(4, quantities[3], false);
        itemHandler.extractItem(5, quantities[4], false);
        itemHandler.extractItem(6, quantities[5], false);
        itemHandler.extractItem(7, quantities[6], false);
        itemHandler.extractItem(8, quantities[7], false);
        itemHandler.extractItem(9, quantities[8], false);
        itemHandler.insertItem(10, output, false);
    }

    public int getCraftProgress(){
        return this.progress;
    }

    @Override
    public void setProgress(int progress){ //For clientside tile update.
        this.progress = progress;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AssemblerBlockEntity pEntity) {
        if(level.isClientSide)
            return;

        craft(pEntity);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.manicmechanics.assembler");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new AssemblerBlockMenu(p_39954_, p_39955_, this, this.data);
    }
}
