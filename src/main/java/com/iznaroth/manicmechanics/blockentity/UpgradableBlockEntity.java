package com.iznaroth.manicmechanics.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class UpgradableBlockEntity extends BlockEntity {
    int tier;

    public UpgradableBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
        this.tier = 0;
    }

    public void upgradeTier(int in){
        this.tier = in;
    }

    public int getTier(){
        return tier;
    }
}
