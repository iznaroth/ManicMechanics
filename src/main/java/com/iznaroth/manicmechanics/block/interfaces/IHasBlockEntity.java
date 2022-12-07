package com.iznaroth.manicmechanics.block.interfaces;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

//Borrowed from Mekanism!
public interface IHasBlockEntity <TILE extends BlockEntity> {

    BlockEntityType<? extends TILE> getTileType();

}
