package com.iznaroth.manicmechanics.capability.data;

import com.iznaroth.manicmechanics.IndustrizerMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.common.capabilities.

public class CurrencyEvents {
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof PlayerEntity) {
            if (!event.getObject().getCapability(PlayerCurrencyProvider.PLAYER_CURRENCY_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(IndustrizerMod.MOD_ID, "playercurrency"), new PlayerCurrencyProvider());
            }
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // We need to copyFrom the capabilities
            event.getOriginal().getCapability(PlayerCurrencyProvider.PLAYER_CURRENCY_CAPABILITY).ifPresent(oldStore -> {
                event.getPlayer().getCapability(PlayerCurrencyProvider.PLAYER_CURRENCY_CAPABILITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerCurrency.class);
    }

    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        // Don't do anything client side
        if (event.world.isClientSide) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        CurrencyManager manager = CurrencyManager.get(event.world);
        manager.tick(event.world);
    }
}
