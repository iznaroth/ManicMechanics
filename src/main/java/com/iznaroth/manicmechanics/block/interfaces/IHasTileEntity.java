package com.iznaroth.manicmechanics.block.interfaces;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

//Borrowed from Mekanism!
public interface IHasTileEntity <TILE extends TileEntity> {

    TileEntityType<? extends TILE> getTileType();

}
