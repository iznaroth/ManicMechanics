package com.iznaroth.manicmechanics.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
            //generator.addProvider(new Recipes(generator));
            //generator.addProvider(new LootTables(generator));
            //generator.addProvider(new Tags(generator, event.getExistingFileHelper()));
        }

        if (event.includeClient()) {
            //generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
            //generator.addProvider(new Items(generator, event.getExistingFileHelper()));
        }
    }
}
