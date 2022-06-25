package com.iznaroth.industrizer.item;

import com.iznaroth.industrizer.IndustrizerMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrizerMod.MOD_ID);

    public static final RegistryObject<Item> DYSPERSIUM_DUST = ITEMS.register("dyspersium_dust",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_GROUP)));

    public static final RegistryObject<Item> DUSTED_INGOT = ITEMS.register("dusted_ingot",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_GROUP)));

    public static final RegistryObject<Item> DYSPERSIUM_ALLOY = ITEMS.register("dyspersium_alloy",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_GROUP)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
