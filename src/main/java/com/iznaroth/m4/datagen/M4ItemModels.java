package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;

import com.iznaroth.m4.common.registration.M4Blocks;
import com.iznaroth.m4.common.registration.M4Items;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;
import java.util.stream.Collectors;

import static com.iznaroth.m4.common.registration.M4CreativeTabs.addToDisplay;

public class M4ItemModels extends ItemModelProvider {
    public M4ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, M4.MODID, existingFileHelper);
    }

    /*
        We use filters to downfilter the list to a few key discriminating tags.
        We use the item instance in each generator, checking an instanceOf
     */

    @Override
    protected void registerModels() {
        System.out.println("DATAGEN STAGE: Models");
        HashMap<String, Holder<Item>> unregisteredBlockItems = new HashMap<>();
        for(Holder<Item> item : M4Items.BLOCK_ITEMS.getEntries()){
            System.out.println(item.getRegisteredName());
            unregisteredBlockItems.put(item.getRegisteredName(), item);
        }
        //! NOTE - variant models usually use different names - if you need to display a specific blockstate ofr the item model, you HAVE to include it here.

        registerAndRemoveSpecific(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.getId().getPath(), "hyperfield_extract_polarization_chamber_on", unregisteredBlockItems);
        registerAndRemoveSpecific(M4Blocks.CHARGING_STATION.getId().getPath(), "charging_station_off", unregisteredBlockItems);
        registerAndRemoveSpecific(M4Blocks.POWER_CABLE_BLOCK.getId().getPath(), "cable", unregisteredBlockItems);

        registerNonBlockItems();
        registerBlockItems(unregisteredBlockItems.values());
    }

    private void registerBlockItems(Collection<Holder<Item>> itemsToRegister) {
        System.out.println("REG: " + itemsToRegister);
        for (Holder<Item> itemProvider : itemsToRegister) {
            //Test for the existence of a texture with a matching name.

            try {
                simpleBlockItem(Block.byItem(itemProvider.value()));
            } catch (Exception e) {
                System.out.println("Defaulting to generic texture for: " + itemProvider.value().toString());
                withExistingParent(itemProvider.getKey().location().toString(), modLoc("block/" + itemProvider.getRegisteredName().substring(3)));
                //basicItem(itemProvider.value());
            }
        }
    }

    private void registerNonBlockItems() {
        for (Holder<Item> itemProvider : M4Items.ITEMS.getEntries()) {
            //Test for the existence of a texture with a matching name.

            //If we don't want to do filesystems we could catch the error and handle it as a generic.

            try {
                basicItem(itemProvider.value());
            } catch (Exception e) {
                System.out.println("Defaulting to generic texture for: " + itemProvider.value());
                //itemProvider.value().components().has() can check if the item type has a datacomponenttype attached
                //instanceof is also valid for items that extend a given base
                withExistingParent(itemProvider.getKey().location().toString(), mcLoc("item/generated")).texture("layer0", "item/generic_intermediary");
                //basicItem(itemProvider.value());
            }
        }
    }

    /* Register a block with a standard pass-in and remove it from the placeholder evals.
     */
    private void registerAndRemoveSpecific(String path, String name, HashMap<String, Holder<Item>> registryEntries){
        System.out.println("REG: " + registryEntries);
        System.out.println("path: " + path);
        withExistingParent(path, modLoc("block/" + name));
        registryEntries.remove("m4:" + path);
    }

    //we're making a copied map of each registry and using a wrapper to withExistingParent and remove anything we actually create textures for.
    //In addition, we're only manually texit
}
