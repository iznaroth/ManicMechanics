package com.iznaroth.industrizer.tile;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.render.TileRenderTester;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IndustrizerTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, IndustrizerMod.MOD_ID);

    public static final RegistryObject<TileEntityType<BureauBlockTile>> BUREAU_TILE = TILE_ENTITIES.register("currency_bureau", () -> TileEntityType.Builder.of(BureauBlockTile::new, IndustrizerBlocks.CURRENCY_BUREAU.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<GeneratorBlockTile>> GENERATOR_TILE = TILE_ENTITIES.register("hep", () -> TileEntityType.Builder.of(GeneratorBlockTile::new, IndustrizerBlocks.HEP.get()).build(null)); //FIGURE THIS SHIT OUT!


    public static final RegistryObject<TileEntityType<CommunicatorBlockTile>> COMMUNICATOR_TILE = TILE_ENTITIES.register("communicator", () -> TileEntityType.Builder.of(CommunicatorBlockTile::new, IndustrizerBlocks.COMMUNICATOR.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<HighwayControllerBlockTile>> HIGHWAY_CONTROLLER_TILE = TILE_ENTITIES.register("highway_controller", () -> TileEntityType.Builder.of(HighwayControllerBlockTile::new, IndustrizerBlocks.HIGHWAY_CONTROLLER.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<TileRenderTester>> RENDER_TESTER_TILE = TILE_ENTITIES.register("render_tester_tile", () -> TileEntityType.Builder.of(TileRenderTester::new, IndustrizerBlocks.RENDER_TESTER.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<TileEntityType<TubeBundleTile>> TUBE_BUNDLE_TILE = TILE_ENTITIES.register("tube_bundle_tile", () -> TileEntityType.Builder.of(TubeBundleTile::new, IndustrizerBlocks.TUBE_BUNDLE.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
