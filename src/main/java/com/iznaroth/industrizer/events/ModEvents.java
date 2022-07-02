package com.iznaroth.industrizer.events;

import com.iznaroth.industrizer.IndustrizerMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;


@Mod.EventBusSubscriber(modid = IndustrizerMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if(!event.getOriginal().getCommandSenderWorld().isClientSide()) {
            event.getPlayer().getPersistentData().putIntArray(IndustrizerMod.MOD_ID + "homepos",
                    event.getOriginal().getPersistentData().getIntArray(IndustrizerMod.MOD_ID + "homepos"));
        }
    }
}