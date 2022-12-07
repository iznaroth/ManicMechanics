package com.iznaroth.manicmechanics.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MMBlockWrapperDebug extends Block {
    public MMBlockWrapperDebug(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        System.out.println("Call pSD on " + this.getName());
        return !isShapeFullBlock(p_49928_.getShape(p_49929_, p_49930_)) && p_49928_.getFluidState().isEmpty();
    }

    @Override
    public void fillItemCategory(CreativeModeTab p_49812_, NonNullList<ItemStack> p_49813_) {
        System.out.println("Fill inv for " + this.getName() + ". Is this null?" + new ItemStack(this));
        p_49813_.add(new ItemStack(this));
        System.out.println("Filled inv for " + this.getName());
    }
    //This was used to troubleshoot a registration/blockstate error.
}
