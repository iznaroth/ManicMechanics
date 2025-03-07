package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.registration.M4Blocks;
import com.iznaroth.m4.common.m4;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class M4BlockStates extends BlockStateProvider {
    public M4BlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, m4.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(M4Blocks.OBLITERATION_PLINTH.get());
    }
}
