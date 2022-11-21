package com.iznaroth.industrizer.block.tube;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class FluidTubeBlock extends AbstractTubeBlock {
    public FluidTubeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTubeType(){
        return 2;
    }

    @Override
    public boolean canBuildConnection(IBlockReader iBlockReader, BlockPos blockPos, Direction direction){
        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);

        if(neighborBlockState.hasTileEntity() && iBlockReader.getBlockEntity(neighborPos) instanceof IFluidHandler){
            return true;
        }

        return false;
    }
}
