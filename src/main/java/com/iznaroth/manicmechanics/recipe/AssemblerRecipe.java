package com.iznaroth.manicmechanics.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.blockentity.AssemblerBlockEntity;
import com.iznaroth.manicmechanics.item.MMItems;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WorldData;

import javax.annotation.Nullable;

public class AssemblerRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;

    private final ItemStack tooling;
    private final NonNullList<Ingredient> recipeItems;
    private int[] quantities;

    public AssemblerRecipe(ResourceLocation id, ItemStack output, ItemStack tooling, NonNullList<Ingredient> recipeItems, int[] quantities) {
        this.id = id;
        this.output = output;
        this.tooling = tooling;
        this.recipeItems = recipeItems;
        this.quantities = quantities;
    }


    public boolean matches(SimpleContainer pContainer, Level pLevel, AssemblerBlockEntity entity) {

        System.out.println("Call for match");

        if(pLevel.isClientSide()) {
            return false;
        }

        if(!tooling.getItem().equals(pContainer.getItem(0).getItem())){
            System.out.println(tooling.getItem());
            System.out.println(pContainer.getItem(0));
            return false;
        }

        for(int i = 0; i < recipeItems.size(); i++) {
            if (!recipeItems.get(i).test(pContainer.getItem(i + 1))) {
                System.out.println(recipeItems.get(i));
                System.out.println(pContainer.getItem(i));
                return false;
            }
        }

        for(int i = 0; i < quantities.length; i++) {
            if (quantities[i] != pContainer.getItem(i + 1).getCount() && !pContainer.getItem(i + 1).isEmpty()) {
                System.out.println(quantities[i]);
                System.out.println(pContainer.getItem(i + 1).getCount() + " " + pContainer.getItem(i + 1));
                return false;
            }
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

    public ItemStack getToolingItem(){ return tooling.copy(); }

    public int[] getQuantities(){ return quantities; }


    public ItemStack getIcon(){
        return new ItemStack(MMBlocks.ASSEMBLER.get());
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

    public static class Type implements RecipeType<AssemblerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "assembler";
    }

    public static class Serializer implements RecipeSerializer<AssemblerRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ManicMechanics.MOD_ID, "assembler");

        @Override
        public AssemblerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            ItemStack tooling;

            if (json.has("tooling")) {
                tooling = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "tooling"));
            } else {
                tooling = new ItemStack(MMItems.MECHANICAL_ARM.get(), 1); //All vanilla recipes are packed using the mechanical arm.
            }

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            JsonArray counts;

            if(json.has("counts")) {
                counts = GsonHelper.getAsJsonArray(json, "counts");
            }else {
                counts = new JsonArray(); //For autocrafting of vanilla recipes, counts do not exist.
            }

            NonNullList<Ingredient> inputs = NonNullList.withSize(9, Ingredient.EMPTY);
            int[] quantities = {1, 1, 1, 1, 1, 1, 1, 1, 1};

            for(int i = 0; i < inputs.size(); i++){
                if(i < ingredients.size()) {
                    inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
                } else {
                    inputs.set(i, Ingredient.EMPTY);
                }
            }

            for(int i = 0; i < quantities.length; i++){
                if(counts.size() > 0 && i < counts.size()) { //omitted counts OR vanilla recipes default to 1 on empty.
                    quantities[i] = getQuantitiesFromJson(counts.get(i));
                } else {
                    quantities[i] = 1;
                }
            }

            return new AssemblerRecipe(recipeId, output, tooling, inputs, quantities);
        }

        public int getQuantitiesFromJson(@Nullable JsonElement quantity){
            if (quantity != null && !quantity.isJsonNull()) {

                //System.out.println(mode.toString());
                //System.out.println(mode.toString().substring(8, mode.toString().length()-1));
                if (quantity.isJsonObject()) {

                    int result = Integer.parseInt(quantity.toString().substring(12, quantity.toString().length()-1));
                    return result;

                }
            }

            return -1;
        }

        @Nullable
        @Override
        public AssemblerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            int[] quantities = new int[9];

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            for(int i = 0; i < quantities.length; i++){
                quantities[i] = buffer.readInt();
            }

            ItemStack output = buffer.readItem();
            ItemStack tooling = buffer.readItem();

            return new AssemblerRecipe(recipeId, output, tooling, inputs, quantities);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AssemblerRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for(Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(buffer);
            }

            for(int i : recipe.getQuantities()){
                buffer.writeInt(i);
            }

            buffer.writeItemStack(recipe.getResultItem(), false);
            buffer.writeItemStack(recipe.getToolingItem(), false);
        }
    }
}
