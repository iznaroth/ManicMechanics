package com.iznaroth.manicmechanics.world.feature;


import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class MMPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ManicMechanics.MOD_ID);


    public static final RegistryObject<PlacedFeature> DYSPERSIUM_ORE_PLACED = PLACED_FEATURES.register("dyspersium_ore_placed",
            () -> new PlacedFeature(MMConfiguredFeatures.DYSPERSIUM_ORE.getHolder().get(),
                    commonOrePlacement(7, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(2)))));

    public static final RegistryObject<PlacedFeature> NITROL_ORE_PLACED = PLACED_FEATURES.register("nitrol_ore_placed",
            () -> new PlacedFeature(MMConfiguredFeatures.NITROL_ORE.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(12)))));

    public static final RegistryObject<PlacedFeature> PHOSPHORITE_ORE_PLACED = PLACED_FEATURES.register("phosphorite_ore_placed",
            () -> new PlacedFeature(MMConfiguredFeatures.PHOSPHORITE_ORE.getHolder().get(),
                    commonOrePlacement(9, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(45)))));

    public static final RegistryObject<PlacedFeature> THALLITE_ORE_PLACED = PLACED_FEATURES.register("thallite_ore_placed",
            () -> new PlacedFeature(MMConfiguredFeatures.THALLITE_ORE.getHolder().get(),
                    commonOrePlacement(13, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-12), VerticalAnchor.absolute(65)))));


    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}

