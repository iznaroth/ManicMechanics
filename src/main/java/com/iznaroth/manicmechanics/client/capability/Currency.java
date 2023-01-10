package com.iznaroth.manicmechanics.client.capability;

import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.client.gui.GuiCurrencyHUD;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class Currency implements ICurrency {
    private int mdd = 0;

    @Override
    public int getCurrentBalance() {
        return mdd;
    }

    @Override
    public int setBalance(int mdd) {
        this.mdd = mdd;

        return this.getCurrentBalance();
    }

    @Override
    public int addCurrency(int mddToAdd) {
        this.setBalance(this.getCurrentBalance() + mddToAdd);
        GuiCurrencyHUD.triggerChangeCountdown(mddToAdd, true);
        return this.getCurrentBalance();
    }

    @Override
    public int removeCurrency(int mddToRemove) {
        if(mddToRemove < 0)
            mddToRemove = 0;
        GuiCurrencyHUD.triggerChangeCountdown(mddToRemove, false);
        this.setBalance(this.getCurrentBalance() - mddToRemove);
        return this.getCurrentBalance();
    }

    @Override
    public void copyFrom(ICurrency other){
        this.mdd = other.getCurrentBalance();
    }

    @Override
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mdd", mdd);
    }

    @Override
    public void loadNBTData(CompoundTag nbt){
        mdd = nbt.getInt("mdd");
    }

}
