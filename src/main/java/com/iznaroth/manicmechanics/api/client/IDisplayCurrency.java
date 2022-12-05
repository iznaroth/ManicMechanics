package com.iznaroth.manicmechanics.api.client;

import net.minecraft.item.ItemStack;

public interface IDisplayCurrency {
    /**
     * If the held itemstack should display the currency
     */
    default boolean shouldDisplay(ItemStack stack){
        return true;
    }
}
