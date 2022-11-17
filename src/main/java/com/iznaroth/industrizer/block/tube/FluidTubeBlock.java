package com.iznaroth.industrizer.block.tube;

public class FluidTubeBlock extends AbstractTubeBlock {
    public FluidTubeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTubeType(){
        return 2;
    }
}
