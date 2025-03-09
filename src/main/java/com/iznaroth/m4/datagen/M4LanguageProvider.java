package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;

import com.iznaroth.m4.common.block.ManufactorumBlock;
import com.iznaroth.m4.common.blockentity.ManufactorumBlockEntity;
import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class M4LanguageProvider extends LanguageProvider {
    public M4LanguageProvider(PackOutput output, String locale) {
        super(output, M4.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(M4Blocks.OBLITERATION_PLINTH.get(), "Obliteration Plinth");
        add(M4Blocks.MANUFACTORUM.get(), "Manufactorum");
        add(ManufactorumBlock.SCREEN_MANUFACTORUM, "Manufactorum");
        add(ManufactorumBlockEntity.ACTION_MELT, "Melt input: %s");
        add(ManufactorumBlockEntity.ACTION_BREAK, "Block loot: %s");
        add(ManufactorumBlockEntity.ACTION_SOUND, "Play break sound: %s");
        add(ManufactorumBlockEntity.ACTION_SPAWN, "Spawn egg: %s");
        add(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get(), "Hyperfield Extract Polarization Chamber");
        add(M4Blocks.CHARGING_STATION.get(), "Charging Station");
        add("tab.m4machines", "M4 Machines");
    }
}
