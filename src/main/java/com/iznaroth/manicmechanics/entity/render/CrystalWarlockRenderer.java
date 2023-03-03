package com.iznaroth.manicmechanics.entity.render;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.CrystalWarlockEntity;
import com.iznaroth.manicmechanics.entity.custom.GridSkaterEntity;
import com.iznaroth.manicmechanics.entity.model.CrystalWarlockModel;
import com.iznaroth.manicmechanics.entity.model.GridSkaterModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrystalWarlockRenderer extends GeoEntityRenderer<CrystalWarlockEntity> {
    public CrystalWarlockRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrystalWarlockModel());
        this.shadowRadius = 0.1f;
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalWarlockEntity animatable) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/entity/crystal_warlock.png");
    }

    @Override
    public RenderType getRenderType(CrystalWarlockEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
        //return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
