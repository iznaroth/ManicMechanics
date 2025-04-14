package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;

import com.iznaroth.m4.common.block.ManufactorumBlock;
import com.iznaroth.m4.common.blockentity.ManufactorumBlockEntity;
import com.iznaroth.m4.common.registration.M4Blocks;
import com.iznaroth.m4.common.registration.M4Items;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class M4LanguageProvider extends LanguageProvider {
    public M4LanguageProvider(PackOutput output, String locale) {
        super(output, M4.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        System.out.println("DATAGEN STAGE: Lang");

        HashMap<String, Holder<Item>> untranslatedItems = new HashMap<>();
        for(Holder<Item> item : M4Items.ITEMS.getEntries()){
            System.out.println(item.getRegisteredName());
            untranslatedItems.put(item.getRegisteredName(), item);
        }

        HashMap<String, Holder<Block>> untranslatedBlocks = new HashMap<>();
        for(Holder<Block> block : M4Blocks.BLOCKS.getEntries()){
            System.out.println(block.getRegisteredName());
            untranslatedBlocks.put(block.getRegisteredName(), block);
        }

        add(ManufactorumBlock.SCREEN_MANUFACTORUM, "Manufactorum");
        add(ManufactorumBlockEntity.ACTION_MELT, "Melt input: %s");
        add(ManufactorumBlockEntity.ACTION_BREAK, "Block loot: %s");
        add(ManufactorumBlockEntity.ACTION_SOUND, "Play break sound: %s");
        add(ManufactorumBlockEntity.ACTION_SPAWN, "Spawn egg: %s");
        addSpecificBlockAndRemove(M4Blocks.POWER_CABLE_BLOCK.get(), "Cophrolite Cable", untranslatedBlocks, "m4:cable");
        add("itemGroup.m4", "Mad-Manic Magi-Mechanics");

        addSpecificItemAndRemove(M4Items.HH_HOUSING.asItem(), "H&H Improvisational Housing", untranslatedItems, "m4:hh_housing");
        addSpecificItemAndRemove(M4Items.THREE_CORE.asItem(), "3-core", untranslatedItems, "m4:three_core");
        addSpecificItemAndRemove(M4Items.THREE_CUBE.asItem(), "3-cube", untranslatedItems, "m4:three_cube");
        addSpecificItemAndRemove(M4Items.SIMPLE_P_D_CIRCUIT.asItem(), "Simple P.D. Circuit", untranslatedItems, "m4:simple_p_d_circuit");
        addSpecificItemAndRemove(M4Items.TPLAS_INSULATION.asItem(), "TPLAS Insulation", untranslatedItems, "m4:tplas_insulation");
        addSpecificItemAndRemove(M4Items.TPLAS_BASEBOARD.asItem(), "TPLAS Baseboard", untranslatedItems, "m4:tplas_baseboard");
        addSpecificItemAndRemove(M4Items.TR_INSULATION.asItem(), "TR Insulation", untranslatedItems, "m4:tr_insulation");
        addSpecificItemAndRemove(M4Items.MANUFACTURERS_TOOLING_BIT.asItem(), "Manufacturer's Tooling Bit", untranslatedItems, "m4:manufacturers_tooling_bit");



        autogenerateItemEnglishFromKey(untranslatedItems.values());
        autogenerateBlockEnglishFromKey(untranslatedBlocks.values());
    }

    private void addSpecificItemAndRemove(Item toAdd, String name, HashMap<String, Holder<Item>> untranslatedItems, String key){
        add(toAdd, name);
        untranslatedItems.remove(key);
    }

    private void addSpecificBlockAndRemove(Block toAdd, String name, HashMap<String, Holder<Block>> untranslatedBlocks, String key){
        add(toAdd, name);
        System.out.println("Key to remove: " + key);
        System.out.println(untranslatedBlocks);
        untranslatedBlocks.remove(key);
    }

    private void autogenerateItemEnglishFromKey(Collection<Holder<Item>> untranslatedItems){
        for(Holder<Item> item : untranslatedItems){
            add(item.value(), keyToCapitalizedEnglish(item.getRegisteredName()));
        }
    }

    private void autogenerateBlockEnglishFromKey(Collection<Holder<Block>> untranslatedBlocks){
        for(Holder<Block> block : untranslatedBlocks){
            add(block.value(), keyToCapitalizedEnglish(block.getRegisteredName()));
        }
    }

    private String keyToCapitalizedEnglish(String languageKey){
        char[] processing = languageKey.replaceAll("_", " ").substring(3).toCharArray();

        processing[0] = Character.toUpperCase(processing[0]);
        for(int i = 1; i < processing.length; i++){
            if(processing[i] == ' ' && processing[i+1] != ' '){
                processing[i+1] = Character.toUpperCase(processing[i+1]);
            }
        }

        return String.valueOf(processing);
    }
}
