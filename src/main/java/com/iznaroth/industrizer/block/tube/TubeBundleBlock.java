package com.iznaroth.industrizer.block.tube;

import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.item.IndustrizerItems;
import com.iznaroth.industrizer.logistics.ILogisticTube;
import com.iznaroth.industrizer.logistics.INetworkNavigable;
import com.iznaroth.industrizer.tile.CommunicatorBlockTile;
import com.iznaroth.industrizer.tile.TubeBundleTile;
import com.iznaroth.industrizer.util.TubeBundleStateMapper;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//Big thanks to TheGreyGhost's MinecraftByExample repo. Go check it out! --> https://github.com/TheGreyGhost/MinecraftByExample


public class TubeBundleBlock extends AbstractTubeBlock {

    public TubeBundleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state){ return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        //This block is only created by eating a prior single-tube.  It should return a reference to that old tile as-bound, but this may cause some issues.
        return new TubeBundleTile();
    }

    @Override
    public boolean canTubeLink(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {

        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        Block neighborBlock = neighborBlockState.getBlock();
        TubeBundleTile here = (TubeBundleTile) iBlockReader.getBlockEntity(blockPos);

        if(here == null){
            System.out.println("Tile uninitialized. Waiting for shape update!");
            return false; //We are in preinit and the TE is not ready yet. updateShape will be recalled for all directions once the TE wakes up.
        }

        //System.out.println("Trying to link for " + direction + ", where neighbor is " + neighborBlock);

        if (neighborBlock == Blocks.BARRIER) return false;
        if (isExceptionForConnection(neighborBlock)) return false;

        if (((neighborBlock instanceof AbstractTubeBlock && !(neighborBlock instanceof TubeBundleBlock) && here.hasTube(((AbstractTubeBlock) neighborBlock).getTubeType())) || (neighborBlock == IndustrizerBlocks.TUBE_BUNDLE.get() && ((TubeBundleTile) iBlockReader.getBlockEntity(neighborPos)).anyMatch(here)))) {
            //Tube Bundle connects to anything matching one of its known contents.
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
   public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {

        Block with = Block.byItem(player.getItemInHand(hand).getItem());
        TubeBundleTile on = (TubeBundleTile) world.getBlockEntity(pos);

        if(on == null) throw new IllegalArgumentException("Got a null TileEntity on use!");

        if(player.getItemInHand(hand).getItem().equals(IndustrizerItems.DYSPERSIUM_WIDGET.get())){
            System.out.println("Used widget!");
            boolean[] contents = on.getTubesInBlock();
            System.out.println(Arrays.toString(contents));
        }

        if(with instanceof AbstractTubeBlock && !with.equals(state.getBlock()) && !on.hasTube(((AbstractTubeBlock)with).getTubeType())) { //IF you are using a DIFFERENT logistic tube, we want to add to the bundle.
            on.addTube(((AbstractTubeBlock)with).getTubeType());

            ItemStack decrement = player.getItemInHand(hand);
            decrement.setCount(decrement.getCount() - 1);
            player.setItemInHand(hand, decrement);

            System.out.println("This array should contain the item you just added: " + Arrays.toString(on.getTubesInBlock()));

            on.refreshModelConnections();

            return ActionResultType.SUCCESS; //This should create the tile-entity as well?
        }

        return ActionResultType.PASS;
    }

    //WARNING - this is an unsafe call! It only has assumed protection from null tile errors.

}