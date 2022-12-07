package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.blockentity.GeneratorBlockEntity;
import com.iznaroth.manicmechanics.blockentity.HighwayControllerBlockEntity;
import com.iznaroth.manicmechanics.blockentity.MMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HighwayControllerBlock extends BaseEntityBlock {

    public HighwayControllerBlock(Properties properties) {
        super(properties);
    }

    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){ return new HighwayControllerBlockEntity(pos, state); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED); //syntactically incorrect? tutorial used createBlockStateDefinition
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        Component msg = Component.literal("Attempting to create Vacuum Highway from this controller. Right click the block again to continue.");

        System.out.println("Clicked Highway Controller");

        if(stack.equals(new ItemStack(MMBlocks.VACUUM_HIGHWAY_SEGMENT.get()))){
            player.sendSystemMessage(msg);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type){
        return createTickerHelper(type, MMBlockEntities.HIGHWAY_CONTROLLER_TILE.get(), HighwayControllerBlockEntity::tick);
    }

}
