package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.block.tube.AbstractTubeBlock;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasCyclableButton;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasCraftProgress;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasEnergyStorage;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasInvHandler;
import com.iznaroth.manicmechanics.client.capability.EnergyStorageWrapper;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.logistics.IFluidStorage;
import com.iznaroth.manicmechanics.menu.InfuserBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.EnergySyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.FluidSyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ItemStackSyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ProgressSyncS2CPacket;
import com.iznaroth.manicmechanics.recipe.InfuserRecipe;
import com.iznaroth.manicmechanics.recipe.SealingChamberRecipe;
import com.iznaroth.manicmechanics.util.NonItemRecipeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.util.AbstractTypeVisitor6;
import java.util.Arrays;
import java.util.Optional;

public class InfuserBlockEntity extends BlockEntity implements IHasInvHandler, IHasEnergyStorage, IHasCraftProgress, IHasCyclableButton, MenuProvider {


    int progress = 0;

    public int[] modes = {0, 0, 0};
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                MMMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == MMItems.LUMINOUS_PHOSPHOTHALLITE_MIXTURE.get();
                case 1 -> stack.getItem() == MMItems.NEUTRALIZED_PHOSPHOTHALLITE_MIXTURE.get();
                case 2 -> stack.getItem() == Items.WATER_BUCKET; //NOTE - might screw up recipe
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
            System.out.println("CREATE PACKET ---------- ENERGY CHANGED ----------- UPDATE SCREEN");
            MMMessages.sendToClients(new EnergySyncS2CPacket(this.getEnergyStored(), getBlockPos()));
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                MMMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }


    protected final ContainerData data;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    private LazyOptional<IFluidHandler> lazyFluidStorage = LazyOptional.empty();

    private int counter;

    public InfuserBlockEntity(BlockPos pos, BlockState state) {
        super(MMBlockEntities.INFUSER_BE.get(), pos, state);

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
        if(cap == ForgeCapabilities.FLUID_HANDLER){
            return lazyFluidStorage.cast();
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
        lazyFluidStorage = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
        lazyFluidStorage.invalidate();
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

    public static void checkForBuckets(InfuserBlockEntity pEntity){
        if(pEntity.itemHandler.getStackInSlot(2).getItem().equals(Items.WATER_BUCKET.asItem())){ //TODO - ForgeTags check for fluid bucket
            pEntity.FLUID_TANK.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(Items.BUCKET.asItem(), 1));
        }
    }

    public static void craft(InfuserBlockEntity pEntity){

        SimpleContainer inv = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i = 0; i < pEntity.itemHandler.getSlots(); i++){
            inv.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<InfuserRecipe> recipe = NonItemRecipeHelper.getInfuserRecipeFor(inv, pEntity.level, pEntity);

        recipe.ifPresent(iRecipe -> {

            System.out.println("Recipe works.");

            if(pEntity.itemHandler.getStackInSlot(1).getCount() >= pEntity.itemHandler.getSlotLimit(1) || (!pEntity.itemHandler.getStackInSlot(1).getItem().equals(iRecipe.getResultItem().getItem()) && !pEntity.itemHandler.getStackInSlot(1).getItem().equals(Items.AIR))){
                System.out.println("No room.");
                return; //Can't perform the craft, slot is full or holds a different itemstack.
            }

            if(!pEntity.hasEnoughFluid(iRecipe.getRecipeFluids(), 120 - pEntity.progress, pEntity)){
                System.out.println("Insufficient fluid.");
                return;
            }

            if(pEntity.progress < 120){ //Only increment progress if we can take energy.
                if(pEntity.energyStorage.getEnergyStored() > 0 && pEntity.FLUID_TANK.getFluidAmount() > 0) { //gotta double-nest since prog. is a hard requirement
                    pEntity.progress++;
                    MMMessages.sendToClients(new ProgressSyncS2CPacket(pEntity.progress, pEntity.getBlockPos()));
                    pEntity.energyStorage.energyOperation(4);
                    pEntity.FLUID_TANK.drain(4, IFluidHandler.FluidAction.EXECUTE);
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

    private boolean hasEnoughFluid(NonNullList<Fluid> ingredients, int lengthRemaining, InfuserBlockEntity pEntity){
        System.out.println(ingredients.toString());
        int progForStep; //If this is >0, that's how many ticks for FIRST FLUID. Ex prog 0 = lengthRemaining 120. 120 - 80 = 40, so this batch has 40 ticks left.

        for(int i = 0; i < ingredients.size(); i++){

            if(ingredients.get(i).equals(Fluids.EMPTY)){
                continue; //EMPTY is a flag for a nonfluid operation (dw about it)
            }

            progForStep = lengthRemaining - (40 * (2-i));
            //System.out.println("Checking " + ingredients.get(i) + " against " + pEntity.FLUID_TANK.getFluid().getFluid() + " with amount " + pEntity.FLUID_TANK.getFluid().getAmount() + " , is it not empty? " + !ingredients.get(i).equals(Fluids.EMPTY));
            if(pEntity.FLUID_TANK.getFluid().getFluid() != ingredients.get(i) && !ingredients.get(i).equals(Fluids.EMPTY)){ //4 mb/tick by default, use config
                return false;
            }

            if(progForStep > 0 && pEntity.FLUID_TANK.getFluid().getAmount() < Math.min(progForStep, 40) * 4){ //If the existing number is ever larger than 40, then this step is maxed out. Also forces true for any fluid in a completed step.
                return false;
            }
        }

        return true;
    }

    private void craftTheItem(ItemStack output){
        itemHandler.extractItem(0, 1, false);
        itemHandler.insertItem(1, output, false);
    }

    public int getCraftProgress(){
        return this.progress;
    }

    @Override
    public void setProgress(int progress){ //For clientside tile update.
        this.progress = progress;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfuserBlockEntity pEntity) {
        if(level.isClientSide)
            return;

        checkForBuckets(pEntity);
        craft(pEntity);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.manicmechanics.infuser");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new InfuserBlockMenu(p_39954_, p_39955_, this, this.data);
    }

    public int getModeFor(int whichButton){
        return modes[whichButton];
    }

    @Override
    public void cycleForward(int which) {
        if(modes[which] == 2){
            modes[which] = 0;
        } else {
            modes[which]++;
        }

        System.out.println("Cycle button " + which + " forward to " + modes[which]);
        setChanged();
    }

    @Override
    public void cycleBackward(int which) {
        if(modes[which] == 0){
            modes[which] = 2;
        } else {
            modes[which]--;
        }

        setChanged();
    }
}
