package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;

public class NodePointerBlock extends Block {
    public NodePointerBlock(BlockBehaviour.Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UPPER_ARM, FOREARM);
    }


    /**
     * when the block is placed into the world, calculates the correct BlockState based on whether there is already water
     * in this block or not
     * Copied from StandingSignBlock
     *
     * @param blockItemUseContext
     * @return
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockItemUseContext) {
        BlockState blockState = this.defaultBlockState().setValue(UPPER_ARM, blockItemUseContext.getClickedFace().getOpposite()).setValue(FOREARM, blockItemUseContext.getClickedFace());

        System.out.println(blockState.getValue(UPPER_ARM));
        System.out.println("The opposite of UPPER_ARM is: " + blockState.getValue(UPPER_ARM).getOpposite() + ". FOREARM assigned: " + blockState.getValue(FOREARM));

        return blockState;
    }

    public static final DirectionProperty UPPER_ARM = DirectionProperty.create("upper_arm");
    public static final DirectionProperty FOREARM = DirectionProperty.create("forearm");
}
