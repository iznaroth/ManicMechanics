package com.iznaroth.manicmechanics.world.feature;

import com.iznaroth.manicmechanics.entity.ModEntityTypes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.level.LevelEvent;

public class ModEntityGeneration {
    public static void onEntitySpawn(final LevelEvent.PotentialSpawns event){
        event.addSpawnerData(new MobSpawnSettings.SpawnerData(ModEntityTypes.PINCH.get(), 40, 2, 4));
    }


}
