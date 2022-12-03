package com.iznaroth.industrizer.world;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.world.gen.ModEntityGeneration;
import com.iznaroth.industrizer.world.gen.ModOreGeneration;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IndustrizerMod.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        ModOreGeneration.generateOres(event);

        ModEntityGeneration.onEntitySpawn(event);

    }
}
