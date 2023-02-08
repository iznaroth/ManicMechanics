package com.iznaroth.manicmechanics.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;

public class RotatableBlock extends MMBlockWrapper {

    public RotatableBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.POWERED); //syntactically incorrect? tutorial used createBlockStateDefinition
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
