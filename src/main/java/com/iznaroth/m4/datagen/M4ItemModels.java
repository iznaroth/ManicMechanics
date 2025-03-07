package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.m4;

import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class M4ItemModels extends ItemModelProvider {
    public M4ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, m4.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(M4Blocks.OBLITERATION_PLINTH.getId().getPath(), modLoc("block/obliteration_plinth"));
    }
}
