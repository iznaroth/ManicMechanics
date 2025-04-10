package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Predicate;

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
                output.accept(M4Items.POWER_CABLE_ITEM.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> M4_ITEMS = CREATIVE_MODE_TABS.register("m4_items", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.m4")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> M4Items.DYSPERSIRON_PARALLINGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                addToDisplay(M4Items.ITEMS.getEntries(), output);
                addToDisplay(M4Items.BLOCK_ITEMS.getEntries(), output);
            }).build());

    @SafeVarargs
    public static void addToDisplay(CreativeModeTab.Output output, Holder<Item>... items) {
        CreativeModeTab.TabVisibility visibility;
        if (output instanceof BuildCreativeModeTabContentsEvent) {
            //If we are added from the event, only add the item to the parent tab, as we will already be contained in the search tab
            // from when we are adding to our tabs
            visibility = CreativeModeTab.TabVisibility.PARENT_TAB_ONLY;
        } else {
            visibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
        }
        for (Holder<Item> item : items) {
            Item itemLike = item.value();
            output.accept(itemLike, visibility);
        }
    }

    public static void addToDisplay(Collection<? extends Holder<Item>> items, CreativeModeTab.Output output) {
        for (Holder<Item> itemProvider : items) {
            addToDisplay(output, itemProvider);
        }
    }

    // Add the example block item to the building blocks tab
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(M4Blocks.MANUFACTORUM);
    }
}
