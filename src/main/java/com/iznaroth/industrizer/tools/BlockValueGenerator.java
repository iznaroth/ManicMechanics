package com.iznaroth.industrizer.tools;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

public class BlockValueGenerator {

    HashMap<String, Integer> current_mapping;

    Class blocks = net.minecraft.block.Blocks.class;

    Random rand = new Random();


    public HashMap<String, Integer> populateEconomyMapping(String biome_key, double seed){
        Field[] fields = blocks.getDeclaredFields();
        current_mapping.clear();

        //Use SEED and Biome to generate consistent biome-world-bound mappings.

        for(int i = 0; i < fields.length; i++){
            current_mapping.put(fields[i].getName(), rand.nextInt(5000)); //assign random price between 1 and 5000 to all named blocks
        }

        return current_mapping;

    }

    public HashMap<String, Integer> getPrevMap(){
        return current_mapping;
    }

}
