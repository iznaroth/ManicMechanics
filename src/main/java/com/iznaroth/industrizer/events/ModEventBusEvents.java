package com.iznaroth.industrizer.events;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.entity.ModEntityTypes;
import com.iznaroth.industrizer.entity.custom.CopCarEntity;
import com.iznaroth.industrizer.entity.custom.PinchEntity;
import com.iznaroth.industrizer.item.custom.ModSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IndustrizerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.COP_CAR.get(), CopCarEntity.setCustomAttributes().build());
        event.put(ModEntityTypes.PINCH.get(), PinchEntity.setCustomAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initSpawnEggs();
    }
}