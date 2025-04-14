package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.registration.M4Blocks;
import com.iznaroth.m4.common.registration.M4Items;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class M4Recipes extends RecipeProvider {
    public M4Recipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        System.out.println("DATAGEN STAGE: Recipes");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, M4Blocks.OBLITERATION_PLINTH.get())
                .pattern("dsd")
                .pattern("dxd")
                .pattern("ddd")
                .define('d', ItemTags.DIRT)
                .define('x', Tags.Items.GEMS_DIAMOND)
                .define('s', Items.STICK)
                .group("tutorial")
                .unlockedBy("has_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.GEMS_DIAMOND).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, M4Blocks.POWER_CABLE_BLOCK.get())
                .pattern("iii")
                .pattern("rrr")
                .pattern("iii")
                .define('i', Tags.Items.INGOTS_COPPER)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .group("tutorial")
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .save(consumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, M4Blocks.MANUFACTORUM.get())
                .pattern("hdh")
                .pattern("mte")
                .pattern("bcp")
                .define('h', M4Items.HH_HOUSING)
                .define('t', M4Items.THREE_CORE)
                .define('c', M4Items.COPHROLITE_INTAKE_PART)
                .define('d', M4Items.DYSPERSIRON_ARTICULATING_PART)
                .define('p', M4Items.SIMPLE_P_D_CIRCUIT)
                .define('e', M4Items.COPHROLITE_CABLING)
                .define('b', M4Items.BINDING_STONE_LIS)
                .define('m', M4Items.SHODDY_ENTRAPMENT_MATRIX)
                .group("tutorial")
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .save(consumer);

        System.out.println("The problem is not Manufactorum");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, M4Blocks.MANIPULATOR.get())
                .pattern("hrh")
                .pattern("gte")
                .pattern("ycp")
                .define('h', M4Items.HH_HOUSING)
                .define('t', M4Items.THREE_CORE)
                .define('c', M4Items.COPHROLITE_INTAKE_PART)
                .define('g', M4Items.INTERNAL_GANTRY)
                .define('p', M4Items.SIMPLE_P_D_CIRCUIT)
                .define('e', M4Items.COPHROLITE_CABLING)
                .define('y', M4Items.BINDING_STONE_YOL)
                .define('r', M4Items.DYSPERSIRON_PROCESSING_PART)
                .group("tutorial")
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .save(consumer);

        System.out.println("The problem is not Manipulator!");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, M4Blocks.INOCULATOR.get())
                .pattern("huh")
                .pattern("ite")
                .pattern("kcp")
                .define('h', M4Items.HH_HOUSING)
                .define('t', M4Items.THREE_CORE)
                .define('c', M4Items.COPHROLITE_INTAKE_PART)
                .define('u', M4Items.DYSPERSIRON_PUMP_PART)
                .define('p', M4Items.SIMPLE_P_D_CIRCUIT)
                .define('e', M4Items.COPHROLITE_CABLING)
                .define('k', M4Items.BINDING_STONE_EKH)
                .define('i', M4Items.INSCRIBED_TANK)
                .group("tutorial")
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .save(consumer);
/*
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get())
                .pattern("dsd")
                .pattern("ixi")
                .pattern("idi")
                .define('d', ItemTags.DIRT)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('x', Tags.Items.GEMS_DIAMOND)
                .define('s', Items.STICK)
                .group("tutorial")
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .save(consumer);



        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, M4Blocks.CHARGING_STATION.get())
                .pattern("ixi")
                .pattern("iii")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('x', Tags.Items.GEMS_DIAMOND)
                .group("tutorial")
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .save(consumer);


*/
    }
}
