package com.iznaroth.manicmechanics.recipe;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMRecipeTypes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ManicMechanics.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SealingChamberRecipe>> SEALING_CHAMBER_SERIALIZER
            = SERIALIZERS.register("sealer", () -> SealingChamberRecipe.Serializer.INSTANCE);


    public static final RegistryObject<RecipeSerializer<InfuserRecipe>> INFUSER_SERIALIZER
            = SERIALIZERS.register("infuser", () -> InfuserRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
