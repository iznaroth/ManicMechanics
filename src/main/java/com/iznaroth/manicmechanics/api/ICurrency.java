package com.iznaroth.manicmechanics.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

public interface ICurrency {

    int getCurrentBalance();

    int setBalance(final int mdd);

    int addCurrency(final int mddToAdd);

    int removeCurrency(final int mddToRemove);

    void saveNBTData(CompoundTag nbt);

    void loadNBTData(CompoundTag nbt);

    void copyFrom(ICurrency source);

}
