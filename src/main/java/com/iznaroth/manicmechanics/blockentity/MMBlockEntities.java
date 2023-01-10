package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMBlockEntities {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ManicMechanics.MOD_ID);


    public static final RegistryObject<BlockEntityType<BureauBlockEntity>> BUREAU_BE = BLOCK_ENTITIES.register("currency_bureau", () -> BlockEntityType.Builder.of(BureauBlockEntity::new, MMBlocks.CURRENCY_BUREAU.get()).build(null));

    public static final RegistryObject<BlockEntityType<InfuserBlockEntity>> INFUSER_BE = BLOCK_ENTITIES.register("infuser", () -> BlockEntityType.Builder.of(InfuserBlockEntity::new, MMBlocks.INFUSER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CondenserBlockEntity>> CONDENSER_BE = BLOCK_ENTITIES.register("condenser", () -> BlockEntityType.Builder.of(CondenserBlockEntity::new, MMBlocks.CONDENSER.get()).build(null));

    public static final RegistryObject<BlockEntityType<AssemblerBlockEntity>> ASSEMBLER_BE = BLOCK_ENTITIES.register("assembler", () -> BlockEntityType.Builder.of(AssemblerBlockEntity::new, MMBlocks.ASSEMBLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR_TILE = BLOCK_ENTITIES.register("hep", () -> BlockEntityType.Builder.of(GeneratorBlockEntity::new, MMBlocks.HEP.get()).build(null)); //FIGURE THIS SHIT OUT!


    public static final RegistryObject<BlockEntityType<CommunicatorBlockEntity>> COMMUNICATOR_TILE = BLOCK_ENTITIES.register("communicator", () -> BlockEntityType.Builder.of(CommunicatorBlockEntity::new, MMBlocks.COMMUNICATOR.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<SimpleCommunicatorBlockEntity>> SIMPLE_COMMUNICATOR_TILE = BLOCK_ENTITIES.register("simple_communicator", () -> BlockEntityType.Builder.of(SimpleCommunicatorBlockEntity::new, MMBlocks.SIMPLE_COMMUNICATOR.get()).build(null)); //FIGURE THIS SHIT OUT!



    public static final RegistryObject<BlockEntityType<HighwayControllerBlockEntity>> HIGHWAY_CONTROLLER_TILE = BLOCK_ENTITIES.register("highway_controller", () -> BlockEntityType.Builder.of(HighwayControllerBlockEntity::new, MMBlocks.HIGHWAY_CONTROLLER.get()).build(null)); //FIGURE THIS SHIT OUT!

    //public static final RegistryObject<BlockEntityType<TileRenderTester>> RENDER_TESTER_TILE = BLOCK_ENTITIES.register("render_tester_tile", () -> BlockEntityType.Builder.of(TileRenderTester::new, MMBlocks.RENDER_TESTER.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<TubeBundleBE>> TUBE_BUNDLE_TILE = BLOCK_ENTITIES.register("tube_bundle_tile", () -> BlockEntityType.Builder.of(TubeBundleBE::new, MMBlocks.TRANSPORT_TUBE.get(), MMBlocks.POWER_TUBE.get(), MMBlocks.FLUID_TUBE.get(), MMBlocks.GAS_TUBE.get(), MMBlocks.TUBE_BUNDLE.get()).build(null)); //FIGURE THIS SHIT OUT!

    public static final RegistryObject<BlockEntityType<SealingChamberBlockEntity>> SEALER_TILE = BLOCK_ENTITIES.register("sealer_tile", () -> BlockEntityType.Builder.of(SealingChamberBlockEntity::new, MMBlocks.SEALER.get()).build(null)); //FIGURE THIS SHIT OUT!



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
