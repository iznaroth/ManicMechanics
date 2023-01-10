package com.iznaroth.manicmechanics.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.blockentity.CondenserBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;

public class CondenserRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int[] modes;


    public CondenserRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int[] modes) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.modes = modes;
    }


    public boolean matches(SimpleContainer pContainer, Level pLevel, CondenserBlockEntity entity) {

        if(pLevel.isClientSide()) {
            return false;
        }

        for(int i = 0; i < recipeItems.size(); i++) {
            if (!recipeItems.get(i).test(pContainer.getItem(i))) {
                //System.out.println(recipeItems.get(i));
                //System.out.println(pContainer.getItem(i));
                return false;
            }
        }

        //System.out.println("Recipe wants these modes: " + Arrays.toString(modes));
        //System.out.println("Entity has these modes: " + entity.getModeFor(0) + " " + entity.getModeFor(1) + " " + entity.getModeFor(2));
        for(int i = 0; i < modes.length; i++){
            if(entity.getModeFor(i) != modes[i])
                return false; //compare mode mismatch
        }

        return true;
    }

    //NOTE - special recipe types bypass this thru NonItemRecipeHelper
    @Override
    public boolean matches(SimpleContainer p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(SimpleContainer inv) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public int[] getModes(){
        return this.modes;
    }

    public ItemStack getIcon(){
        return new ItemStack(MMBlocks.CONDENSER.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CondenserRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "condenser";
    }

    public static class Serializer implements RecipeSerializer<CondenserRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ManicMechanics.MOD_ID, "condenser");

        @Override
        public CondenserRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            JsonArray modes = GsonHelper.getAsJsonArray(json, "modes");

            NonNullList<Ingredient> inputs = NonNullList.withSize(6, Ingredient.EMPTY);
            int[] modeRequirements = new int[9];

            for(int i = 0; i < inputs.size(); i++){
                if(i < ingredients.size()) {
                    inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
                } else {
                    inputs.set(i, Ingredient.EMPTY);
                }
            }

            for(int i = 0; i < modeRequirements.length; i++){
                modeRequirements[i] = getModeFromJson(modes.get(i));
            }

            return new CondenserRecipe(recipeId, output, inputs, modeRequirements);
        }

        public int getModeFromJson(@Nullable JsonElement mode){
            if (mode != null && !mode.isJsonNull()) {

                //System.out.println(mode.toString());
                //System.out.println(mode.toString().substring(8, mode.toString().length()-1));
                if (mode.isJsonObject()) {

                    int result = Integer.parseInt(mode.toString().substring(8, mode.toString().length()-1));
                    return result;

                }
            }

            return -1;
        }

        @Nullable
        @Override
        public CondenserRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            int[] modes = new int[3];

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            for(int i = 0; i < modes.length; i++){
                modes[i] = buffer.readInt();
            }

            ItemStack output = buffer.readItem();
            return new CondenserRecipe(recipeId, output, inputs, modes);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CondenserRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for(Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(buffer);
            }

            for(int i : recipe.getModes()){
                buffer.writeInt(i);
            }

            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
