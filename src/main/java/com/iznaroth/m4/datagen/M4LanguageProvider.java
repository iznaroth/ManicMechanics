package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.m4;

import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class M4LanguageProvider extends LanguageProvider {
    public M4LanguageProvider(PackOutput output, String locale) {
        super(output, m4.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(M4Blocks.OBLITERATION_PLINTH.get(), "Obliteration Plinth");
    }
}
