package com.iznaroth.manicmechanics.block.tube;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class GasTubeBlock extends AbstractTubeBlock {
    public GasTubeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTubeType(){
        return 3;
    }

    @Override
    public boolean canBuildConnection(IBlockReader iBlockReader, BlockPos blockPos, Direction direction){
        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);

        if(neighborBlockState.hasTileEntity() && iBlockReader.getBlockEntity(neighborPos) instanceof IFluidHandler){
            return true; //NOTE - Gas is not implemented yet, so this is a placeholder.
        }

        return false;
    }
}
