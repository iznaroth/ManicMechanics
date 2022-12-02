package com.iznaroth.industrizer.entity.render;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.entity.custom.CopCarEntity;
import com.iznaroth.industrizer.entity.custom.PinchEntity;
import com.iznaroth.industrizer.entity.model.CopCarModel;
import com.iznaroth.industrizer.entity.model.PinchModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PinchRenderer extends MobRenderer<PinchEntity, PinchModel<PinchEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(IndustrizerMod.MOD_ID, "textures/entity/pinch.png");

    public PinchRenderer(EntityRendererManager renderManagerIn){
        super(renderManagerIn, new PinchModel(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(PinchEntity entity){
        return TEXTURE;
    }
}
