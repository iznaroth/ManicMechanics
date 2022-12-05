package com.iznaroth.manicmechanics.tile;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.render.TileRenderTester;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IndustrizerTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ManicMechanics.MOD_ID);

    public static final RegistryObject<TileEntityType<BureauBlockTile>> BUREAU_TILE = TILE_ENTITIES.register("currency_bureau", () -> TileEntityType.Builder.of(BureauBlockTile::new, MMBlocks.CURRENCY_BUREAU.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<GeneratorBlockTile>> GENERATOR_TILE = TILE_ENTITIES.register("hep", () -> TileEntityType.Builder.of(GeneratorBlockTile::new, MMBlocks.HEP.get()).build(null)); //FIGURE THIS SHIT OUT!


    public static final RegistryObject<TileEntityType<CommunicatorBlockTile>> COMMUNICATOR_TILE = TILE_ENTITIES.register("communicator", () -> TileEntityType.Builder.of(CommunicatorBlockTile::new, MMBlocks.COMMUNICATOR.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<HighwayControllerBlockTile>> HIGHWAY_CONTROLLER_TILE = TILE_ENTITIES.register("highway_controller", () -> TileEntityType.Builder.of(HighwayControllerBlockTile::new, MMBlocks.HIGHWAY_CONTROLLER.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<TileRenderTester>> RENDER_TESTER_TILE = TILE_ENTITIES.register("render_tester_tile", () -> TileEntityType.Builder.of(TileRenderTester::new, MMBlocks.RENDER_TESTER.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<TubeBundleTile>> TUBE_BUNDLE_TILE = TILE_ENTITIES.register("tube_bundle_tile", () -> TileEntityType.Builder.of(TubeBundleTile::new, MMBlocks.TRANSPORT_TUBE.get(), MMBlocks.POWER_TUBE.get(), MMBlocks.FLUID_TUBE.get(), MMBlocks.GAS_TUBE.get(), MMBlocks.TUBE_BUNDLE.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<SealingChamberBlockTile>> SEALER_TILE = TILE_ENTITIES.register("sealer_tile", () -> TileEntityType.Builder.of(SealingChamberBlockTile::new, MMBlocks.SEALER.get()).build(null)); //FIGURE THIS SHIT OUT!


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
