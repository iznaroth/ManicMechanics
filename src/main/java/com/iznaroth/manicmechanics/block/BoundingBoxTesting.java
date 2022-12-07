package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.util.MultipartUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BoundingBoxTesting extends MMBlockWrapperDebug {
    public BoundingBoxTesting(Properties p_i48440_1_) {

        super(p_i48440_1_);

        initialiseShapeCache();
    }

    protected static final VoxelShape WEST = Block.box(0.0D, 6.0D, 6.0D, 3.0D, 10.0D, 10.0D); //Connected on WEST
    protected static final VoxelShape EAST = Block.box(13.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D); //Connected on EAST

    protected static final VoxelShape NORTH = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 3.0D); //Connected on NORTH
    protected static final VoxelShape SOUTH = Block.box(6.0D, 6.0D, 13.0D, 10.0D, 10.0D, 16.0D); //Connected on SOUTH

    protected static final VoxelShape UP = Block.box(6.0D, 13.0D, 6.0D, 10.0D, 16.0D, 10.0D); //Connected on UP
    protected static final VoxelShape DOWN = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 3.0D, 10.0D); //Connected on DOWN

    protected static final VoxelShape ERROR = Block.box(6.0D, 13.0D, 6.0D, 10.0D, 16.0D, 10.0D); //Connected on DOWN


    private static final VoxelShape[] connections = {EAST, WEST, NORTH, SOUTH, UP, DOWN};

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        //IMPL-1: This is all of the shapes in a bundle.
        //VoxelShape voxelShape = voxelShapeCache.get(state);
        //return voxelShape != null ? voxelShape : VoxelShapes.block();  // should always find it... just being defensive

        //IMPL-2: This method casts a ray and evaluates the connection array, only displaying the hovered one.

        if (!(context instanceof EntityHitResult) || ((EntityHitResult) context).getEntity() == null) {
            //If we don't have an entity get the full VoxelShape
            return ERROR;
        }

        MultipartUtils.AdvancedRayTraceResult result = MultipartUtils.collisionRayTrace(((EntityHitResult)context).getEntity(), pos, Arrays.asList(connections));
        if (result != null && result.valid()) {
            return result.bounds;
        }

        return ERROR;
    }

    private void initialiseShapeCache() {
        for (BlockState blockState : getStateDefinition().getPossibleStates()) {
            VoxelShape combinedShape = WEST;
            ArrayList<Direction> connected = new ArrayList<Direction>();


            combinedShape = Shapes.or(combinedShape, EAST);
            combinedShape = Shapes.or(combinedShape, NORTH);
            combinedShape = Shapes.or(combinedShape, SOUTH);
            combinedShape = Shapes.or(combinedShape, UP);
            combinedShape = Shapes.or(combinedShape, DOWN);
            connected.add(Direction.UP);



            voxelShapeCache.put(blockState, combinedShape);
        }
    }

    private static HashMap<BlockState, VoxelShape> voxelShapeCache = new HashMap<>();

}
