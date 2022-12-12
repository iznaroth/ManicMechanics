package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.block.tube.AbstractTubeBlock;
import com.iznaroth.manicmechanics.client.capability.EnergyStorageWrapper;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.menu.InfuserBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.EnergySyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ItemStackSyncS2CPacket;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class InfuserBlockEntity extends UpgradableBlockEntity implements MenuProvider {

    int progress = 0;

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
                case 0 -> stack.getItem() == MMItems.TUBE_HOUSING.get(); //Tagged as INFUSER COMPONENT
                case 1 -> false; //Output slot.
                case 2 -> true; //Any fluid-carrying container.
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

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    private int counter;

    protected final ContainerData data;
    public InfuserBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(MMBlockEntities.INFUSER_BE.get(), p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int getCount() {
                return 2;
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
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
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

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        System.out.println("Creating INFUSER menu from ENTITY");
        return new InfuserBlockMenu(p_39954_, p_39955_, this, this.data);
    }

    public int getCraftProgress(){
        return this.progress;
    }

    public void setProgress(int progress){ //For clientside tile update.
        this.progress = progress;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfuserBlockEntity pEntity) {
        if(level.isClientSide)
            return;
    }
}
