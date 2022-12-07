package com.iznaroth.manicmechanics.datagen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ManicMechanics.MOD_ID, existingFileHelper);
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





        withExistingParent(MMBlocks.HEP.get().getRegistryName().getPath(), new ResourceLocation(ManicMechanics.MOD_ID, "block/hep"));
        withExistingParent(MMBlocks.CURRENCY_BUREAU.get().getRegistryName().getPath(), new ResourceLocation(ManicMechanics.MOD_ID, "block/currency_bureau"));
        withExistingParent(MMBlocks.COMMUNICATOR.get().getRegistryName().getPath(), new ResourceLocation(ManicMechanics.MOD_ID, "block/communicator"));
    */

    }


    private ItemModelBuilder createTestModel(int suffix) {
        return getBuilder("testitem" + suffix).parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/firstitem" + suffix);
    }

}
