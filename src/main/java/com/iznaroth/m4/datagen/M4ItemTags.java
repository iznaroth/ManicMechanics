package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.m4;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class M4ItemTags extends ItemTagsProvider {

    public M4ItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, blockTags.contentsGetter(), m4.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }
}
