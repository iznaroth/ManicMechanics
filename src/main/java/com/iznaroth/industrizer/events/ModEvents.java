package com.iznaroth.industrizer.events;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.item.IndustrizerItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.crafting.NBTIngredient;
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


    private static final String NBT_KEY = "industrizer.firstjoin";

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        //if (IndustrizerMod.config.isStartingBookDisabled()) {
        //    return;
        //}N


        System.out.println("Player joined.");

        CompoundNBT data = event.getPlayer().getPersistentData();
        CompoundNBT persistent;
        if (!data.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
            data.put(PlayerEntity.PERSISTED_NBT_TAG, (persistent = new CompoundNBT()));
        } else {
            persistent = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        }

        if (!persistent.hasUUID(NBT_KEY)) {
            persistent.putBoolean(NBT_KEY, true);
            System.out.println("Player gets book.");
            event.getPlayer().inventory.add(new ItemStack(IndustrizerItems.ILLEGIBLE_TOME.get()));

        }
    }
}