package com.iznaroth.industrizer.capability;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageWrapper extends EnergyStorage {
    public EnergyStorageWrapper(int capacity) {
        super(capacity);
    }

    public EnergyStorageWrapper(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EnergyStorageWrapper(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EnergyStorageWrapper(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergy(int to){
        this.energy = to;
    }

    public void energyOperation(int tickAmount){
        //Non-checked removal of power.
        energy = energy - tickAmount;
    }
}
