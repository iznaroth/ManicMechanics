package com.iznaroth.manicmechanics.world.feature;

import com.iznaroth.manicmechanics.entity.MMEntityTypes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.level.LevelEvent;

public class MMEntityGeneration {
    public static void onEntitySpawn(final LevelEvent.PotentialSpawns event){
        event.addSpawnerData(new MobSpawnSettings.SpawnerData(MMEntityTypes.PINCH.get(), 40, 2, 4));
    }


}
