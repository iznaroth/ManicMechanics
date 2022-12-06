package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.render.TileRenderTester;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMBlockEntities {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ManicMechanics.MOD_ID);

    public static final RegistryObject<BlockEntityType<BureauBlockEntity>> BUREAU_BE = BLOCK_ENTITIES.register("currency_bureau", () -> BlockEntityType.Builder.of(BureauBlockEntity::new, MMBlocks.CURRENCY_BUREAU.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<GeneratorBlockTile>> GENERATOR_TILE = BLOCK_ENTITIES.register("hep", () -> BlockEntityType.Builder.of(GeneratorBlockTile::new, MMBlocks.HEP.get()).build(null)); //FIGURE THIS SHIT OUT!


    public static final RegistryObject<BlockEntityType<CommunicatorBlockTile>> COMMUNICATOR_TILE = BLOCK_ENTITIES.register("communicator", () -> BlockEntityType.Builder.of(CommunicatorBlockTile::new, MMBlocks.COMMUNICATOR.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<SimpleCommunicatorBlockTile>> SIMPLE_COMMUNICATOR_TILE = BLOCK_ENTITIES.register("simple_communicator", () -> BlockEntityType.Builder.of(SimpleCommunicatorBlockTile::new, MMBlocks.SIMPLE_COMMUNICATOR.get()).build(null)); //FIGURE THIS SHIT OUT!



    public static final RegistryObject<BlockEntityType<HighwayControllerBlockTile>> HIGHWAY_CONTROLLER_TILE = BLOCK_ENTITIES.register("highway_controller", () -> BlockEntityType.Builder.of(HighwayControllerBlockTile::new, MMBlocks.HIGHWAY_CONTROLLER.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<TileRenderTester>> RENDER_TESTER_TILE = BLOCK_ENTITIES.register("render_tester_tile", () -> BlockEntityType.Builder.of(TileRenderTester::new, MMBlocks.RENDER_TESTER.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<TubeBundleBE>> TUBE_BUNDLE_TILE = BLOCK_ENTITIES.register("tube_bundle_tile", () -> BlockEntityType.Builder.of(TubeBundleBE::new, MMBlocks.TRANSPORT_TUBE.get(), MMBlocks.POWER_TUBE.get(), MMBlocks.FLUID_TUBE.get(), MMBlocks.GAS_TUBE.get(), MMBlocks.TUBE_BUNDLE.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<SealingChamberBlockTile>> SEALER_TILE = BLOCK_ENTITIES.register("sealer_tile", () -> BlockEntityType.Builder.of(SealingChamberBlockTile::new, MMBlocks.SEALER.get()).build(null)); //FIGURE THIS SHIT OUT!


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
