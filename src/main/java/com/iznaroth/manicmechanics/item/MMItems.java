package com.iznaroth.manicmechanics.item;

import com.iznaroth.manicmechanics.ManicMechanics;
//import com.iznaroth.manicmechanics.item.custom.ModSpawnEggItem;
import com.iznaroth.manicmechanics.entity.ModEntityTypes;
import com.iznaroth.manicmechanics.util.ModSoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMItems {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManicMechanics.MOD_ID);

    public static final RegistryObject<Item> ILLEGIBLE_TOME = ITEMS.register("illegible_tome",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DEVELOPMENT_ARCHIVE = ITEMS.register("development_archive",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //Tools
    public static final RegistryObject<Item> WIRE_CUTTER = ITEMS.register("wire_cutter",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS).durability(400)));

    public static final RegistryObject<Item> WALLET = ITEMS.register("wallet",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> LINK_TOOL = ITEMS.register("link_tool",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> REINFORCED_ARM = ITEMS.register("reinforced_arm",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_WIDGET = ITEMS.register("dyspersium_widget",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS).stacksTo(1)));

    public static final RegistryObject<Item> GRAVITY_HEART = ITEMS.register("gravity_heart",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> WIRELESS_COMMUNICATOR = ITEMS.register("wireless_communicator",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> AUTHORIZED_SECURITY_BRICK = ITEMS.register("security_brick",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));



    //Fun stuff
    public static final RegistryObject<Item> EVA_MUSIC_DISC = ITEMS.register("eva_music_disc",
            () -> new RecordItem(4, ModSoundEvents.EVA_MUSIC_DISC, new Item.Properties().tab(MMItemGroup.MM_ITEMS).stacksTo(1), 400));


    //Weapons - Tier I
    public static final RegistryObject<Item> PHOSPHOTHALLITE_CLEAVER = ITEMS.register("phosphothallite_cleaver",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> KINETIC_SLING = ITEMS.register("kinetic_sling",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> BOOMERANG_SHOVEL = ITEMS.register("boomerang_shovel",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //Intermediaries
    public static final RegistryObject<Item> PINCH = ITEMS.register("pinch",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> THALLITE_PLASTIC = ITEMS.register("thallite_plastic",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> TUBE_HOUSING = ITEMS.register("tube_housing",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> SEALANT = ITEMS.register("sealant",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //INGOTS - SMELTING
    public static final RegistryObject<Item> DYSPERSIUM_DUST = ITEMS.register("dyspersium_dust",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DUSTED_INGOT = ITEMS.register("dusted_ingot",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_ALLOY = ITEMS.register("dyspersium_alloy",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> OBAMIUM_INGOT = ITEMS.register("obamium_ingot",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> PHOSPHORITE_INGOT = ITEMS.register("phosphorite_ingot",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> STICKY_THALLITE = ITEMS.register("sticky_thallite",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> THALLITE_INGOT = ITEMS.register("thallite_ingot",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> LUMINOUS_PHOSPHOTHALLITE_MIXTURE = ITEMS.register("luminous_phosphothallite_mixture",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> NEUTRALIZED_PHOSPHOTHALLITE_MIXTURE = ITEMS.register("neutralized_phosphothallite_mixture",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> UNSTABLE_NITROL_CHUNK = ITEMS.register("volatile_nitrol_chunk",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> UNSTABLE_NITROL_INGOT = ITEMS.register("volatile_nitrol_ingot",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //Aspectus Crystals - Early
    public static final RegistryObject<Item> JAGGED_CRYSTAL = ITEMS.register("jagged_crystal",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> SLANTED_CRYSTAL = ITEMS.register("slanted_crystal",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> LANGUID_CRYSTAL = ITEMS.register("languid_crystal",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> BULBOUS_CRYSTAL = ITEMS.register("bulbous_crystal",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));



    //WIRING
    public static final RegistryObject<Item> GOLD_WIRE = ITEMS.register("gold_wire",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_WIRE = ITEMS.register("dyspersium_wire",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> PHOSPHORITE_FILAMENT = ITEMS.register("phosphorite_filament",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //PARTS
    public static final RegistryObject<Item> IRON_PART = ITEMS.register("iron_part",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    public static final RegistryObject<Item> GOLD_PART = ITEMS.register("gold_part",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_PART = ITEMS.register("dyspersium_part.json",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DYSPERSIUM_WELL = ITEMS.register("dyspersium_well",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //SPECIALIZERS
    public static final RegistryObject<Item> PNEUMATIC_PART = ITEMS.register("pneumatic_part",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> VOLTAGE_PART = ITEMS.register("voltage_part",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> COMMUNICATION_PART = ITEMS.register("communication_part",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> LOGISTIC_PART = ITEMS.register("logistic_part",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));



    //CUBES
    public static final RegistryObject<Item> PSEUDORACT = ITEMS.register("pseudoract",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> TESSERACT = ITEMS.register("tesseract",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> PENTARACT = ITEMS.register("pentaract",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //ENTITY
    //public static final RegistryObject<Item> COP_CAR_SPAWN_EGG = ITEMS.register("cop_car_spawn_egg",
    //        () -> new ModSpawnEggItem(ModEntityTypes.COP_CAR, 0x464F56, 0x1D6336, new Item.Properties().tab(ModItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> DIMENSION_STRETCHER = ITEMS.register("dimension_stretcher",
            () -> new ForgeSpawnEggItem(ModEntityTypes.PINCH, 0x464F56, 0x1D6336, new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //SPECIAL
    public static final RegistryObject<Item> DYSPERSIUM_BATTERY = ITEMS.register("dyspersium_battery",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> HYPERDENSE_ALLOY = ITEMS.register("hyperdense_alloy",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> BULB = ITEMS.register("bulb",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> STRENGTHENED_GLASS = ITEMS.register("strengthened_glass",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> SIMPLE_CIRCUIT = ITEMS.register("simple_circuit",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));

    public static final RegistryObject<Item> IRON_FAN = ITEMS.register("iron_fan",
            () -> new Item(new Item.Properties().tab(MMItemGroup.MM_ITEMS)));


    //MACHINE CORES


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
