package com.iznaroth.m4.common.block;


import com.iznaroth.m4.common.blockentity.ManufactorumBlockEntity;
import com.iznaroth.m4.common.container.ManufactorumContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ManufactorumBlock extends Block implements EntityBlock {
    public static final String SCREEN_MANUFACTORUM = "m4.screen.manufactorum";

    public static final BooleanProperty BUTTON00 = BooleanProperty.create("button00");
    public static final BooleanProperty BUTTON01 = BooleanProperty.create("button01");
    public static final BooleanProperty BUTTON10 = BooleanProperty.create("button10");
    public static final BooleanProperty BUTTON11 = BooleanProperty.create("button11");

    private static final VoxelShape SHAPE_DOWN = Shapes.box(0, 2.0/16, 0, 1, 1, 1);
    private static final VoxelShape SHAPE_UP = Shapes.box(0, 0, 0, 1, 14.0/16, 1);
    private static final VoxelShape SHAPE_NORTH = Shapes.box(0, 0, 2.0/16, 1, 1, 1);
    private static final VoxelShape SHAPE_SOUTH = Shapes.box(0, 0, 0, 1, 1, 14.0/16);
    private static final VoxelShape SHAPE_WEST = Shapes.box(2.0/16, 0, 0, 1, 1, 1);
    private static final VoxelShape SHAPE_EAST = Shapes.box(0, 0, 0, 14.0/16, 1, 1);

    public ManufactorumBlock() {
        // Let our block behave like a metal block
        super(Properties.of()
                .strength(3.5F)
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ManufactorumBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable(SCREEN_MANUFACTORUM);
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new ManufactorumContainer(windowId, playerEntity, pos);
                    }
                };
                player.openMenu(containerProvider, buf -> buf.writeBlockPos(pos));
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        this.useWithoutItem(state, level, pos, player, hitResult);
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BlockStateProperties.FACING)) {
            case DOWN -> SHAPE_DOWN;
            case UP -> SHAPE_UP;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ManufactorumBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            // We don't have anything to do on the client side
            return null;
        } else {
            // Server side we delegate ticking to our block entity
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof ManufactorumBlockEntity be) {
                    be.tickServer();
                }
            };
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite())
                .setValue(BUTTON00, false)
                .setValue(BUTTON01, false)
                .setValue(BUTTON10, false)
                .setValue(BUTTON11, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BUTTON00, BUTTON01, BUTTON10, BUTTON11);
    }
}