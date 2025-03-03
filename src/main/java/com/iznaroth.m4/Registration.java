package com.iznaroth.m4;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.*;

import javax.xml.transform.Source;

public class Registration {
    // Create a Deferred Register to hold Blocks which will all be registered under the "m4" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(m4.MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "m4" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(m4.MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "m4" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, m4.MODID);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, m4.MODID);

    // BLOCKS
    //Note - This is a segment head.
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    /*
    //Natural Resources

    //Placable Intermediaries

    //Logistics & Power I
    public static final DeferredBlock<Block> SOLID_TRANSPORT_TUBE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> LIQUID_TRANSPORT_TUBE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> GASEOUS_TRANSPORT_TUBE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> COPHROLITE_CABLE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    //MACHINE BLOCKS - Basics!
    public static final DeferredBlock<Block> LOW_RITUAL_CONVERSION_UNIT = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MORBID_SOULSTONE_ENERVATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> COPHROLITE_BUFFER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MANUFACTORUM = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MANIPULATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> INOCULATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> CHARGING_STATION = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> CONSTRUCTIVE_KINESIS_WORKBENCH = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> OPERATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> IRRIGATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> DISTRIBUTOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> IMPROVISED_COMMUNICATION_RELAY = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> DELIVERY_PLATFORM = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    //MACHINE BLOCKS - The Free Market
    public static final DeferredBlock<Block> EXPORTER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> IMPORTER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> PSEUDOEXPLOSIVE_VOLATILE_CATALYSIS_CENTRIFUGE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MULTITHREADED_MATERIAL_INSPECTOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MACRODATA_DECRYPTION_STATION = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SYNCHRONIZER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MORTONS_THEORETICAL_DEPHASING_STATION_CONTROLLER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> DIFFUSE_ISOMORPHIC_PARACRUCIBLE_CONTROLLER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> PRECISION_MICROASSEMBLY_PLATFORM_CONTROLLER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SEMISTABLE_ACTION_REDUCTION_MATRIX = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> IMPROVED_COMMUNICATION_RELAY = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SHIPPING_BUOY_CORE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SEMISINGULARITY_ATTRACTOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SEMISINGULARITY_REPULSOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    //MACHINE BLOCKS - Crystalline Echoes
    public static final DeferredBlock<Block> GRAVITY_FURNACE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> RAKTOMIC_CRYSTALLIZER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SALIPSURIAN_SULFUR_ENGINE_PROTOTYPE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> ALCHEMICAL_STABILIZER_EKH = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> ALCHEMICAL_STABILIZER_MOA = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> ALCHEMICAL_STABILIZER_LIS = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> ALCHEMICAL_STABILIZER_YUL = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SOURSTONE_BASING = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SOURSTONE_SUPPORT = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SOURING_TABLE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> LACERATIVE_HOUSING = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> FRAMED_BATH = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> GROWTH_LATTICE_PART = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> CHANCELLORS_HOLYFORGE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> STONEFINGER_BATTERY = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> NEUTRAL_FOCUS = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> EKHIC_FOCUS = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MOARRAN_FOCUS = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> LISSIC_FOCUS = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> YULAN_FOCUS = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> DEEPSTONE_ETCHER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> RETENTION_MATRIX = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SAPPHIRIC_NODE_POINTER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> EMERALDINE_NODE_POINTER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> RUBIDIC_NODE_POINTER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MATERLIZATION_PLINTH = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> OBLITERATION_PLINTH = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));


    //Logistics & Power II
    public static final DeferredBlock<Block> CIGNOKOPHRITE_CABLE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> CIGNOKOPHRITE_BUFFER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));


    //MACHINE BLOCKS - The Golden Age
    public static final DeferredBlock<Block> METAMATERIAL_MACERATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> TRAGMOORS_SPIKE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> LIQUID_DIPLOCOMPRESSOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SUBSTRUCTURAL_REEXTENDER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MICROPARTICULATE_MIRACULATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> FINE_GRIT_REINCORPORATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> THRACKING_PYLON = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> LOW_RESISTANCE_LIDOREACTOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    //MACHINE BLOCKS - Advanced Research
    public static final DeferredBlock<Block> LAQUIAN_LG_ACTION_REDUCTION_MATRIX = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> LOGOMANIACAL_FISSILE_REACTION_CHAMBER_CONTROLLER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> QUASIPARALLEL_TRENCH_VACUUM_TAP_CONTROLLER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> MANIC_MANAMULCHER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> INTERPRETER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> ATMOSPHERIC_PSYCHOSTABILIZER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SATIATION_SPOKE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> INTERFERENCE_MONITOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));


    //MACHINE BLOCKS - Rich Resources
    public static final DeferredBlock<Block> SUPERRESISTANT_MECHANICAL_TAP = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> HYPERCOMPRESSION_DRILL_GANTRY = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> PSEUDOMORTAL_CONTAINMENT_BEACON = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> ATMOSPHERIC_REALIZATION_ARRAY = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));


    //MACHINE BLOCKS - Multiblocks
    public static final DeferredBlock<Block> BANDED_DYSPERSIRON_MACHINE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> DENSE_DROMIUM_MACHINE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> CRYOSTABLE_GILLIMENE_MACHINE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> SAPPHIRIC_CIGNUM_MACHINE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> POWER_INTAKE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> ITEM_INTAKE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> POWER_OUTPUT = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> IRON_OUTPUT = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> DROMODYSPERTIC_PORT_COVER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    //Logistics & Power II
    public static final DeferredBlock<Block> VITALISTIC_CIGNUM_ANTIMETAL_CABLE = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> VITALISTIC_CIGNUM_ANTIMETAL_BUFFER = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    //MACHINE BLOCKS - CPAK1 Other
    public static final DeferredBlock<Block> PILFERED_TRIANGULATOR = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> PILFERED_WORKBENCH = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));


    //GENERATED STRUCTURE BLOCKS
    public static final DeferredBlock<Block> XACROGLASS = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> KPL_SHEET_METAL = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> KPL_FAN_PORT = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<Block> GOLDPOISONED_ = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));



     */

    // BLOCKENTITIES
    //Note - This is a segment head.

    //BLOCKITEMS
    //Note - This is a segment head.

    //Minerals
    public static final DeferredItem<BlockItem> CRUCIS_QUARTZITE_BLOCK = ITEMS.registerSimpleBlockItem("crucis_quartzite_block", EXAMPLE_BLOCK);

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

    //REGULAR ITEMS
    //Note - This is a segment head.
    //Items suffer most from multiresponsibility issues, so the organizational scheme is imperfect.
    */
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

    //Fluids!
    //Note - This is a segment head.
    //Fluids are relatively unorganized because they are less shorthandy and less common than the above entries.
    //We'd be better off building the helper independently and disguising this here, a.la the Mekanism implementation.
    //...because you need to register the base, the flowing, the bucket, the block.

    public static final DeferredHolder<FluidType, FluidType> WHISPERING_WATER_TYPE = DeferredHolder.create(NeoForgeRegistries.Keys.FLUID_TYPES, ResourceLocation.withDefaultNamespace("milk"));
    public static final DeferredHolder<Fluid, Fluid> WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("milk"));
    public static final DeferredHolder<Fluid, Fluid> FLOWING_WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("flowing_milk"));

    // Creates a creative tab with the id "m4:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> M4_BLOCKS = CREATIVE_MODE_TABS.register("m4_locks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.m4")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public static void init(IEventBus modEventBus){
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    // Add the example block item to the building blocks tab
    static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(CRUCIS_QUARTZITE_BLOCK);
    }
}
