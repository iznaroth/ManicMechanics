package com.iznaroth.manicmechanics.item;

import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MMItemGroup {

    public static final CreativeModeTab MM_ITEMS = new CreativeModeTab("mmItemTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMItems.DYSPERSIUM_DUST.get());
        }
    };

    public static final CreativeModeTab MM_RESOURCES = new CreativeModeTab("mmResourceTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMBlocks.DYSPERSIUM_ORE.get());
        }
    };

    public static final CreativeModeTab MM_MACHINES = new CreativeModeTab("mmMachineTab") { //Split into MACHINES, INTERMEDIATES, TOOLS, OTHER ?
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMBlocks.HEP.get());
        }
    };
}
