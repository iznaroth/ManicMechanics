package com.iznaroth.industrizer.datagen;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, IndustrizerMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Example generation for a simple textured item:
        //        singleTexture(
        //                Registration.TESTITEM.get().getRegistryName().getPath(),
        //                new ResourceLocation("item/handheld"),
        //                "layer0",
        //                new ResourceLocation(Tutorial.MODID, "item/firstitem"));

        /*getBuilder(ModItems.TESTITEM.get().getRegistryName().getPath())
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/firstitem0")
                .override().predicate(DISTANCE_PROPERTY, 0).model(createTestModel(0)).end()
                .override().predicate(DISTANCE_PROPERTY, 1).model(createTestModel(1)).end()
                .override().predicate(DISTANCE_PROPERTY, 2).model(createTestModel(2)).end()
                .override().predicate(DISTANCE_PROPERTY, 3).model(createTestModel(3)).end();


         */

        withExistingParent(ModBlocks.HEP.get().getRegistryName().getPath(), new ResourceLocation(IndustrizerMod.MOD_ID, "block/hep"));
        withExistingParent(ModBlocks.CURRENCY_BUREAU.get().getRegistryName().getPath(), new ResourceLocation(IndustrizerMod.MOD_ID, "block/currency_bureau"));
    }

    private ItemModelBuilder createTestModel(int suffix) {
        return getBuilder("testitem" + suffix).parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/firstitem" + suffix);
    }

}
