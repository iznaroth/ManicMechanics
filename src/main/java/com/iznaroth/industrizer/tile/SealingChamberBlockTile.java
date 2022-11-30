package com.iznaroth.industrizer.tile;

import com.iznaroth.industrizer.capability.EnergyStorageWrapper;
import com.iznaroth.industrizer.networking.IndustrizerMessages;
import com.iznaroth.industrizer.networking.packet.EnergySyncS2CPacket;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SealingChamberBlockTile extends TileEntity implements IItemHandler {

    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    private ItemStackHandler itemHandler = createHandler();

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);


    private EnergyStorageWrapper energyStorage = createStorage(0);

    private LazyOptional<IEnergyStorage> storage = LazyOptional.of(() -> energyStorage);

    private int counter;

    public SealingChamberBlockTile() {
        super(IndustrizerTileEntities.SEALER_TILE.get());
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
    public void load(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        counter = tag.getInt("counter");
        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("counter", counter);
        return super.save(tag);
    }

    //TODO - Check if it is acceptable to deprecate this and just implement IItemHandler
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

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

            public void onEnergyChanged(){
                setChanged();
                System.out.println("CREATE PACKET ---------- ENERGY CHANGED ----------- UPDATE SCREEN");
                IndustrizerMessages.sendToClients(new EnergySyncS2CPacket(this.getEnergyStored(), getBlockPos()));
            }
        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return storage.cast();
        }
        return super.getCapability(cap, side);
    }

    public void sell(){

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


}
