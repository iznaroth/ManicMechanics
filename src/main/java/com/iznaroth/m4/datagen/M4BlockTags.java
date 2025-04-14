package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;
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
        super(output, lookupProvider, M4.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        System.out.println("DATAGEN STAGE: Tags");
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(M4Blocks.OBLITERATION_PLINTH.get())
                .add(M4Blocks.MANUFACTORUM.get())
                .add(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get())
                .add(M4Blocks.CHARGING_STATION.get())
                .add(M4Blocks.POWER_CABLE_BLOCK.get())
                .add(M4Blocks.DYSPERSIUM_ORE.get())
                .add(M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(M4Blocks.OBLITERATION_PLINTH.get())
                .add(M4Blocks.MANUFACTORUM.get())
                .add(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get())
                .add(M4Blocks.CHARGING_STATION.get())
                .add(M4Blocks.POWER_CABLE_BLOCK.get())
                .add(M4Blocks.DYSPERSIUM_ORE.get())
                .add(M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.get());
    }
}
