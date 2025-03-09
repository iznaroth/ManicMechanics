package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class M4CreativeTabs {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "m4" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, M4.MODID);

    // Creates a creative tab with the id "m4:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> M4_BLOCKS = CREATIVE_MODE_TABS.register("m4_blocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.m4")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> M4Items.HEPC_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(M4Items.MANUFACTORUM_ITEM.get());
                output.accept(M4Items.CHARGING_STATION_ITEM.get());
                output.accept(M4Items.HEPC_ITEM.get());
                output.accept(M4Items.OBLITERATION_PLINTH_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    // Add the example block item to the building blocks tab
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(M4Blocks.MANUFACTORUM);
    }
}
