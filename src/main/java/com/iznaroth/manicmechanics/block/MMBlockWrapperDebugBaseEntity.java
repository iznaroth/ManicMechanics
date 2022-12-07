package com.iznaroth.manicmechanics.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MMBlockWrapperDebugBaseEntity extends BaseEntityBlock {
    public MMBlockWrapperDebugBaseEntity(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        System.out.println("Call pSD on " + this.getName());
        return !isShapeFullBlock(p_49928_.getShape(p_49929_, p_49930_)) && p_49928_.getFluidState().isEmpty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }
    //This was used to troubleshoot a registration/blockstate error.
}
