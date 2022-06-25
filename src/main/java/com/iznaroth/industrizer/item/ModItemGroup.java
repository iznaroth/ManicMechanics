package com.iznaroth.industrizer.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup INDUSTRIZER_GROUP = new ItemGroup("industrizerModTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.DYSPERSIUM_DUST.get());
        }
    };
}
