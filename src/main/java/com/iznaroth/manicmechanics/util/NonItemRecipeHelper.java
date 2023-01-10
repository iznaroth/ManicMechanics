package com.iznaroth.manicmechanics.util;

import com.iznaroth.manicmechanics.blockentity.AssemblerBlockEntity;
import com.iznaroth.manicmechanics.blockentity.CondenserBlockEntity;
import com.iznaroth.manicmechanics.blockentity.InfuserBlockEntity;
import com.iznaroth.manicmechanics.recipe.AssemblerRecipe;
import com.iznaroth.manicmechanics.recipe.CondenserRecipe;
import com.iznaroth.manicmechanics.recipe.InfuserRecipe;
import com.iznaroth.manicmechanics.recipe.MMRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class NonItemRecipeHelper {

    public static NonItemRecipeHelper INSTANCE;

    public static Optional<InfuserRecipe> getInfuserRecipeFor(SimpleContainer pContainer, Level pLevel, InfuserBlockEntity pEntity) {
        return pLevel.getRecipeManager()
                .getAllRecipesFor(InfuserRecipe.Type.INSTANCE) // Gets all recipes
                .stream() // Looks through all recipes for types
                .filter(recipe -> recipe.matches(pContainer, pLevel, pEntity)) // Checks if the recipe inputs are valid
                .findFirst(); // Finds the first recipe whose inputs match. Should never, ever be multiple.
    }

    public static Optional<CondenserRecipe> getCondenserRecipeFor(SimpleContainer pContainer, Level pLevel, CondenserBlockEntity pEntity) {
        return pLevel.getRecipeManager()
                .getAllRecipesFor(CondenserRecipe.Type.INSTANCE) // Gets all recipes
                .stream() // Looks through all recipes for types
                .filter(recipe -> recipe.matches(pContainer, pLevel, pEntity)) // Checks if the recipe inputs are valid
                .findFirst(); // Finds the first recipe whose inputs match. Should never, ever be multiple.
    }

    public static Optional<AssemblerRecipe> getAssemblerRecipeFor(SimpleContainer pContainer, Level pLevel, AssemblerBlockEntity pEntity) {
        return pLevel.getRecipeManager()
                .getAllRecipesFor(AssemblerRecipe.Type.INSTANCE) // Gets all recipes
                .stream() // Looks through all recipes for types
                .filter(recipe -> recipe.matches(pContainer, pLevel, pEntity)) // Checks if the recipe inputs are valid
                .findFirst(); // Finds the first recipe whose inputs match. Should never, ever be multiple.
    }
}

