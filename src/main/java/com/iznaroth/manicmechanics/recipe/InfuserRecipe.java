package com.iznaroth.manicmechanics.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InfuserRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<Fluid> recipeFluids;
    private final int[] modes;


    public InfuserRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, NonNullList<Fluid> recipeFluids, int[] modes) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.recipeFluids = recipeFluids;
        this.modes = modes;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {

        if(pLevel.isClientSide()) {
            return false;
        }

        if(!recipeItems.get(0).test(pContainer.getItem(0))){
            return false;
        }

        if(!recipeItems.get(1).test(pContainer.getItem(1))){
            return false;
        }

        return true;
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

    public NonNullList<Fluid> getRecipeFluids(){
        return this.recipeFluids;
    }

    public int[] getModes(){
        return this.modes;
    }

    public ItemStack getIcon(){
        return new ItemStack(MMBlocks.SEALER.get());
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

    public static class Type implements RecipeType<InfuserRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "infuser";
    }

    public static class Serializer implements RecipeSerializer<InfuserRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ManicMechanics.MOD_ID, "infuser");

        @Override
        public InfuserRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            JsonArray fluids = GsonHelper.getAsJsonArray(json, "fluids");
            JsonArray modes = GsonHelper.getAsJsonArray(json, "modes");

            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            NonNullList<Fluid> fluidInputs = NonNullList.withSize(3, Fluids.EMPTY);
            int[] modeRequirements = new int[3];

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            
            for(int i = 0; i < fluidInputs.size(); i++){
                fluidInputs.set(i, getFluidFromJson(fluids.get(i)));
            }

            return new InfuserRecipe(recipeId, output, inputs, fluidInputs, modeRequirements);
        }
        
        public Fluid getFluidFromJson(@Nullable JsonElement fluid){
            if (fluid != null && !fluid.isJsonNull()) {

                System.out.println(fluid);
                if (fluid.isJsonObject()) {
                    //This has been re-called on one element.
                    //Registry.FLUID.get(new ResourceLocation(fluid.getAsString()));
                    Fluid result = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluid.getAsString()));
                    if(result != Fluids.EMPTY){
                        return result;
                    }; //NOTE - A legal Vanilla may be present in forge. Test it !
                } else if (fluid.isJsonArray()) {
                    JsonArray jsonarray = fluid.getAsJsonArray();
                    if (jsonarray.size() == 0) {
                        throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
                    } else {
                        System.out.println("How did I get here? We were passed an array.");
                        return Fluids.EMPTY;
                    }
                } else {
                    throw new JsonSyntaxException("Expected item to be object or array of objects");
                }
            } else {
                throw new JsonSyntaxException("Item cannot be null");
            }
            return Fluids.EMPTY;
        }

        @Nullable
        @Override
        public InfuserRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            NonNullList<Fluid> fluidInputs = NonNullList.withSize(3, Fluids.EMPTY);
            int[] modes = new int[3];

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            for(int i = 0; i < fluidInputs.size(); i++){
                fluidInputs.set(i, ForgeRegistries.FLUIDS.getValue(new ResourceLocation(buffer.readUtf())));
            }

            for(int i = 0; i < modes.length; i++){
                modes[i] = buffer.readInt();
            }

            ItemStack output = buffer.readItem();
            return new InfuserRecipe(recipeId, output, inputs, fluidInputs, modes);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, InfuserRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for(Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(buffer);
            }

            for(Fluid f : recipe.getRecipeFluids()){
                buffer.writeUtf(ForgeRegistries.FLUIDS.getKey(f).toString()); //NOTE - f.toString might work lol
            }

            for(int i : recipe.getModes()){
                buffer.writeInt(i);
            }

            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
