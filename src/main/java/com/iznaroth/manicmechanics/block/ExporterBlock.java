package com.iznaroth.manicmechanics.block;


import com.iznaroth.manicmechanics.blockentity.ExporterBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class ExporterBlock extends MMBaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ExporterBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){ return new ExporterBlockEntity(pos, state); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.POWERED); //syntactically incorrect? tutorial used createBlockStateDefinition
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ExporterBlockEntity) {
                ((ExporterBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!world.isClientSide) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ExporterBlockEntity) {

                NetworkHooks.openScreen((ServerPlayer) player, (ExporterBlockEntity) blockEntity, blockEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        if(Screen.hasShiftDown()){
            list.add(Component.translatable("message.bureau.tooltip").withStyle(ChatFormatting.AQUA));
        } else {
            list.add(Component.translatable("message.manicmechanics.tooltip").withStyle(ChatFormatting.GRAY));
        }
    }


}
