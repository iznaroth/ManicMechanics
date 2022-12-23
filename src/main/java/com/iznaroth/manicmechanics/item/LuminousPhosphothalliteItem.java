package com.iznaroth.manicmechanics.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class LuminousPhosphothalliteItem extends Item {
    public LuminousPhosphothalliteItem(Properties p_41383_) {
        super(p_41383_);
    }

    //@Override
    //public boolean hasCustomEntity(ItemStack stack) {
    //    return stack.getItem() == MMItems.LUMINOUS_PHOSPHOTHALLITE_MIXTURE.get();
        //Overly-defensive.
    //}

    //@Override
    //public @Nullable Entity createEntity(Level level, Entity location, ItemStack stack) {
    //    return new WaterSensitiveItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
    //}

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.getLevel();

        if(level.getBlockState(new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ())).is(Blocks.WATER)){
            entity.setItem(new ItemStack(MMItems.NEUTRALIZED_PHOSPHOTHALLITE_MIXTURE.get(), stack.getCount()));
            System.out.println("Water check passed.");
        }

        return super.onEntityItemUpdate(stack, entity);
    }
}
