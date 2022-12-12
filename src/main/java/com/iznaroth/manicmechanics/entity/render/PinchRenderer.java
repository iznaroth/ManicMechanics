package com.iznaroth.manicmechanics.entity.render;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.PinchEntity;
import com.iznaroth.manicmechanics.entity.model.PinchModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PinchRenderer extends GeoEntityRenderer<PinchEntity> {

    public PinchRenderer(EntityRendererProvider.Context renderManagerIn){
        super(renderManagerIn, new PinchModel());
        this.shadowRadius = 0F;
    }



    @Override
    public ResourceLocation getTextureLocation(PinchEntity entity){
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/entity/pinch.png");
    }

    @Override
    public RenderType getRenderType(PinchEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.8f, 1.8f, 1.8f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
