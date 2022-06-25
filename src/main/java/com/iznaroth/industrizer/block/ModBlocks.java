package com.iznaroth.industrizer.block;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.item.ModItemGroup;
import com.iznaroth.industrizer.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrizerMod.MOD_ID);

    //WORLDGEN
    public static final RegistryObject<Block> DYSPERSIUM_ORE = registerBlock("dyspersium_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(3.2f)));
    public static final RegistryObject<Block> COPPER_ORE = registerBlock("dyspersium_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(3.2f)));


    //NO POWER
    public static final RegistryObject<Block> INDUSTRIAL_WORKBENCH = registerBlock("industrial_workbench", () -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));


    //MACHINES (CORE - TIER I)
    public static final RegistryObject<Block> HEP = registerBlock("hep", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> INDUSTRIAL_FURNACE = registerBlock("hep", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> CONDENSER = registerBlock("condenser", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> ASSEMBLER = registerBlock("assembler", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> ETCHER = registerBlock("etcher", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));

    public static final RegistryObject<Block> DEMYSTIFIER = registerBlock("demystifier", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> ALCHEMIZER = registerBlock("alchemizer", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> REEXTENDER = registerBlock("reextender", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));

    public static final RegistryObject<Block> TRANSMISSION_STATION = registerBlock("transmission_station", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> CURRENCY_BUREAU = registerBlock("currency_bureau", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));
    public static final RegistryObject<Block> EXPORT_CENTER = registerBlock("export_center", () -> new GeneratorBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(2.5f)));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ModItemGroup.INDUSTRIZER_RESOURCES)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
