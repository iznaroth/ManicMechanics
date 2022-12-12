package com.iznaroth.manicmechanics.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;

import javax.annotation.Nullable;

public class UpgradableMachineBlock extends MMBaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 5);


    /**
     * All UpgradableMachineBlocks use the TIER blockstate to modify their appearance. They implicatively have an UpgradableBlockEntity attached.
     *
     * @param p_i48440_1_
     */
    public UpgradableMachineBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        BlockState defaultBlockState = this.defaultBlockState()
                .setValue(TIER, 0);
        this.registerDefaultState(defaultBlockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIER);
    }




}



