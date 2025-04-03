package com.iznaroth.m4.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.iznaroth.m4.common.M4;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class CableTubeModelLoader implements IGeometryLoader<CableTubeModelLoader.CableModelGeometry> {

    public static final ResourceLocation GENERATOR_LOADER = ResourceLocation.fromNamespaceAndPath(M4.MODID, "cableloader");

    public static void register(ModelEvent.RegisterGeometryLoaders event) {
        event.register(GENERATOR_LOADER, new CableTubeModelLoader());
    }


    @Override
    public CableModelGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        //boolean facade = jsonObject.has("facade") && jsonObject.get("facade").getAsBoolean();
        return new CableModelGeometry();
    }

    public static class CableModelGeometry implements IUnbakedGeometry<CableModelGeometry> {

        //private final boolean facade;

        public CableModelGeometry() {
            //this.facade = facade;
        }

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
            return new CableTubeBakedModel(context);
        }
    }
}