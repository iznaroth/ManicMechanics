package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.iznaroth.m4.common.registration.M4Blocks.*;

public class M4Items {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(M4.MODID);

    //BLOCKITEMS
    //Note - This is a segment head.

    //Minerals
    //public static final DeferredItem<BlockItem> CRUCIS_QUARTZITE_BLOCK = ITEMS.registerSimpleBlockItem("crucis_quartzite_block", EXAMPLE_BLOCK);
    public static final DeferredItem<Item> OBLITERATION_PLINTH_ITEM = ITEMS.register("obliteration_plinth", () -> new BlockItem(OBLITERATION_PLINTH.get(), new Item.Properties()));

    public static final DeferredItem<Item> MANUFACTORUM_ITEM = ITEMS.register("manufactorum", () -> new BlockItem(MANUFACTORUM.get(), new Item.Properties()));
    public static final DeferredItem<Item> HEPC_ITEM = ITEMS.register("hyperfield_extract_polarization_station", () -> new BlockItem(HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get(), new Item.Properties()));
    public static final DeferredItem<Item> CHARGING_STATION_ITEM = ITEMS.register("charging_station", () -> new BlockItem(CHARGING_STATION.get(), new Item.Properties()));


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
    /*
    //Item Intermediaries - Resources | You get these when you break something.
    public static final DeferredItem<Item> STICKY_THALLITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIUM_DUST = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DIRTY_DYSPERSIUM_VEIN = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIUM_PIT = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> PHOTOKOPHRITE_CHUNK = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> VALEDRITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DUORODROMITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> GILLITHRITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> LARICIGNITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> EXOTIC_COMPOSITE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CRUCIS_QUARTZITE_FRAGMENT = ITEMS.registerSimpleItem("example_item", new Item.Properties());

    //Items - Resources - Entity Drops | Can end up with some unexpected entires - ENTITY is anything !


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
    public static final DeferredItem<Item> ETCHING_KNIFE = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> HAMMER_MULTITOOL = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> CUTTER_MULTITOOL = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> TONGS = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> DYSPERSIRON_PARAGAUNTLET = ITEMS.registerSimpleItem("example_item", new Item.Properties());

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

}
