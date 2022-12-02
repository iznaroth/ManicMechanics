package com.iznaroth.industrizer.recipe;

import com.iznaroth.industrizer.IndustrizerMod;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface ISealingChamberRecipe extends IRecipe<IInventory> {
    ResourceLocation TYPE_ID = new ResourceLocation(IndustrizerMod.MOD_ID, "sealer");

    @Override
    default IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height){
        return true;
    }

    @Override
    default boolean isSpecial(){
        return true;
    }
}
