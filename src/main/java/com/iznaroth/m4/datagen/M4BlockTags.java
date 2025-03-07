package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.m4;
import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class M4BlockTags extends BlockTagsProvider {
    public M4BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, m4.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(M4Blocks.OBLITERATION_PLINTH.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(M4Blocks.OBLITERATION_PLINTH.get());
    }
}
