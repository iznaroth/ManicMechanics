package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.item.BucketItemExtension;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.iznaroth.m4.common.registration.M4Blocks.*;

public class M4Items {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(M4.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(M4.MODID);

    //BLOCKITEMS
    //Note - This is a segment head.

    //Minerals
    //public static final DeferredItem<BlockItem> CRUCIS_QUARTZITE_BLOCK = ITEMS.registerSimpleBlockItem("crucis_quartzite_block", EXAMPLE_BLOCK);
    public static final DeferredItem<Item> DYSPERSIUM_ORE = BLOCK_ITEMS.register("dyspersium_ore", () -> new BlockItem(M4Blocks.DYSPERSIUM_ORE.get(), new Item.Properties()));
    public static final DeferredItem<Item> DEEPSLATE_DYSPERSIUM_ORE = BLOCK_ITEMS.register("deepslate_dyspersium_ore", () -> new BlockItem(M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.get(), new Item.Properties()));

    //Machines
    public static final DeferredItem<Item> OBLITERATION_PLINTH_ITEM = BLOCK_ITEMS.register("obliteration_plinth", () -> new BlockItem(OBLITERATION_PLINTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> MANUFACTORUM_ITEM = BLOCK_ITEMS.register("manufactorum", () -> new BlockItem(MANUFACTORUM.get(), new Item.Properties()));
    public static final DeferredItem<Item> MANIPULATOR_ITEM = BLOCK_ITEMS.register("manipulator", () -> new BlockItem(MANIPULATOR.get(), new Item.Properties()));
    public static final DeferredItem<Item> INOCULATOR_ITEM = BLOCK_ITEMS.register("inoculator", () -> new BlockItem(INOCULATOR.get(), new Item.Properties()));
    public static final DeferredItem<Item> HEPC_ITEM = BLOCK_ITEMS.register("hyperfield_extract_polarization_chamber", () -> new BlockItem(HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get(), new Item.Properties()));
    public static final DeferredItem<Item> CHARGING_STATION_ITEM = BLOCK_ITEMS.register("charging_station", () -> new BlockItem(CHARGING_STATION.get(), new Item.Properties()));
    public static final DeferredItem<Item> POWER_CABLE_ITEM = BLOCK_ITEMS.register("cable", () -> new BlockItem(POWER_CABLE_BLOCK.get(), new Item.Properties()));

    public static final DeferredItem<Item> PORTAL_DEOBFUSCATOR = BLOCK_ITEMS.register("portal_deobfuscator", () -> new BlockItem(M4Blocks.PORTAL_DEOBFUSCATOR.get(), new Item.Properties()));


    /*
    public static final DeferredItem<Item> SOURSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GOLDPOISONED_BLACKSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GOLDPOISONED_MARBLE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GOLDPOISONED_QUARTZ = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GOLDPOISONED_QUARTZ_PILLAR = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GOLDPOISONED_QUARTZ_STAIR = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DISCHARGED_SOULSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> BITTER_SOULSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> ANHEDONIC_SOULSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    //Flora
    public static final DeferredItem<Item> GOLDPOISONED_BIRCH_WOOD = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GOLDPOISONED_BIRCH_LEAVES = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> LACERATIVE_OAK_WOOD = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> KNIFELEAVES = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> LAMENTING_TOPSOIL = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    //

    //Rich Resource Microbiomes
    public static final DeferredItem<Item> ANTEMHORDIAN_INGRESS = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> ANTEMHORDIAN_GREATWOOD = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> ANTEMHORDIAN_LEAVES = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> WITHERING_VEIN = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> VEINED_SAND = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> PACKED_FINGER = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> AMBER_SANDSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> BLOODSAND = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> COAGULATED_SANDSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> FRACTAL_RELIQUARY = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> IRREGULAR_ICE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> ULTRADENSE_SNOW = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> ARCHAIC_FLOE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> NOSTALGIC_ICE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DISLOCATED_ICE = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> BRIAR_PETRIFACTION = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> STRANGLED_GRASS = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> WIRELEAF = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> DELICATE_CORALKNOT = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> BREATHING_SEABED = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CORAL_FINGER = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> VITREOUS_SPOUT = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> FOOLS_AQUAMARINE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> ROCKSAND = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    //Rich Resource Harvesters
    */

    //REGULAR ITEMS
    //Note - This is a segment head.
    //Items suffer most from multiresponsibility issues, so the organizational scheme is imperfect.
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    //Item Intermediaries - Resources | You get these when you break something.
    public static final DeferredItem<Item> STICKY_THALLITE = ITEMS.registerSimpleItem("sticky_thallite", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIUM_DUST = ITEMS.registerSimpleItem("dyspersium_dust", new Item.Properties());
    public static final DeferredItem<Item> DIRTY_DYSPERSIUM_VEIN = ITEMS.registerSimpleItem("dirty_dyspersium_vein", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIUM_PIT = ITEMS.registerSimpleItem("dyspersium_pit", new Item.Properties());
    public static final DeferredItem<Item> PHOTOKOPHRITE_CHUNK = ITEMS.registerSimpleItem("photokophrite_chunk", new Item.Properties());
    public static final DeferredItem<Item> VALEDRITE = ITEMS.registerSimpleItem("valedrite", new Item.Properties());
    public static final DeferredItem<Item> DUORODROMITE = ITEMS.registerSimpleItem("duorodromite", new Item.Properties());
    public static final DeferredItem<Item> GILLITHRITE = ITEMS.registerSimpleItem("gillithrite", new Item.Properties());
    public static final DeferredItem<Item> LARICIGNITE = ITEMS.registerSimpleItem("laricignite", new Item.Properties());
    public static final DeferredItem<Item> EXOTIC_COMPOSITE = ITEMS.registerSimpleItem("exotic_composite", new Item.Properties());
    public static final DeferredItem<Item> CRUCIS_QUARTZITE_FRAGMENT = ITEMS.registerSimpleItem("crucis_quartzite_fragment", new Item.Properties());

    public static final DeferredItem<Item> WHISPERING_WATER_BUCKET = ITEMS.register("whispering_water_bucket", () -> new BucketItem(M4Fluids.WHISPERING_WATER.get(), (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));

    //Items - Resources - Entity Drops | Can end up with some unexpected entires - ENTITY is anything !

/*
    //Item Intermediaries - Midprocessing / Inoculator-Primary - CPAK1 | NO ALLOYS, overreaches, stuff you see in machines while processing
    public static final DeferredItem<Item> SEMISOLID_THALLITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> PT_INTEGRATE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DENATURED_PSEUDOTHALLITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> PURIFIED_DYSPERSIUM_VEIN = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> INFLECTED_MATERIAL = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSERITE_SCRAP = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DYSPERTIC_SCULK_MOLODE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> FLASH_CRYSTALLIZED_DYSPERSIUM_AGITATE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> UNOPPOSED_DYSPERSIUM_LODE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CONTENTIOUS_DYSPERSIUM_LODE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> EPSILIC_DYSPERSIUN = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DYSPERTIC_TORMENT = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> MAUVE_VALEDRIUM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> ALABASTER_VALEDRIUM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> VALEDROGRAPHITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> MOLLIC_VALEDRIUM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> VALEDRIUM_CHUNK = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> CUPRODROMIC_LODE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DROMOCUPRIC_LODE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> JAGGED_DROMIUM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> MOTTLED_RED_DROMITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> BLOOD_COPPER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> IMPURE_DROMIUM_CHUNK = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> PURE_DROMIUM_CHUNK = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> FINE_DROMIUM_POWDER = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> TREATED_BRITTLE_GILLITHRITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GILLIMENE_GEODE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> G_L_ICEBALL = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> RIVERSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CALLIC_GILLIMENE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> STALLED_GILLIMENE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> FROZEN_GILLIMENE_CHUNK = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> INERT_GILLIMENE_ORE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> HYPERCOOLED_WHITESTONE_FRAGMENT = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> SHAPED_GILLIMENE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> FRIGID_GILLIMENE = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> CIGNIC_GOLD_BOLTSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> MIDNIGHT_CIGNUM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> SUNSET_CIGNUM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DISCHARGE_CIGNUM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CRACKED_FULGURITE_INTRUSIONS = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CIGNUM_CHUNK = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> IRIDESCENT_PEARL = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> BRAINSTONE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> SHUFFLING_SEDIMENT = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> HALLUCINATED_GEODE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> SUPERLIMINAL_COMPOSITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    public static final DeferredItem<Item> MAUVE_SILICATE_POWER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> MAROON_SILICATE_POWDER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> TEAL_SILICATE_POWDER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> MIDNIGHT_SILICATE_POWDER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GREY_SILICATE_POWDER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CRUSHED_LILTING_CRYSTALS = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CRUSHED_JAGGED_CRYSTALS = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CRUSHED_SLANTED_CRYSTALS = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CRUSHED_BULBOUS_CRYSTALS = ITEMS.registerSimpleItem("example_item", new Item.Properties());


    //Tools!
    public static final DeferredItem<Item> ETCHING_KNIFE = ITEMS.registerSimpleItem("etching_knife", new Item.Properties());
    public static final DeferredItem<Item> TOOLBOX = ITEMS.registerSimpleItem("toolbox", new Item.Properties());
    public static final DeferredItem<Item> STRANGE_WRENCH = ITEMS.registerSimpleItem("strange_wrench", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_PARAGAUNTLET = ITEMS.registerSimpleItem("dyspersiron_paragauntlet", new Item.Properties());

    public static final DeferredItem<Item> DYSPERSIRON_QUASI_PARALLAXE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> VALEDROKOPHRIC_PICKSAW = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CIGNICAN_PICKSAW = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> LUCID_FULGURITE_PICKSAW = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> SEMISAPIENT_DL_COMPELLED_AUTOTOOL = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> VLV_NEOSTABLE_PARALLAXE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> REGULATED_NITRIC_DETONATOR = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CHROMASTATIC_CC_AUTOHAMMER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DEVASTATOR = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GLOOMWARPED_SULFUROUS_GRINDER = ITEMS.registerSimpleItem("example_item", new Item.Properties());
*/

    //The Unorganized Sequence of Random Shit - for the First Five Machines
    public static final DeferredItem<Item> PHOTOKOPHRITE_INGOT = ITEMS.registerSimpleItem("photokophrite_ingot", new Item.Properties());
    public static final DeferredItem<Item> SOFT_COPHROLITE_INGOT = ITEMS.registerSimpleItem("soft_cophrolite_ingot", new Item.Properties());
    public static final DeferredItem<Item> COPHROLITE_INGOT = ITEMS.registerSimpleItem("cophrolite_ingot", new Item.Properties());
    public static final DeferredItem<Item> COPHROLITE_WIRE = ITEMS.registerSimpleItem("cophrolite_wire", new Item.Properties());

    public static final DeferredItem<Item> THALLIC_RUBBER = ITEMS.registerSimpleItem("thallic_rubber", new Item.Properties());
    public static final DeferredItem<Item> THALLIC_PLASTIC = ITEMS.registerSimpleItem("thallic_plastic", new Item.Properties());
    public static final DeferredItem<Item> COPHROLITE_CABLING = ITEMS.registerSimpleItem("cophrolite_cabling", new Item.Properties());

    public static final DeferredItem<Item> IRON_PLATE = ITEMS.registerSimpleItem("x1_iron_plate", new Item.Properties());
    public static final DeferredItem<Item> X2_IRON_PLATE = ITEMS.registerSimpleItem("x2_iron_plate", new Item.Properties());
    public static final DeferredItem<Item> IRON_SHEET = ITEMS.registerSimpleItem("iron_sheet", new Item.Properties());
    public static final DeferredItem<Item> IRON_ROD = ITEMS.registerSimpleItem("iron_rod", new Item.Properties());
    public static final DeferredItem<Item> IRON_SCREWS = ITEMS.registerSimpleItem("iron_screws", new Item.Properties());

    public static final DeferredItem<Item> DYSPERSIRON_PARALLINGOT = ITEMS.registerSimpleItem("dyspersiron_parallingot", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_SHEET = ITEMS.registerSimpleItem("dyspersiron_sheet", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_WIRE = ITEMS.registerSimpleItem("dyspersiron_wire", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_CABLING = ITEMS.registerSimpleItem("dyspersiron_cabling", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_ROD = ITEMS.registerSimpleItem("dyspersiron_rod", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_SCREWS = ITEMS.registerSimpleItem("dyspersiron_screws", new Item.Properties());

    public static final DeferredItem<Item> COBBLED_CORESTONE = ITEMS.registerSimpleItem("cobbled_corestone", new Item.Properties());
    public static final DeferredItem<Item> COOKED_CORESTONE = ITEMS.registerSimpleItem("cooked_corestone", new Item.Properties());
    public static final DeferredItem<Item> GRANITE_CORESTONE = ITEMS.registerSimpleItem("granite_corestone", new Item.Properties());
    public static final DeferredItem<Item> ANDESITE_CORESTONE = ITEMS.registerSimpleItem("andesite_corestone", new Item.Properties());
    public static final DeferredItem<Item> DIORITE_CORESTONE = ITEMS.registerSimpleItem("diorite_corestone", new Item.Properties());
    public static final DeferredItem<Item> DEEPSLATE_CORESTONE = ITEMS.registerSimpleItem("deepslate_corestone", new Item.Properties());
    public static final DeferredItem<Item> THREE_CUBE = ITEMS.registerSimpleItem("three_cube", new Item.Properties());
    public static final DeferredItem<Item> THREE_CORE = ITEMS.registerSimpleItem("three_core", new Item.Properties());

    public static final DeferredItem<Item> DISTORTED_BARK = ITEMS.registerSimpleItem("antemhordian_bark", new Item.Properties());
    public static final DeferredItem<Item> TPLAS_INSULATION = ITEMS.registerSimpleItem("tplas_insulation", new Item.Properties());
    public static final DeferredItem<Item> HH_HOUSING = ITEMS.registerSimpleItem("hh_housing", new Item.Properties());

    public static final DeferredItem<Item> INDUSTRIAL_HOUSING = ITEMS.registerSimpleItem("industrial_housing", new Item.Properties());
    public static final DeferredItem<Item> TR_INSULATION = ITEMS.registerSimpleItem("tr_insulation", new Item.Properties());
    public static final DeferredItem<Item> COPHROLITE_COIL = ITEMS.registerSimpleItem("cophrolite_coil", new Item.Properties());
    public static final DeferredItem<Item> COPHROLITE_INTAKE_PART = ITEMS.registerSimpleItem("cophrolite_intake_part", new Item.Properties());

    public static final DeferredItem<Item> DYSPERSIRON_MACHINE_COMPONENTS = ITEMS.registerSimpleItem("dyspersiron_machine_components", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_ARTICULATING_PART = ITEMS.registerSimpleItem("dyspersiron_articulating_parts", new Item.Properties());

    public static final DeferredItem<Item> TPLAS_BASEBOARD = ITEMS.registerSimpleItem("tplas_baseboard", new Item.Properties());
    public static final DeferredItem<Item> SOFTENED_GLASS = ITEMS.registerSimpleItem("softened_glass", new Item.Properties());
    public static final DeferredItem<Item> TUBE_MOLDSTONE = ITEMS.registerSimpleItem("tube_moldstone", new Item.Properties());
    public static final DeferredItem<Item> DYSPERTIC_FILAMENT = ITEMS.registerSimpleItem("dyspertic_filament", new Item.Properties());
    public static final DeferredItem<Item> DYSPERTIC_VACUUM_TUBE = ITEMS.registerSimpleItem("dyspertic_vacuum_tube", new Item.Properties());
    public static final DeferredItem<Item> SIMPLE_P_D_CIRCUIT = ITEMS.registerSimpleItem("simple_p_d_circuit", new Item.Properties());

    public static final DeferredItem<Item> INSCRIBED_DYSPERSIRON_SHEET = ITEMS.registerSimpleItem("inscribed_dyspersiron_sheet", new Item.Properties());
    public static final DeferredItem<Item> SHODDY_ENTRAPMENT_MATRIX = ITEMS.registerSimpleItem("shoddy_entrapment_matrix", new Item.Properties());

    public static final DeferredItem<Item> DYSPERSIRON_MOTOR_PART = ITEMS.registerSimpleItem("dyspersiron_motor_part", new Item.Properties());
    public static final DeferredItem<Item> INTERNAL_GANTRY = ITEMS.registerSimpleItem("internal_gantry", new Item.Properties());
    public static final DeferredItem<Item> DRILL = ITEMS.registerSimpleItem("drill", new Item.Properties());
    public static final DeferredItem<Item> HAMMER = ITEMS.registerSimpleItem("hammer", new Item.Properties());
    public static final DeferredItem<Item> CHISEL = ITEMS.registerSimpleItem("chisel", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_PROCESSING_PART = ITEMS.registerSimpleItem("dyspersiron_processing_part", new Item.Properties());

    public static final DeferredItem<Item> IRON_TANK = ITEMS.registerSimpleItem("iron_tank", new Item.Properties());
    public static final DeferredItem<Item> INSCRIBED_TANK = ITEMS.registerSimpleItem("inscribed_tank", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_PUMP_PART = ITEMS.registerSimpleItem("dyspersiron_pump_part", new Item.Properties());

    public static final DeferredItem<Item> BINDING_STONE_LIS = ITEMS.registerSimpleItem("binding_stone_lis", new Item.Properties());
    public static final DeferredItem<Item> BINDING_STONE_EKH = ITEMS.registerSimpleItem("binding_stone_ekh", new Item.Properties());
    public static final DeferredItem<Item> BINDING_STONE_YOL = ITEMS.registerSimpleItem("binding_stone_yol", new Item.Properties());
    public static final DeferredItem<Item> BINDING_STONE_MOA = ITEMS.registerSimpleItem("binding_stone_moa", new Item.Properties());





    public static final DeferredItem<Item> MANUFACTURERS_TOOLING_BIT = ITEMS.registerSimpleItem("manufacturers_tooling_bit", new Item.Properties());


}
