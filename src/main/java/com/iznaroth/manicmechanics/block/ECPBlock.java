package com.iznaroth.manicmechanics.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class ECPBlock extends Block {

    protected static final VoxelShape AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public ECPBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED); //syntactically incorrect? tutorial used createBlockStateDefinition
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return AABB;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    public boolean strikeAndAffirm(BlockPos here, ServerWorld level){ //Called by a block beneath this one to check for open sky and height, then strike self with lightning and return true.
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

        EntityType.LIGHTNING_BOLT.spawn(level, null, null, here, SpawnReason.TRIGGERED, true, true);
        return true; //Contact made. Tell the tile to finish the operation succesfully.
    }

}
