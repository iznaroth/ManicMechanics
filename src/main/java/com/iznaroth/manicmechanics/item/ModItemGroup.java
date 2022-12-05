package com.iznaroth.manicmechanics.item;

import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup MM_ITEMS = new ItemGroup("mmItemTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMItems.DYSPERSIUM_DUST.get());
        }
    };

    public static final ItemGroup MM_RESOURCES = new ItemGroup("mmResourceTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMBlocks.DYSPERSIUM_ORE.get());
        }
    };

    public static final ItemGroup MM_MACHINES = new ItemGroup("mmMachineTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMBlocks.HEP.get());
        }
    };
}