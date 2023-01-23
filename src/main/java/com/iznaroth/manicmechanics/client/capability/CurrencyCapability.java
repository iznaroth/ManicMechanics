package com.iznaroth.manicmechanics.client.capability;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.api.ICurrency;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CurrencyCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    //@AutoRegisterCapability(ICurrency.class)
    public static final Capability<ICurrency> CURRENCY_CAPABILITY = CapabilityManager.get(new CapabilityToken<ICurrency>() { });

    public static final Direction DEFAULT_FACING = null;

    private ICurrency currency = null;

    private final LazyOptional<ICurrency> optional = LazyOptional.of(this::createCurrency);

    private ICurrency createCurrency(){
        if(this.currency == null){
            this.currency = new Currency();
        }

        return this.currency;
    }

    public static final ResourceLocation ID = new ResourceLocation(ManicMechanics.MOD_ID, "currency");

    public static LazyOptional<ICurrency> getBalance(final LivingEntity entity){
        return entity.getCapability(CURRENCY_CAPABILITY, DEFAULT_FACING);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
        if(cap == CURRENCY_CAPABILITY){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCurrency().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCurrency().loadNBTData(nbt);
    }



}
