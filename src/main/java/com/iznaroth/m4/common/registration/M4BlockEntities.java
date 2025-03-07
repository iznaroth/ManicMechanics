package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.blockentity.ObliterationPlinthBlockEntity;
import com.iznaroth.m4.common.m4;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class M4BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, m4.MODID);

    public static final Supplier<BlockEntityType<ObliterationPlinthBlockEntity>> OBLITERATION_PLINTH_ENTITY = BLOCK_ENTITIES.register("obliteration_plinth",
            () -> BlockEntityType.Builder.of(ObliterationPlinthBlockEntity::new, M4Blocks.OBLITERATION_PLINTH.get()).build(null));

}
