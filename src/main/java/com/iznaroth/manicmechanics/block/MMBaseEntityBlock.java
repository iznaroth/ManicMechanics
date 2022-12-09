package com.iznaroth.manicmechanics.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MMBaseEntityBlock extends BaseEntityBlock {

    //Default MM implementations and extensions for BlockEntities.

    public MMBaseEntityBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        //System.out.println("Call pSD on " + this.getName());
        return !isShapeFullBlock(p_49928_.getShape(p_49929_, p_49930_)) && p_49928_.getFluidState().isEmpty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }

    @Override
    public void fillItemCategory(CreativeModeTab p_49812_, NonNullList<ItemStack> p_49813_) {
        //System.out.println("Fill inv for " + this.getName() + ". Is this null?" + new ItemStack(this));
        p_49813_.add(new ItemStack(this));
        //System.out.println("Fill inv for " + this.getName());
    }
    //This was used to troubleshoot a registration/blockstate error.

    @Override //BEs assume nothing about their shape, unlike normal blocks. Default behavior should be MODEL.
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }
}
