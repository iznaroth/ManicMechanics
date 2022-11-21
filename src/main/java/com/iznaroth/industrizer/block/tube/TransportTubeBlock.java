package com.iznaroth.industrizer.block.tube;

import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.logistics.ILogisticTube;
import com.iznaroth.industrizer.logistics.INetworkNavigable;
import com.iznaroth.industrizer.tile.TubeBundleTile;
import com.iznaroth.industrizer.util.TubeBundleStateMapper;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

//Big thanks to TheGreyGhost's MinecraftByExample repo. Go check it out! --> https://github.com/TheGreyGhost/MinecraftByExample


public class TransportTubeBlock extends AbstractTubeBlock {

    public TransportTubeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTubeType(){
        return 0; //THIS IS OVERRIDEN BY CHILDREN - 0 is Logistic, 1 is Fluid, 2 is Gas, 3 is Power
    }

    @Override
    public boolean canBuildConnection(IBlockReader iBlockReader, BlockPos blockPos, Direction direction){
        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        TubeBundleTile here = (TubeBundleTile) iBlockReader.getBlockEntity(blockPos); //We can presume this is not null, because this function is only called inside a function that verifies it.

        System.out.println("Logistic Tube trying to connect to non-conduit face");


        if(neighborBlockState.hasTileEntity() && iBlockReader.getBlockEntity(neighborPos) instanceof IItemHandler || iBlockReader.getBlockEntity(neighborPos) instanceof IInventory){
            here.buildOrUpdateConnection(this.getTubeType(), direction); //dont worry it wont
            return true;
        }

        return false;
    }

}