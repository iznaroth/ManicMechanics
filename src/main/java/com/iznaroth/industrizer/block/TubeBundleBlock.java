package com.iznaroth.industrizer.block;

import com.iznaroth.industrizer.logistics.ILogisticTube;
import com.iznaroth.industrizer.logistics.INetworkNavigable;
import com.iznaroth.industrizer.tile.CommunicatorBlockTile;
import com.iznaroth.industrizer.tile.TubeBundleTile;
import com.sun.xml.internal.ws.api.pipe.Tube;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

//Big thanks to TheGreyGhost's MinecraftByExample repo. Go check it out! --> https://github.com/TheGreyGhost/MinecraftByExample


public class TubeBundleBlock extends Block implements INetworkNavigable, ILogisticTube {

    public TubeBundleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state){ return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new TubeBundleTile();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {

        ItemStack with = player.getItemInHand(hand);

        if(with.getItem().equals(IndustrizerBlocks.TRANSPORT_TUBE.get().asItem())) {
            //world.setBlock(pos, IndustrizerBlocks.TRANSPORT_TUBE_BUNDLE.get().defaultBlockState(), 0);
            return ActionResultType.SUCCESS; //This should create the tile-entity as well?
        }

        return ActionResultType.PASS;
    }

    //May need to compute elsewhere...
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape voxelShape = voxelShapeCache.get(state);
        return voxelShape != null ? voxelShape : VoxelShapes.block();  // should always find it... just being defensive
    }


    private static HashMap<BlockState, VoxelShape> voxelShapeCache = new HashMap<>();


    @Override
    public boolean onItemPassesPoint() {
        return INetworkNavigable.super.onItemPassesPoint();
    }

    @Override
    public boolean shouldFilter() {
        return INetworkNavigable.super.shouldFilter();
    }

    @Override
    public boolean runFilterFor(ItemStack itemStack) {
        return false;
    }
}