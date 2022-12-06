package com.iznaroth.manicmechanics.entity.render;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.PinchEntity;
import com.iznaroth.manicmechanics.entity.model.PinchModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PinchRenderer extends MobRenderer<PinchEntity, PinchModel<PinchEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(ManicMechanics.MOD_ID, "textures/entity/pinch.png");

    public PinchRenderer(EntityRendererProvider.Context renderManagerIn){
        super(renderManagerIn, new PinchModel(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(PinchEntity entity){
        return TEXTURE;
    }
}
