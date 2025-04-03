package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.blockentity.*;
import com.iznaroth.m4.common.M4;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class M4BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, M4.MODID);

    public static final Supplier<BlockEntityType<ObliterationPlinthBlockEntity>> OBLITERATION_PLINTH_ENTITY = BLOCK_ENTITIES.register("obliteration_plinth",
            () -> BlockEntityType.Builder.of(ObliterationPlinthBlockEntity::new, M4Blocks.OBLITERATION_PLINTH.get()).build(null));

    public static final Supplier<BlockEntityType<ManufactorumBlockEntity>> MANUFACTORUM_BLOCK_ENTITY = BLOCK_ENTITIES.register("manufactorum",
            () -> BlockEntityType.Builder.of(ManufactorumBlockEntity::new, M4Blocks.MANUFACTORUM.get()).build(null));

    public static final Supplier<BlockEntityType<HEPCBlockEntity>> HEPC_BLOCK_ENTITY = BLOCK_ENTITIES.register("hyperfield_extract_polarization_chamber",
            () -> BlockEntityType.Builder.of(HEPCBlockEntity::new, M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get()).build(null));

    public static final Supplier<BlockEntityType<ChargingStationBlockEntity>> CHARGING_STATION_BLOCK_ENTITY = BLOCK_ENTITIES.register("charging_station",
            () -> BlockEntityType.Builder.of(ChargingStationBlockEntity::new, M4Blocks.CHARGING_STATION.get()).build(null));

    public static final Supplier<BlockEntityType<CableTubeBlockEntity>> CABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("power_cable",
            () -> BlockEntityType.Builder.of(CableTubeBlockEntity::new, M4Blocks.POWER_CABLE_BLOCK.get()).build(null));

    //public static final Supplier<BlockEntityType<FacadeBlockEntity>> FACADE_BLOCK_ENTITY = BLOCK_ENTITIES.register("facade_block",
    //        () -> BlockEntityType.Builder.of(FacadeBlockEntity::new, M4Blocks.FACADE_BLOCK.get()).build(null));


}
