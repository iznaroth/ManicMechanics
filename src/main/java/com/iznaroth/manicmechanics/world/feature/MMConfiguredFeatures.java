package com.iznaroth.manicmechanics.world.feature;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class MMConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ManicMechanics.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_DYSPERSIUM_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MMBlocks.DYSPERSIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DYSPERSIUM_DEEPSLATE_ORE.get().defaultBlockState())));


    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_NITROL_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MMBlocks.NITROL_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MMBlocks.NITROL_DEEPSLATE_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_PHOSPHORITE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MMBlocks.PHOSPHORITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MMBlocks.PHOSPHORITE_DEEPSLATE_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_THALLITE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MMBlocks.THALLITE_ORE.get().defaultBlockState())));


    public static final RegistryObject<ConfiguredFeature<?, ?>> DYSPERSIUM_ORE = CONFIGURED_FEATURES.register("dyspersium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_DYSPERSIUM_ORES.get(),9)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> NITROL_ORE = CONFIGURED_FEATURES.register("nitrol_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_NITROL_ORES.get(),16)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PHOSPHORITE_ORE = CONFIGURED_FEATURES.register("phosphorite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_PHOSPHORITE_ORES.get(),14)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> THALLITE_ORE = CONFIGURED_FEATURES.register("thallite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_PHOSPHORITE_ORES.get(),21)));

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}