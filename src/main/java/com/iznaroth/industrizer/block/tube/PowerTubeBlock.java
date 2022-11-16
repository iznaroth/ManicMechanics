package com.iznaroth.industrizer.block;

import com.iznaroth.industrizer.logistics.ILogisticTube;
import com.iznaroth.industrizer.logistics.INetworkNavigable;
import com.iznaroth.industrizer.tile.TubeBundleTile;
import com.iznaroth.industrizer.util.TubeBundleStateMapper;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public class PowerTubeBlock extends Block implements ILogisticTube, INetworkNavigable{

    public PowerTubeBlock(AbstractBlock.Properties properties) {
        super(properties);

        BlockState defaultBlockState = this.defaultBlockState()
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(EAST, false).setValue(WEST, false)
                .setValue(NORTH, false).setValue(SOUTH, false);
        this.registerDefaultState(defaultBlockState);
        initialiseShapeCache();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }


    /**
     * when the block is placed into the world, calculates the correct BlockState based on whether there is already water
     * in this block or not
     * Copied from StandingSignBlock
     *
     * @param blockItemUseContext
     * @return
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getLevel();
        BlockPos blockPos = blockItemUseContext.getClickedPos();

        BlockState blockState = this.defaultBlockState();
        blockState = setConnections(world, blockPos, blockState);

        return blockState;
    }

    @Override
    public BlockState updateShape(BlockState thisBlockState, Direction directionFromThisToNeighbor, BlockState neighborState,
                                  IWorld world, BlockPos thisBlockPos, BlockPos neighborBlockPos) {

        switch (directionFromThisToNeighbor) {  // Only update the specified direction.  Uses a switch for clarity but probably a map or similar is better for real code
            case UP:
                thisBlockState = thisBlockState.setValue(UP, canTubeLink(world, thisBlockPos, directionFromThisToNeighbor));
                break;
            case DOWN:
                thisBlockState = thisBlockState.setValue(DOWN, canTubeLink(world, thisBlockPos, directionFromThisToNeighbor));
                break;
            case EAST:
                thisBlockState = thisBlockState.setValue(EAST, canTubeLink(world, thisBlockPos, directionFromThisToNeighbor));
                break;
            case WEST:
                thisBlockState = thisBlockState.setValue(WEST, canTubeLink(world, thisBlockPos, directionFromThisToNeighbor));
                break;
            case NORTH:
                thisBlockState = thisBlockState.setValue(NORTH, canTubeLink(world, thisBlockPos, directionFromThisToNeighbor));
                break;
            case SOUTH:
                thisBlockState = thisBlockState.setValue(SOUTH, canTubeLink(world, thisBlockPos, directionFromThisToNeighbor));
                break;
            default:
                LOGGER.error("Unexpected facing:" + directionFromThisToNeighbor);
        }
        return thisBlockState;
    }

    //@Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {

        //Can we raycast for contact points?

        //we can borrow the lookingAt value to see what subshape we've hit.


        Block with = Block.byItem(player.getItemInHand(hand).getItem());

        if(with instanceof ILogisticTube && !with.equals(state.getBlock())) {

            TubeBundleStateMapper.mapToNewBundle(world, pos, (TubeBundleTile) world.getBlockEntity(pos), getTubeType(with.asItem())); //WARNING - May be an unsafe cast


            return ActionResultType.SUCCESS; //This should create the tile-entity as well?
        }

        return ActionResultType.PASS;
    }


    // the link properties below are used to communicate to the model renderer which of the web strands should be drawn, and whether any water should be drawn
    // Be wary of using too many properties.  A blockstate is created for every permutation of properties, so even for this simple example we have
    //  2^7 = 128 blockstates.

    // The mbe03b_block_3dweb_registry_name.json contains logic to check each of these properties by name, and draw the corresponding model components
    //   for each property which is true.


    private BlockState setConnections(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {


        return blockState
                .setValue(UP, canTubeLink(iBlockReader, blockPos, Direction.UP))
                .setValue(DOWN, canTubeLink(iBlockReader, blockPos, Direction.DOWN))
                .setValue(NORTH, canTubeLink(iBlockReader, blockPos, Direction.NORTH))
                .setValue(SOUTH, canTubeLink(iBlockReader, blockPos, Direction.SOUTH))
                .setValue(EAST, canTubeLink(iBlockReader, blockPos, Direction.EAST))
                .setValue(WEST, canTubeLink(iBlockReader, blockPos, Direction.WEST));
    }

    private boolean canTubeLink(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {



        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        Block neighborBlock = neighborBlockState.getBlock();

        if (neighborBlock == Blocks.BARRIER) return false;
        if (isExceptionForConnection(neighborBlock)) return false;
        if (neighborBlock instanceof INetworkNavigable || neighborBlock instanceof ILogisticTube || neighborBlock instanceof ContainerBlock) {
            //System.out.println("True for " + direction);
            return true;
        }
        return false;
    }


    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape voxelShape = voxelShapeCache.get(state);
        return voxelShape != null ? voxelShape : VoxelShapes.block();  // should always find it... just being defensive
    }


    private static final Vector3d CORE_MIN_CORNER = new Vector3d(5.5, 5.5, 5.5);
    private static final Vector3d CORE_MAX_CORNER = new Vector3d(10.5, 10.5, 10.5);

    private static final VoxelShape CORE_SHAPE =
            Block.box(CORE_MIN_CORNER.x, CORE_MIN_CORNER.y, CORE_MIN_CORNER.z, CORE_MAX_CORNER.x, CORE_MAX_CORNER.y, CORE_MAX_CORNER.z);

    private static final VoxelShape LINK_UP_SHAPE =
            Block.box(7.5, 10.5, 7.5, 8.5, 16, 8.5);
    private static final VoxelShape LINK_DOWN_SHAPE =
            Block.box(7.5, 0.0, 7.5, 8.5, 5.5, 8.5);
    private static final VoxelShape LINK_WEST_SHAPE =
            Block.box(0, 7.5, 7.5, 5.5, 8.5, 8.5);
    private static final VoxelShape LINK_EAST_SHAPE =
            Block.box(10.5, 7.5, 7.5, 16.0, 8.5, 8.5);
    private static final VoxelShape LINK_NORTH_SHAPE =
            Block.box(7.5, 7.5, 0.0, 8.5, 8.5, 5.5);
    private static final VoxelShape LINK_SOUTH_SHAPE =
            Block.box(7.5, 7.5, 10.5, 8.5, 8.5, 16.0);

    /**
     * Create a cache of the VoxelShape for each blockstate (all possible combinations of links).
     * If we wanted to optimise further we could get rid of the waterlogged property first but for the purposes of this
     *   example it's not worth the added complexity
     * @return
     */

    /**
     * NOTE: Injection can be ignored here. This is describing the shape of the block from a collision standpoint as it changes.
     * Model decisions occur based on the JSON.
     **/
    private void initialiseShapeCache() {
        for (BlockState blockState : getStateDefinition().getPossibleStates()) {
            VoxelShape combinedShape = CORE_SHAPE;
            ArrayList<Direction> connected = new ArrayList<Direction>();

            if (blockState.getValue(UP).booleanValue()) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_UP_SHAPE);
                connected.add(Direction.UP);
            }
            if (blockState.getValue(DOWN).booleanValue()) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_DOWN_SHAPE);
            }
            if (blockState.getValue(WEST).booleanValue()) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_WEST_SHAPE);
            }
            if (blockState.getValue(EAST).booleanValue()) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_EAST_SHAPE);
            }
            if (blockState.getValue(NORTH).booleanValue()) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_NORTH_SHAPE);
            }
            if (blockState.getValue(SOUTH).booleanValue()) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_SOUTH_SHAPE);
            }


            voxelShapeCache.put(blockState, combinedShape);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state){ return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        TubeBundleTile tile = new TubeBundleTile();
        tile.addTube(0); //TILE 0 is LOGISTIC
        return tile;
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

    public int getTubeType(){
        return 0; //THIS IS OVERRIDEN BY CHILDREN - 0 is Logistic, 1 is Fluid, 2 is Gas, 3 is Power
    }

    public int getTubeType(Item from){

        if(from.equals(IndustrizerBlocks.TRANSPORT_TUBE.get().asItem())){
            return 0;
        } else if (from.equals(IndustrizerBlocks.POWER_TUBE.get().asItem())){
            return 1;
        } else if(from.equals(IndustrizerBlocks.FLUID_TUBE.get().asItem())){
            return 2;
        } else if(from.equals(IndustrizerBlocks.GAS_TUBE.get().asItem())){
            return 3;
        }

        return -1;
    }

}


