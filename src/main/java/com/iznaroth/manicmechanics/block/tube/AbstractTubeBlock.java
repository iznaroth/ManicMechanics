package com.iznaroth.manicmechanics.block.tube;

import com.iznaroth.manicmechanics.block.MMBaseEntityBlock;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.logistics.Connection;
import com.iznaroth.manicmechanics.blockentity.TubeBundleBE;
import com.iznaroth.manicmechanics.util.TubeBundleStateMapper;
import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class AbstractTubeBlock extends MMBaseEntityBlock {

    public AbstractTubeBlock(BlockBehaviour.Properties properties) {
        super(properties);

        BlockState defaultBlockState = this.defaultBlockState()
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(EAST, false).setValue(WEST, false)
                .setValue(NORTH, false).setValue(SOUTH, false);
        this.registerDefaultState(defaultBlockState);
        initialiseShapeCache();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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
    public BlockState getStateForPlacement(BlockPlaceContext blockItemUseContext) {
        Level world = blockItemUseContext.getLevel();
        BlockPos blockPos = blockItemUseContext.getClickedPos();

        BlockState blockState = this.defaultBlockState();
        blockState = setConnections(world, blockPos, blockState);

        return blockState;
    }

    @Override
    public BlockState updateShape(BlockState thisBlockState, Direction directionFromThisToNeighbor, BlockState neighborState,
                                  LevelAccessor world, BlockPos thisBlockPos, BlockPos neighborBlockPos) {

        System.out.println("-------------------- UPDATESHAPE IS BEING CALLED BY " + this.getClass().getTypeName() + " ------------------------------- ");

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
                //LOGGER.error("Unexpected facing:" + directionFromThisToNeighbor);
        }
        return thisBlockState;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {

        //Can we raycast for contact points?

        //we can borrow the lookingAt value to see what subshape we've hit.

        TubeBundleBE on = (TubeBundleBE) world.getBlockEntity(pos);

        if(on == null) throw new IllegalArgumentException("Got a null BlockEntity on use!");

        if(player.getItemInHand(hand).getItem().equals(MMItems.DYSPERSIUM_WIDGET.get())){
            System.out.println("Used widget!");
            boolean[] contents = on.getTubesInBlock();
            System.out.println(Arrays.toString(contents));

            for (Connection cxn : on.getConnections()) {
                if (cxn != null)
                    cxn.cycleMode();
            }


            return InteractionResult.SUCCESS;
        }

        if(player.getItemInHand(hand).getItem().equals(MMItems.WALLET.get())){ //NOTE - remove this
            System.out.println("Used wallet!");
            Connection[] contents = on.getConnections();
            System.out.println(Arrays.toString(contents));
            System.out.println(on.getNetworkManager());


            return InteractionResult.SUCCESS;
        }


        Block with = Block.byItem(player.getItemInHand(hand).getItem());

        if(!(with instanceof TubeBundleBlock) && with instanceof AbstractTubeBlock && !with.equals(state.getBlock())) {
            assert on != null;
            on.addTube(((AbstractTubeBlock) with).getTubeType());
            TubeBundleStateMapper.mapDestroyedTile(pos, on);
            world.setBlock(pos, ((TubeBundleBlock) MMBlocks.TUBE_BUNDLE.get()).setConnections(world, pos, MMBlocks.TUBE_BUNDLE.get().defaultBlockState()), 0); //NOTE - this is ugly and could be done better

            return InteractionResult.SUCCESS; //This should create the tile-entity as well?
        }


        return InteractionResult.PASS;
    }


    // the link properties below are used to communicate to the model renderer which of the web strands should be drawn, and whether any water should be drawn
    // Be wary of using too many properties.  A blockstate is created for every permutation of properties, so even for this simple example we have
    //  2^7 = 128 blockstates.

    // The mbe03b_block_3dweb_registry_name.json contains logic to check each of these properties by name, and draw the corresponding model components
    //   for each property which is true.

    public BlockState setConnections(BlockGetter iBlockReader, BlockPos blockPos, BlockState blockState) {


        return blockState
                .setValue(UP, canTubeLink(iBlockReader, blockPos, Direction.UP))
                .setValue(DOWN, canTubeLink(iBlockReader, blockPos, Direction.DOWN))
                .setValue(NORTH, canTubeLink(iBlockReader, blockPos, Direction.NORTH))
                .setValue(SOUTH, canTubeLink(iBlockReader, blockPos, Direction.SOUTH))
                .setValue(EAST, canTubeLink(iBlockReader, blockPos, Direction.EAST))
                .setValue(WEST, canTubeLink(iBlockReader, blockPos, Direction.WEST));
    }

    public boolean canTubeLink(BlockGetter iBlockReader, BlockPos blockPos, Direction direction) {

        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        Block neighborBlock = neighborBlockState.getBlock();
        TubeBundleBE here = (TubeBundleBE) iBlockReader.getBlockEntity(blockPos);

        if(here == null){
            System.out.println("FALSE for " + direction + " from " + blockPos + " due to uninitialized TE");
            return false; //We are in preinit and the TE is not ready yet. updateShape will be recalled for all directions once the TE wakes up. This is important to prevent synchro crashes
        }                 //when we want to build and activate CONNECTIONS.

        if (neighborBlock == Blocks.BARRIER) return false;
        if (isExceptionForConnection(neighborBlockState)) return false;

        if (neighborBlock == this.defaultBlockState().getBlock() || (neighborBlock == MMBlocks.TUBE_BUNDLE.get() && ((TubeBundleBE)iBlockReader.getBlockEntity(neighborPos)).hasTube(this.getTubeType()))) { //DEFAULT BEHAVIOR FOR TUBES = only connect to identicals and bundles. Bundles override this to link to all others.
            //System.out.println("True for " + direction);
            System.out.println("TRUE for " + direction + " from " + blockPos);
            here.addTileNeighbor((TubeBundleBE) iBlockReader.getBlockEntity(here.getBlockPos().relative(direction)), direction.ordinal());
            return true;
        }

        return this.canBuildConnection(iBlockReader, blockPos, direction);
    }

    public boolean canBuildConnection(BlockGetter iBlockReader, BlockPos blockPos, Direction direction){
        System.out.println("YOU SHOULD NEVER SEE THIS MESSAGE. cBC called from abstract.");
        return false; //This is overriden by all children!  It returns "true" when the correct type of handler is attached.
    }


    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public VoxelShape getShape(BlockState state, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        VoxelShape voxelShape = voxelShapeCache.get(state);
        return voxelShape != null ? voxelShape : Shapes.block();  // should always find it... just being defensive
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
                combinedShape = Shapes.or(combinedShape, LINK_UP_SHAPE);
                connected.add(Direction.UP);
            }
            if (blockState.getValue(DOWN).booleanValue()) {
                combinedShape = Shapes.or(combinedShape, LINK_DOWN_SHAPE);
            }
            if (blockState.getValue(WEST).booleanValue()) {
                combinedShape = Shapes.or(combinedShape, LINK_WEST_SHAPE);
            }
            if (blockState.getValue(EAST).booleanValue()) {
                combinedShape = Shapes.or(combinedShape, LINK_EAST_SHAPE);
            }
            if (blockState.getValue(NORTH).booleanValue()) {
                combinedShape = Shapes.or(combinedShape, LINK_NORTH_SHAPE);
            }
            if (blockState.getValue(SOUTH).booleanValue()) {
                combinedShape = Shapes.or(combinedShape, LINK_SOUTH_SHAPE);
            }


            voxelShapeCache.put(blockState, combinedShape);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        TubeBundleBE tile = new TubeBundleBE(pos, state);
        tile.addTube(this.getTubeType()); //TILE 0 is LOGISTIC
        return tile;
    }

    private static HashMap<BlockState, VoxelShape> voxelShapeCache = new HashMap<>();

    public int getTubeType(){
        return -1; //THIS IS OVERRIDEN BY CHILDREN - 0 is Logistic, 1 is Power, 2 is Fluid, 3 is Gas
    }

    public int getTubeType(Item from){

        if(from.equals(MMBlocks.TRANSPORT_TUBE.get().asItem())){
            return 0;
        } else if (from.equals(MMBlocks.POWER_TUBE.get().asItem())){
            return 1;
        } else if(from.equals(MMBlocks.FLUID_TUBE.get().asItem())){
            return 2;
        } else if(from.equals(MMBlocks.GAS_TUBE.get().asItem())){
            return 3;
        }

        return -1;
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
        //((TubeBundleBE) p_60516_.getBlockEntity(p_60517_)).clearAll();
    }

}
