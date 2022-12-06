package com.iznaroth.manicmechanics.datagen;

//import com.mcjty.mytutorial.setup.Registration;
import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.LootParameterSets;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        //lootTables.put(ModBlocks.DYSPERSIUM_ORE.get(), createStandardTable("dyspersium_ore", ModBlocks.DYSPERSIUM_ORE.get()));
        lootTables.put(MMBlocks.HEP.get(), createStandardTable("hep", MMBlocks.HEP.get()).setParamSet(LootParameterSets.BLOCK));
        lootTables.put(MMBlocks.CURRENCY_BUREAU.get(), createStandardTable("currency_bureau", MMBlocks.CURRENCY_BUREAU.get()).setParamSet(LootParameterSets.BLOCK));
        lootTables.put(MMBlocks.COMMUNICATOR.get(), createStandardTable("communicator", MMBlocks.COMMUNICATOR.get()).setParamSet(LootParameterSets.BLOCK));
    }
}
