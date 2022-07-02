package com.iznaroth.industrizer.item;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.entity.ModEntityTypes;
import com.iznaroth.industrizer.item.custom.ModSpawnEggItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrizerMod.MOD_ID);

    //Tools
    public static final RegistryObject<Item> WIRE_CUTTER = ITEMS.register("wire_cutter",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> WALLET = ITEMS.register("wallet",
            () -> new WalletItem(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    //Intermediaries
    public static final RegistryObject<Item> PINCH = ITEMS.register("pinch",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> UNETCHED_CIRCUIT_WOOD = ITEMS.register("unetched_circuit_wood",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    //INGOTS - SMELTING
    public static final RegistryObject<Item> DYSPERSIUM_DUST = ITEMS.register("dyspersium_dust",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> DUSTED_INGOT = ITEMS.register("dusted_ingot",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_ALLOY = ITEMS.register("dyspersium_alloy",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> OBAMIUM_INGOT = ITEMS.register("obamium_ingot",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    //WIRING
    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> GOLD_WIRE = ITEMS.register("gold_wire",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_WIRE = ITEMS.register("dyspersium_wire",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    //PARTS
    public static final RegistryObject<Item> SIMPLE_PART = ITEMS.register("simple_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> IRON_PART = ITEMS.register("iron_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> COPPER_PART = ITEMS.register("copper_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_PART = ITEMS.register("dyspersium_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));


    //SPECIALIZERS
    public static final RegistryObject<Item> PNEUMATIC_PART = ITEMS.register("pneumatic_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> VOLTAGE_PART = ITEMS.register("voltage_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> COMMUNICATION_PART = ITEMS.register("communication_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> LOGISTIC_PART = ITEMS.register("logistic_part",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    //CORES
    public static final RegistryObject<Item> REPLICATION_CORE = ITEMS.register("replication_core",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> DESTRUCTION_CORE = ITEMS.register("destruction_core",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> MODULATION_CORE = ITEMS.register("modulation_core",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> TRANSMUTATION_CORE = ITEMS.register("transmutation_core",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    //CUBES
    public static final RegistryObject<Item> PSEUDORACT = ITEMS.register("pseudoract",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    //ENTITY
    public static final RegistryObject<Item> COP_CAR_SPAWN_EGG = ITEMS.register("cop_car_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.COP_CAR, 0x464F56, 0x1D6336, new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));


    //OTHERS
    public static final RegistryObject<Item> DYSPERSIUM_BATTERY = ITEMS.register("dyspersium_battery",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> BULB = ITEMS.register("bulb",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> STRENGTHENED_GLASS = ITEMS.register("strengthened_glass",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));

    public static final RegistryObject<Item> SIMPLE_CIRCUIT = ITEMS.register("simple_circuit",
            () -> new Item(new Item.Properties().tab(ModItemGroup.INDUSTRIZER_ITEMS)));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
