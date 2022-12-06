package com.iznaroth.manicmechanics.block.tube;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.blockentity.TubeBundleBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

//Big thanks to TheGreyGhost's MinecraftByExample repo. Go check it out! --> https://github.com/TheGreyGhost/MinecraftByExample


public class TubeBundleBlock extends AbstractTubeBlock {

    public TubeBundleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Item asItem() {
        return null;
    }

    @Override
    protected Block asBlock() {
        return null;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        //This block is only created by eating a prior single-tube.  It should return a reference to that old tile as-bound, but this may cause some issues.
        return new TubeBundleBE(pos, state);
    }


    @Override
    public boolean canTubeLink(BlockGetter iBlockReader, BlockPos blockPos, Direction direction) {

        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        Block neighborBlock = neighborBlockState.getBlock();
        TubeBundleBE here = (TubeBundleBE) iBlockReader.getBlockEntity(blockPos);

        if(here == null){
            System.out.println("FALSE for " + direction + " from " + blockPos + " due to uninitialized TE");
            return false; //We are in preinit and the TE is not ready yet. updateShape will be recalled for all directions once the TE wakes up.
        }

        //System.out.println("Trying to link for " + direction + ", where neighbor is " + neighborBlock);

        if (neighborBlock == Blocks.BARRIER) return false;
        if (isExceptionForConnection(neighborBlockState)) return false;

        if (((neighborBlock instanceof AbstractTubeBlock && !(neighborBlock instanceof TubeBundleBlock) && here.hasTube(((AbstractTubeBlock) neighborBlock).getTubeType())) || (neighborBlock == MMBlocks.TUBE_BUNDLE.get() && ((TubeBundleBE) iBlockReader.getBlockEntity(neighborPos)).anyMatch(here)))) {
            //Tube Bundle connects to anything matching one of its known contents.
            here.addTileNeighbor((TubeBundleBE) iBlockReader.getBlockEntity(here.getBlockPos().relative(direction)), direction.ordinal());
            return true;
        }
        return canBuildConnection(iBlockReader, blockPos, direction);
    }

    @Nonnull
    @Override
   public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {

        if(world.isClientSide()){
            return InteractionResult.PASS;
        }

        Block with = Block.byItem(player.getItemInHand(hand).getItem());
        TubeBundleBE on = (TubeBundleBE) world.getBlockEntity(pos);

        if(on == null) throw new IllegalArgumentException("Got a null BlockEntity on use!");

        if(player.getItemInHand(hand).getItem().equals(MMItems.DYSPERSIUM_WIDGET.get())){
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

            return InteractionResult.SUCCESS; //This should create the tile-entity as well?
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean canBuildConnection(BlockGetter iBlockReader, BlockPos blockPos, Direction direction){
        boolean[] tubesInBlock = ((TubeBundleBE) iBlockReader.getBlockEntity(blockPos)).getTubesInBlock();
        //System.out.println(Arrays.toString(tubesInBlock));

        if(tubesInBlock[0]){
            if(((TransportTubeBlock) MMBlocks.TRANSPORT_TUBE.get()).canBuildConnection(iBlockReader, blockPos, direction))
                return true;
        }

        if(tubesInBlock[1]){
            if(((PowerTubeBlock) MMBlocks.POWER_TUBE.get()).canBuildConnection(iBlockReader, blockPos, direction))
                return true;
        }

        if(tubesInBlock[2]){
            if(((FluidTubeBlock) MMBlocks.FLUID_TUBE.get()).canBuildConnection(iBlockReader, blockPos, direction))
                return true;
        }

        if(tubesInBlock[3]){
             if(((GasTubeBlock) MMBlocks.GAS_TUBE.get()).canBuildConnection(iBlockReader, blockPos, direction))
                return true;
        }

        return false;
    }


    //WARNING - this is an unsafe call! It only has assumed protection from null tile errors.

}