package com.iznaroth.industrizer.datagen;

//import com.mcjty.mytutorial.setup.Registration;
import com.iznaroth.industrizer.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.LootParameterSets;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        //lootTables.put(ModBlocks.DYSPERSIUM_ORE.get(), createStandardTable("dyspersium_ore", ModBlocks.DYSPERSIUM_ORE.get()));
        lootTables.put(ModBlocks.HEP.get(), createStandardTable("hep", ModBlocks.HEP.get()).setParamSet(LootParameterSets.BLOCK));
        lootTables.put(ModBlocks.CURRENCY_BUREAU.get(), createStandardTable("currency_bureau", ModBlocks.CURRENCY_BUREAU.get()).setParamSet(LootParameterSets.BLOCK));
    }
}
