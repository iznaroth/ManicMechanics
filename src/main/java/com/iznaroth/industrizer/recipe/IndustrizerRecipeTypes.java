package com.iznaroth.industrizer.recipe;

import com.iznaroth.industrizer.IndustrizerMod;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IndustrizerRecipeTypes extends RecipeManager {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, IndustrizerMod.MOD_ID);

    public static final RegistryObject<SealingChamberRecipe.Serializer> SEALING_CHAMBER_SERIALIZER
            = RECIPE_SERIALIZER.register("sealer", SealingChamberRecipe.Serializer::new);

    public static final IRecipeType<SealingChamberRecipe> SEALING_CHAMBER_RECIPE
            = new SealingChamberRecipe.SealingChamberRecipeType();

    public static void register(IEventBus eventBus){
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, SealingChamberRecipe.TYPE_ID, SEALING_CHAMBER_RECIPE);
    }
}
