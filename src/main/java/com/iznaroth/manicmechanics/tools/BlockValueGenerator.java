package com.iznaroth.manicmechanics.tools;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.*;

public class BlockValueGenerator {

    public static HashMap<Item, Integer> current_mapping = new HashMap<Item, Integer>();

    static Class blocks = net.minecraft.block.Blocks.class;


    public static HashMap<Item, Integer> populateEconomyMapping(int biome_key, double seed){
        Field[] fields = blocks.getDeclaredFields();
        if(!current_mapping.isEmpty()) {
            current_mapping.clear();
        }

        //Use SEED and Biome to generate consistent biome-world-bound mappings.
        Random rand = new Random((long)seed * biome_key);

        Collection<Item> items = ForgeRegistries.ITEMS.getValues();
        List<Item> itemlist = new ArrayList(items);

        for(int i = 0; i < items.size(); i++){
            current_mapping.put(itemlist.get(i), rand.nextInt(5000)); //assign random price between 1 and 5000 to all named blocks
        }

        return current_mapping;
    }

    public HashMap<Item, Integer> getPrevMap(){
        return current_mapping;
    }

}
