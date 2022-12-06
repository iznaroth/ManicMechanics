package com.iznaroth.manicmechanics.world;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.world.gen.ModEntityGeneration;
import com.iznaroth.manicmechanics.world.gen.ModOreGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManicMechanics.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        ModOreGeneration.generateOres(event);

        ModEntityGeneration.onEntitySpawn(event);

    }
}
