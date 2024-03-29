package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.logistics.ILogisticTube;
import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

//Big thanks to TheGreyGhost's MinecraftByExample repo. Go check it out! --> https://github.com/TheGreyGhost/MinecraftByExample


public class VacuumHighwaySegmentBlock extends MMBlockWrapper {

    public VacuumHighwaySegmentBlock(Properties properties) {
        super(properties);

        BlockState defaultBlockState = this.defaultBlockState()
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(EAST, false).setValue(WEST, false)
                .setValue(NORTH, false).setValue(SOUTH, false)
                .setValue(INJECTED, false); //2^7 blockstates, 128 -
        this.registerDefaultState(defaultBlockState);
        initialiseShapeCache();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN, INJECTED, BlockStateProperties.FACING);
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

        blockState.setValue(BlockStateProperties.FACING, blockItemUseContext.getNearestLookingDirection().getOpposite());

        blockState = setConnections(world, blockPos, blockState, blockItemUseContext.getNearestLookingDirection().getOpposite());

        return blockState;
    }

    @Override
    public BlockState updateShape(BlockState thisBlockState, Direction directionFromThisToNeighbor, BlockState neighborState,
                                  LevelAccessor world, BlockPos thisBlockPos, BlockPos neighborBlockPos) {

        Direction facing = thisBlockState.getValue(BlockStateProperties.FACING);

        switch (directionFromThisToNeighbor) {  // Only update the specified direction.  Uses a switch for clarity but probably a map or similar is better for real code
            case UP:
                thisBlockState = thisBlockState.setValue(UP, canHighwayLinkToNeighbor(world, thisBlockPos, directionFromThisToNeighbor, facing));
                break;
            case DOWN:
                thisBlockState = thisBlockState.setValue(DOWN, canHighwayLinkToNeighbor(world, thisBlockPos, directionFromThisToNeighbor, facing));
                break;
            case EAST:
                thisBlockState = thisBlockState.setValue(EAST, canHighwayLinkToNeighbor(world, thisBlockPos, directionFromThisToNeighbor, facing));
                break;
            case WEST:
                thisBlockState = thisBlockState.setValue(WEST, canHighwayLinkToNeighbor(world, thisBlockPos, directionFromThisToNeighbor, facing));
                break;
            case NORTH:
                thisBlockState = thisBlockState.setValue(NORTH, canHighwayLinkToNeighbor(world, thisBlockPos, directionFromThisToNeighbor, facing));
                break;
            case SOUTH:
                thisBlockState = thisBlockState.setValue(SOUTH, canHighwayLinkToNeighbor(world, thisBlockPos, directionFromThisToNeighbor, facing));
                break;
            default:

        }
        return thisBlockState;
    }

    @Override
    public InteractionResult use(BlockState p_225533_1_, Level p_225533_2_, BlockPos p_225533_3_, Player p_225533_4_, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
        System.out.println(p_225533_1_.getValue(BlockStateProperties.FACING));
        return InteractionResult.PASS;
    }


    // the link properties below are used to communicate to the model renderer which of the web strands should be drawn, and whether any water should be drawn
    // Be wary of using too many properties.  A blockstate is created for every permutation of properties, so even for this simple example we have
    //  2^7 = 128 blockstates.

    // The mbe03b_block_3dweb_registry_name.json contains logic to check each of these properties by name, and draw the corresponding model components
    //   for each property which is true.


    private BlockState setConnections(BlockGetter iBlockReader, BlockPos blockPos, BlockState blockState, Direction facing) {


        return blockState
                .setValue(UP, canHighwayLinkToNeighbor(iBlockReader, blockPos, Direction.UP, facing))
                .setValue(DOWN, canHighwayLinkToNeighbor(iBlockReader, blockPos, Direction.DOWN, facing))
                .setValue(NORTH, canHighwayLinkToNeighbor(iBlockReader, blockPos, Direction.NORTH, facing))
                .setValue(SOUTH, canHighwayLinkToNeighbor(iBlockReader, blockPos, Direction.SOUTH, facing))
                .setValue(EAST, canHighwayLinkToNeighbor(iBlockReader, blockPos, Direction.EAST, facing))
                .setValue(WEST, canHighwayLinkToNeighbor(iBlockReader, blockPos, Direction.WEST, facing))
                .setValue(INJECTED, isInnerTubeInjected(iBlockReader, blockPos))
                .setValue(BlockStateProperties.FACING, facing);
    }

    private boolean canHighwayLinkToNeighbor(BlockGetter iBlockReader, BlockPos blockPos, Direction direction, Direction facing) {

        //These two sides are illegal.
        if (direction == facing || direction == facing.getOpposite()) {
            return false;
        }


        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        Block neighborBlock = neighborBlockState.getBlock();

        if (neighborBlock == Blocks.BARRIER) return false;
        if (neighborBlock instanceof ILogisticTube) return true;
        if (isExceptionForConnection(neighborBlockState)) return false;

        return false;
    }


    private boolean isInnerTubeInjected(BlockGetter iBlockReader, BlockPos blockPos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = blockPos.relative(dir);
            BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
            Block neighborBlock = neighborBlockState.getBlock();

            if (neighborBlock == MMBlocks.VACUUM_HIGHWAY_SEGMENT.get() && neighborBlockState.getValue(INJECTED)) { //Injection is passed down pipelines.
                return true;
            }
        }

        return false; //Only fail injection if no adjacent tubes
    }


    //This will seem confusing -
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    //public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private static final Property<Boolean> INJECTED = BooleanProperty.create("injected"); //Probably illegal..


    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape voxelShape = voxelShapeCache.get(state);
        return voxelShape != null ? voxelShape : Shapes.block();  // should always find it... just being defensive
    }

    // TODO - This is the Tube's bounding box. It needs to be set to the size of a normal cube - 2 on each side normally.
    private static final Vector3d CORE_MIN_CORNER = new Vector3d(2, 2, 2);
    private static final Vector3d CORE_MAX_CORNER = new Vector3d(14, 14, 14);

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

    private static HashMap<BlockState, VoxelShape> voxelShapeCache = new HashMap<>();

}
