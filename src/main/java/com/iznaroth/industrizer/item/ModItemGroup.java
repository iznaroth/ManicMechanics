package com.iznaroth.industrizer.item;

import com.iznaroth.industrizer.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup INDUSTRIZER_ITEMS = new ItemGroup("industrizerItemTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.DYSPERSIUM_DUST.get());
        }
    };

    public static final ItemGroup INDUSTRIZER_RESOURCES = new ItemGroup("industrizerResourceTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.DYSPERSIUM_ORE.get());
        }
    };

    public static final ItemGroup INDUSTRIZER_MACHINES = new ItemGroup("industrizerMachineTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.HEP.get());
        }
    };
}
