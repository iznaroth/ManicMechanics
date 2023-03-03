package com.iznaroth.manicmechanics.setup;

import com.iznaroth.manicmechanics.entity.MMEntityTypes;
import com.iznaroth.manicmechanics.entity.custom.GridSkaterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonSetup {

    /*
    public static TileEntityType<TileRenderTester> tileEntityDataTypeRenderTester;

    @SubscribeEvent
    public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event) {
        tileEntityDataTypeRenderTester =
                TileEntityType.Builder.of(TileRenderTester::new, ModBlocks.RENDER_TESTER.get()).build(null);  // you probably don't need a datafixer --> null should be fine
        tileEntityDataTypeRenderTester.setRegistryName("manicmechanics:render_tester_tile");
        event.getRegistry().register(tileEntityDataTypeRenderTester);
    }*/

    @SubscribeEvent
    public static void onSpawnPlacementRegistration(SpawnPlacementRegisterEvent event){
        event.register(MMEntityTypes.GRID_SKATER.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);

        event.register(MMEntityTypes.CRYSTAL_WARLOCK.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

}
