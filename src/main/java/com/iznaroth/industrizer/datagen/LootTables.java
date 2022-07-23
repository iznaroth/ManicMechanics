package com.iznaroth.industrizer.datagen;

//import com.mcjty.mytutorial.setup.Registration;
import com.iznaroth.industrizer.block.IndustrizerBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.LootParameterSets;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        //lootTables.put(ModBlocks.DYSPERSIUM_ORE.get(), createStandardTable("dyspersium_ore", ModBlocks.DYSPERSIUM_ORE.get()));
        lootTables.put(IndustrizerBlocks.HEP.get(), createStandardTable("hep", IndustrizerBlocks.HEP.get()).setParamSet(LootParameterSets.BLOCK));
        lootTables.put(IndustrizerBlocks.CURRENCY_BUREAU.get(), createStandardTable("currency_bureau", IndustrizerBlocks.CURRENCY_BUREAU.get()).setParamSet(LootParameterSets.BLOCK));
        lootTables.put(IndustrizerBlocks.COMMUNICATOR.get(), createStandardTable("communicator", IndustrizerBlocks.COMMUNICATOR.get()).setParamSet(LootParameterSets.BLOCK));
    }
}
