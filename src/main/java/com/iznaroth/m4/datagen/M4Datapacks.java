package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.worldgen.M4BiomeModifiers;
import com.iznaroth.m4.common.worldgen.M4Dimensions;
import com.iznaroth.m4.common.worldgen.M4OreFeatures;
import com.iznaroth.m4.common.worldgen.M4OrePlacements;
import com.iznaroth.m4.common.worldgen.biome.M4Biomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class M4Datapacks extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, M4OreFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, M4OrePlacements::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, M4BiomeModifiers::bootstrap)
            .add(Registries.DIMENSION_TYPE, M4Dimensions::bootstrap)
            .add(Registries.BIOME, M4Biomes::boostrap)
            .add(Registries.LEVEL_STEM, M4Dimensions::bootstrapStem);

    public M4Datapacks(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(M4.MODID));
    }
}
