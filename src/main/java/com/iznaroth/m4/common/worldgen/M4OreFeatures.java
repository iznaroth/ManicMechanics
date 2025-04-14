package com.iznaroth.m4.common.worldgen;

import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class M4OreFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DYSPERSIUM = registerKey("ore_dyspersium");

    public M4OreFeatures() {

    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);


        List<OreConfiguration.TargetBlockState> overworldDyspersiumOres = List.of(
                OreConfiguration.target(stoneReplaceables, M4Blocks.DYSPERSIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.get().defaultBlockState()));

        register(context, ORE_DYSPERSIUM, Feature.ORE, new OreConfiguration(overworldDyspersiumOres, 9));
    }

    //returns a new ResourceKey corresponding to the resource location at the passed name
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(M4.MODID, name));
    }


    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration
    )
    {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
