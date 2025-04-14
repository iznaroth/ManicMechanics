package com.iznaroth.m4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DataGeneration {

    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new M4BlockStates(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new M4ItemModels(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new M4LanguageProvider(packOutput, "en_us"));

        M4BlockTags blockTags = new M4BlockTags(packOutput, lookupProvider, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new M4ItemTags(packOutput, lookupProvider, blockTags, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new M4Recipes(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(M4LootTables::new, LootContextParamSets.BLOCK)), lookupProvider));
        System.out.println("EXIT: Datagen");
        generator.addProvider(event.includeServer(), new M4Datapacks(packOutput, lookupProvider));
    }
}