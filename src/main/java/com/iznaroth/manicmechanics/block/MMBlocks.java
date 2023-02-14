package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.tube.*;

import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.item.MMItemGroup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MMBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ManicMechanics.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ManicMechanics.MOD_ID);

    //WORLDGEN
    public static final RegistryObject<Block> DYSPERSIUM_ORE = registerBlock("dyspersium_ore", () -> new DyspersiumOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> DYSPERSIUM_DEEPSLATE_ORE = registerBlock("dyspersium_deepslate_ore", () -> new DyspersiumOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);

    public static final RegistryObject<Block> THALLITE_ORE = registerBlock("thallite_ore", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);

    public static final RegistryObject<Block> PHOSPHORITE_ORE = registerBlock("phosphorite_ore", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> PHOSPHORITE_DEEPSLATE_ORE = registerBlock("phosphorite_deepslate_ore", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);

    public static final RegistryObject<Block> NITROL_ORE = registerBlock("nitrol_ore", () -> new NitrolOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> NITROL_DEEPSLATE_ORE = registerBlock("nitrol_deepslate_ore", () -> new NitrolOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);



    public static final RegistryObject<Block> OBAMIUM_ORE = registerBlock("obamium_ore", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> OBAMIUM_BLOCK = registerBlock("obamium_block", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.2f)), MMItemGroup.MM_RESOURCES);


    //NO POWER
    public static final RegistryObject<Block> ELECTROSTATIC_COMMUNICATION_PYLON = registerBlock("ecp", () -> new ECPBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> SIMPLE_COMMUNICATOR = registerBlock("simple_communicator", () -> new SimpleCommunicatorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);


    //MACHINES (CORE - TIER I)
    public static final RegistryObject<Block> HEP = registerBlock("hep", () -> new GeneratorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0).strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> EXPORTER  = registerBlock("exporter", () -> new ExporterBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> IMPORTER  = registerBlock("importer", () -> new ImporterBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);

    public static final RegistryObject<Block> SEALER = registerBlock("sealing_chamber", () -> new SealingChamberBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);

    //public static final RegistryObject<Block> ELECTRIC_FURNACE = registerBlock("electric_furnace", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> CONDENSER = registerBlock("condenser", () -> new CondenserBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> ASSEMBLER = registerBlock("assembler", () -> new AssemblerBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> ETCHER = registerBlock("etcher", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> INFUSER = registerBlock("infuser", () -> new InfuserBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    //public static final RegistryObject<Block> DEMYSTIFIER = registerBlock("demystifier", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    //public static final RegistryObject<Block> ALCHEMIZER = registerBlock("alchemizer", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    //public static final RegistryObject<Block> REEXTENDER = registerBlock("reextender", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> COMMUNICATOR = registerBlock("communicator", () -> new CommunicatorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);

    //MAGIC TIER 1
    public static final RegistryObject<Block> ENERVATOR = registerBlock("enervator", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> LRCU = registerBlock("lrcu", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> MANAMULCHER = registerBlock("manamulcher", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> CRYSTALLIZER = registerBlock("crystallizer", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> HOLYFORGE = registerBlock("holyforge", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> INTERPRETER = registerBlock("interpreter", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> MORTON_CONTROLLER = registerBlock("morton_controller", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);

    public static final RegistryObject<Block> STONEFINGER_BATTERY = registerBlock("stonefinger_battery", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> BLANK_FOCUS = registerBlock("blank_focus", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> SEETHING_FOCUS = registerBlock("seething_focus", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> LILTING_FOCUS = registerBlock("lilting_focus", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> MURMURING_FOCUS = registerBlock("murmuring_focus", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> EBBING_FOCUS = registerBlock("ebbing_focus", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);


    //RICH RESOURCES AND HARVESTERS
    public static final RegistryObject<Block> AMBER_SANDSTONE = registerBlock("amber_sandstone", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> WITHERING_VEIN = registerBlock("withering_vein", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> FRACTAL_RELIQUARY = registerBlock("fractal_reliquary", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> ARCHAIC_FLOE = registerBlock("archaic_floe", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> BRIAR_PETRIFACTION = registerBlock("briar_petrifaction", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> ANTE_BARK = registerBlock("ante_bark", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> DELICATE_CORALKNOT = registerBlock("delicate_coralknot", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> VITREOUS_SPOUT = registerBlock("vitreous_spout", () -> new MMBlockWrapper(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_RESOURCES);

    public static final RegistryObject<Block> SUPERTAP = registerBlock("supertap", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> DRILL_GANTRY = registerBlock("drill_gantry", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> PSEUDOMORTAL_CB = registerBlock("pseudomortal_cb", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> ATMOSPHERIC_RA = registerBlock("atmospheric_ra", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f)), MMItemGroup.MM_MACHINES);


    public static final RegistryObject<Block> JAGGED_CRYSTAL = registerBlock("jagged_crystal", () -> new ChargableCrystalBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> SLANTED_CRYSTAL = registerBlock("slanted_crystal", () -> new ChargableCrystalBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> LANGUID_CRYSTAL = registerBlock("languid_crystal", () -> new ChargableCrystalBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_RESOURCES);
    public static final RegistryObject<Block> BULBOUS_CRYSTAL = registerBlock("bulbous_crystal", () -> new ChargableCrystalBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_RESOURCES);

    public static final RegistryObject<Block> RUBY_NODE_POINTER = registerBlock("ruby_node_pointer", () -> new NodePointerBlock(BlockBehaviour.Properties.of(Material.AMETHYST).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_RESOURCES);


    //RESEARCH
    public static final RegistryObject<Block> MULTITHREADED_INSPECTOR = registerBlock("mmi", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f).noOcclusion()), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> SYNCHRONIZER = registerBlock("synchronizer", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f).noOcclusion()), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> DECRYPTION_STATION = registerBlock("mds", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.5f).noOcclusion()), MMItemGroup.MM_MACHINES);


    public static final RegistryObject<Block> TUBE_BUNDLE = registerBlock("tube_bundle", () -> new TubeBundleBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> BELT = registerBlock("belt", () -> new BeltBlock(BlockBehaviour.Properties.of(Material.METAL).strength(5).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_MACHINES);


    //public static final RegistryObject<Block> RENDER_TESTER = registerBlock("render_tester", () -> new BlockRenderTester(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f).noOcclusion()), ModItemGroup.MM_MACHINES);

    public static final RegistryObject<Block> BOUNDING_TESTER = registerBlock("bounding_tester", () -> new BoundingBoxTesting(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_MACHINES);


    //LOGISTICS
    public static final RegistryObject<Block> VACUUM_HIGHWAY_SEGMENT = registerBlock("vacuum_highway_segment", () -> new VacuumHighwaySegmentBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> HIGHWAY_CONTROLLER = registerBlock("highway_controller", () -> new HighwayControllerBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f)), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> TRANSPORT_TUBE = registerBlock("transport_tube", () -> new TransportTubeBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> FLUID_TUBE = registerBlock("fluid_tube", () -> new FluidTubeBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> GAS_TUBE = registerBlock("gas_tube", () -> new GasTubeBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_MACHINES);
    public static final RegistryObject<Block> POWER_TUBE = registerBlock("power_tube", () -> new PowerTubeBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10f).noOcclusion()), MMItemGroup.MM_MACHINES);



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        MMItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }

}
