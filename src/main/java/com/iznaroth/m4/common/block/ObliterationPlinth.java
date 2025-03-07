package com.iznaroth.m4.common.block;

import com.iznaroth.m4.common.blockentity.ObliterationPlinthBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class ObliterationPlinth extends Block implements EntityBlock {
    public ObliterationPlinth() {
        // Let our block behave like a metal block
        super(BlockBehaviour.Properties.of()
                .strength(3.5F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
    }

    // Our block has an associated block entity. This method from EntityBlock is used to create that block entity
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ObliterationPlinthBlockEntity(pos, state);
    }

    // This method is used to create a BlockEntityTicker for our block entity. This ticker can be used to perform certain actions every tick
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            // We don't have anything to do on the client side
            return null;
        } else {
            // Server side we delegate ticking to our block entity
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof ObliterationPlinthBlockEntity be) {
                    be.tickServer();
                }
            };
        }
    }
}
