package com.iznaroth.manicmechanics.capability.data;

import net.minecraft.nbt.CompoundNBT;

public class PlayerCurrency {

    private int mdd;

    public int getCurrency() {
        return mdd;
    }

    public void setCurrency(int mana) {
        this.mdd = mdd;
    }

    public void addCurrency(int mana) {
        this.mdd += mdd;
    }

    public void copyFrom(PlayerCurrency source) {
        mdd = source.mdd;
    }


    public void saveNBTData(CompoundNBT compound) {
        compound.putInt("mana", mdd);
    }

    public void loadNBTData(CompoundNBT compound) {
        mdd = compound.getInt("mana");
    }

}
