package com.iznaroth.manicmechanics.block.tube;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidTubeBlock extends AbstractTubeBlock {
    public FluidTubeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTubeType(){
        return 2;
    }

    @Override
    public boolean canBuildConnection(BlockGetter iBlockReader, BlockPos blockPos, Direction direction){
        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);

        if(neighborBlockState.hasBlockEntity() && iBlockReader.getBlockEntity(neighborPos) instanceof IFluidHandler){
            return true;
        }

        return false;
    }
}
