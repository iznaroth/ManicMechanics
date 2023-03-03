package com.iznaroth.manicmechanics.events;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.MMEntityTypes;
import com.iznaroth.manicmechanics.entity.custom.CopCarEntity;
import com.iznaroth.manicmechanics.entity.custom.CrystalWarlockEntity;
import com.iznaroth.manicmechanics.entity.custom.GridSkaterEntity;
import com.iznaroth.manicmechanics.entity.custom.PinchEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManicMechanics.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(MMEntityTypes.COP_CAR.get(), CopCarEntity.setCustomAttributes());
        event.put(MMEntityTypes.PINCH.get(), PinchEntity.setCustomAttributes());
        event.put(MMEntityTypes.GRID_SKATER.get(), GridSkaterEntity.setAttributes());
        event.put(MMEntityTypes.CRYSTAL_WARLOCK.get(), CrystalWarlockEntity.setAttributes());
    }


}