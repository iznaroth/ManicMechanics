package com.iznaroth.industrizer.block;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.block.tube.*;
import com.iznaroth.industrizer.item.ModItemGroup;
import com.iznaroth.industrizer.item.IndustrizerItems;
import com.iznaroth.industrizer.render.BlockRenderTester;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class IndustrizerBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrizerMod.MOD_ID);
    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, IndustrizerMod.MOD_ID);

    //WORLDGEN
    public static final RegistryObject<Block> DYSPERSIUM_ORE = registerBlock("dyspersium_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(3.2f)), ModItemGroup.INDUSTRIZER_RESOURCES);
    public static final RegistryObject<Block> COPPER_ORE = registerBlock("copper_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(3.2f)), ModItemGroup.INDUSTRIZER_RESOURCES);

    public static final RegistryObject<Block> OBAMIUM_ORE = registerBlock("obamium_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(3.2f)), ModItemGroup.INDUSTRIZER_RESOURCES);
    public static final RegistryObject<Block> OBAMIUM_BLOCK = registerBlock("obamium_block", () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(3.2f)), ModItemGroup.INDUSTRIZER_RESOURCES);


    //NO POWER
    public static final RegistryObject<Block> BENCH_ENGINE = registerBlock("bench_engine", () -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);


    //MACHINES (CORE - TIER I)
    public static final RegistryObject<Block> HEP = registerBlock("hep", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0).harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> CURRENCY_BUREAU = registerBlock("currency_bureau", () -> new BureauBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);

    public static final RegistryObject<Block> INDUSTRIAL_FURNACE = registerBlock("industrial_furnace", () -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> CONDENSER = registerBlock("condenser", () -> new UpgradableMachine(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> ASSEMBLER = registerBlock("assembler", () -> new UpgradableMachine(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> ETCHER = registerBlock("etcher", () -> new UpgradableMachine(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> INFUSER = registerBlock("infuser", () -> new UpgradableMachine(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> DEMYSTIFIER = registerBlock("demystifier", () -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> ALCHEMIZER = registerBlock("alchemizer", () -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> REEXTENDER = registerBlock("reextender", () -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> COMMUNICATOR = registerBlock("communicator", () -> new CommunicatorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);

    public static final RegistryObject<Block> EXCHANGE_CENTER = registerBlock("exchange_center", () -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)), ModItemGroup.INDUSTRIZER_MACHINES);

    //LOGISTICS
    public static final RegistryObject<Block> VACUUM_HIGHWAY_SEGMENT = registerBlock("vacuum_highway_segment", () -> new VacuumHighwaySegmentBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> HIGHWAY_CONTROLLER = registerBlock("highway_controller", () -> new HighwayControllerBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f)), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> TRANSPORT_TUBE = registerBlock("transport_tube", () -> new TransportTubeBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> FLUID_TUBE = registerBlock("fluid_tube", () -> new FluidTubeBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> GAS_TUBE = registerBlock("gas_tube", () -> new GasTubeBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> POWER_TUBE = registerBlock("power_tube", () -> new PowerTubeBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);


    public static final RegistryObject<Block> TUBE_BUNDLE = registerBlock("tube_bundle", () -> new TubeBundleBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);
    public static final RegistryObject<Block> BELT = registerBlock("belt", () -> new BeltBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);


    public static final RegistryObject<Block> RENDER_TESTER = registerBlock("render_tester", () -> new BlockRenderTester(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);

    public static final RegistryObject<Block> BOUNDING_TESTER = registerBlock("bounding_tester", () -> new BoundingBoxTesting(AbstractBlock.Properties.of(Material.METAL).harvestLevel(5).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f).noOcclusion()), ModItemGroup.INDUSTRIZER_MACHINES);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, ItemGroup tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, ItemGroup tab){
        IndustrizerItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        TILES.register(eventBus);
    }

}
