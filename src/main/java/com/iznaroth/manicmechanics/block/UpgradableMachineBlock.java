package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.logistics.INetworkNavigable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;

public class UpgradableMachineBlock extends MMBlockWrapperDebug {
    public UpgradableMachineBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    public int tier;

    public void upgradeTo(int what) {
        //Override this and set the texture overlay & name!


        this.tier = what;
    }

    public ItemStack downgradeAndReturn() {
        //Return a full upgrade casing of the current tier

        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING); //syntactically incorrect? tutorial used createBlockStateDefinition
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }
}



