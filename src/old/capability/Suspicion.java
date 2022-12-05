package com.iznaroth.manicmechanics.capability;

import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.api.ISuspicion;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nullable;

public class Suspicion implements ISuspicion {

    private final LivingEntity livingEntity;

    private double val;

    private double cap;


    public Suspicion(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public double getCurrentSuspicion() {
        return val;
    }

    @Override
    public double setSuspicion(double val) {
        this.val = val;

        return this.getCurrentSuspicion();
    }

    @Override
    public double incrementSuspicion(double by) {
        this.setSuspicion(this.getCurrentSuspicion() + by);
        return this.getCurrentSuspicion();
    }

    @Override
    public double decrementSuspicion(double by) {
        if(by < 0)
            by = 0;
        this.setSuspicion(this.getCurrentSuspicion() - by);
        return this.getCurrentSuspicion();
    }

}
