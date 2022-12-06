package com.iznaroth.manicmechanics.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


import javax.annotation.Nullable;

public class ECPBlock extends Block {

    protected static final VoxelShape AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public ECPBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED); //syntactically incorrect? tutorial used createBlockStateDefinition
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return AABB;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    public boolean strikeAndAffirm(BlockPos here, ServerLevel level){ //Called by a block beneath this one to check for open sky and height, then strike self with lightning and return true.
        if(here.getY() < 100){
            System.out.println("Not high enough to establish contact!");
            return false;
        }

        for(int i = here.getY() + 1; i < level.getMaxBuildHeight(); i++){
            if(!level.getBlockState(new BlockPos(here.getX(), i, here.getZ())).getBlock().equals(Blocks.AIR)){
                System.out.println("Vision blocked at: " + i);
                return false;
            }
        }

        System.out.println("Spawn lightning here!");

        EntityType.LIGHTNING_BOLT.spawn(level, null, null, here, MobSpawnType.TRIGGERED, true, true);
        return true; //Contact made. Tell the tile to finish the operation succesfully.
    }

}
