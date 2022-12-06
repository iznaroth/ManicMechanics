package com.iznaroth.manicmechanics.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.World;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.awt.*;
import java.util.Random;

public class BlockRenderTester extends Block {

    public BlockRenderTester(Properties properties)
    {
        super(properties);
        BlockState defaultBlockState = this.stateDefinition.any().setValue(USE_WAVEFRONT_OBJ_MODEL, false);
        this.registerDefaultState(defaultBlockState);
    }


    // Called when the block is placed or loaded client side to get the tile entity for the block
    // Should return a new instance of the tile entity for the block
    @Override
    public BlockEntity newBlockEntity(BlockState state, LevelAccessor world) {return new TileRenderTester();}

    // Called just after the player places a block.  Sets the BlockEntity's colour
    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        BlockEntity BlockEntity = worldIn.getBlockEntity(pos);
        if (BlockEntity instanceof TileRenderTester) { // prevent a crash if not the right type, or is null
            TileRenderTester BlockEntityMBE21 = (TileRenderTester)BlockEntity;

            // chose a random colour for the artifact:
            Color [] colorChoices = {Color.BLUE, Color.CYAN, Color.YELLOW, Color.GREEN, Color.WHITE, Color.ORANGE, Color.RED};
            Random random = new Random();
            Color artifactColour = colorChoices[random.nextInt(colorChoices.length)];
            System.out.println(artifactColour);
            BlockEntityMBE21.setArtifactColour(artifactColour);

            // choose a random render style for the artifact:
            TileRenderTester.EnumRenderStyle renderStyle = TileRenderTester.EnumRenderStyle.pickRandom();
            BlockEntityMBE21.setArtifactRenderStyle(renderStyle);
        }
    }

    /**
     * When the player right-clicks the block, update it to the next render style
     */
    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos blockPos,
                                 Player playerEntity, InteractionHand hand, BlockHitResult rayTraceResult) {

        BlockEntity BlockEntity = world.getBlockEntity(blockPos);
        if (BlockEntity instanceof TileRenderTester) { // prevent a crash if not the right type, or is null
            TileRenderTester BlockEntityMBE21 = (TileRenderTester)BlockEntity;
            TileRenderTester.EnumRenderStyle renderStyle = BlockEntityMBE21.getArtifactRenderStyle();
            renderStyle = renderStyle.getNextStyle();
            System.out.println("Switch to " + renderStyle);
            BlockEntityMBE21.setArtifactRenderStyle(renderStyle);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;  // should never get here
    }

    // Used for visuals only, as an easy way to get Forge to load the obj model used by the WaveFront render style
    public static final Property<Boolean> USE_WAVEFRONT_OBJ_MODEL = BooleanProperty.create("use_wavefront_obj_model");

    /**
     * Defines the properties needed for the BlockState
     * @param builder
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(USE_WAVEFRONT_OBJ_MODEL);
    }


    // see MBE02 for more guidance on block VoxelShapes
    @Override
    public VoxelShape getShape(BlockGetter p_60809_, BlockPos p_60810_) {
        return FULL_SHAPE;
    }

    // Create block shapes which match the shape of the hopper (copied from HopperBlock)

    private static final VoxelShape INPUT_SHAPE = Block.box(0.0D, 10.0D, 0.0D,
            16.0D, 16.0D, 16.0D);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(4.0D, 4.0D, 4.0D,
            12.0D, 10.0D, 12.0D);
    private static final VoxelShape INPUT_PLUS_MIDDLE_SHAPE = Shapes.or(MIDDLE_SHAPE, INPUT_SHAPE);
    private static final VoxelShape INSIDE_BOWL_SHAPE = Block.box(2.0D, 11.0D, 2.0D,
            14.0D, 16.0D, 14.0D);
    private static final VoxelShape HOPPER_SHELL_SHAPE = Shapes.join(INPUT_PLUS_MIDDLE_SHAPE, INSIDE_BOWL_SHAPE, BooleanOp.ONLY_FIRST);
    private static final VoxelShape BOTTOM_HUB_SHAPE = Block.box(6.0D, 0.0D, 6.0D,
            10.0D, 4.0D, 10.0D);
    private static final VoxelShape FULL_SHAPE = Shapes.or(HOPPER_SHELL_SHAPE, BOTTOM_HUB_SHAPE);
}
