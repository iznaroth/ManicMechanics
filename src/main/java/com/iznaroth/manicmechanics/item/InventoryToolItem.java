package com.iznaroth.manicmechanics.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class InventoryToolItem extends Item {
    public InventoryToolItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    //Item that can be used in-inventory at a durability cost.
    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        setDamage(itemStack, itemStack.getDamageValue() - 1);
        return new ItemStack(itemStack.getItem(), 1);

    }



    @Override
    public boolean isDamageable(ItemStack stack){
        return true;
    }


    @Override
    public boolean hasContainerItem(ItemStack stack){
        return true;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }
}
