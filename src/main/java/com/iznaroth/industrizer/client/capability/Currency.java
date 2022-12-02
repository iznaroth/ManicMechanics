package com.iznaroth.industrizer.client.capability;

import com.iznaroth.industrizer.api.ICurrency;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nullable;

public class Currency implements ICurrency {

    private final LivingEntity livingEntity;

    private double mdd;

    private int maxMana;

    private int glyphBonus;

    private int bookTier;


    public Currency(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public double getCurrentBalance() {
        return mdd;
    }

    @Override
    public double setBalance(double mdd) {
        this.mdd = mdd;

        return this.getCurrentBalance();
    }

    @Override
    public double addCurrency(double mddToAdd) {
        this.setBalance(this.getCurrentBalance() + mddToAdd);
        return this.getCurrentBalance();
    }

    @Override
    public double removeCurrency(double mddToRemove) {
        if(mddToRemove < 0)
            mddToRemove = 0;
        this.setBalance(this.getCurrentBalance() - mddToRemove);
        return this.getCurrentBalance();
    }

}
