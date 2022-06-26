package com.iznaroth.industrizer.capability.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCurrencyProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {

    @CapabilityInject(PlayerCurrencyProvider.class)
    public static Capability<PlayerCurrency> PLAYER_CURRENCY_CAPABILITY = null;

    private PlayerCurrency PlayerCurrency = null;
    private final LazyOptional<PlayerCurrency> opt = LazyOptional.of(this::createPlayerCurrency);

    @Nonnull
    private PlayerCurrency createPlayerCurrency() {
        if (PlayerCurrency == null) {
            PlayerCurrency = new PlayerCurrency();
        }
        return PlayerCurrency;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == PLAYER_CURRENCY_CAPABILITY) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        createPlayerCurrency().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        createPlayerCurrency().loadNBTData(nbt);
    }
}
