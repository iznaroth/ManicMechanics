package com.iznaroth.manicmechanics.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryToolItem extends Item {
    public InventoryToolItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    //Item that can be used in-inventory at a durability cost.
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack)
    {
        setDamage(itemStack, itemStack.getDamageValue() - 1);
        return new ItemStack(itemStack.getItem(), 1);

    }



    @Override
    public boolean isDamageable(ItemStack stack){
        return true;
    }


    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack){
        return true;
    }

}
