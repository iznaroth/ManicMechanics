package com.iznaroth.manicmechanics.block.interfaces;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

//Borrowed from Mekanism!
public interface IHasBlockEntity <BE extends BlockEntity> {

    BlockEntityType<? extends BE> getTileType();

}
