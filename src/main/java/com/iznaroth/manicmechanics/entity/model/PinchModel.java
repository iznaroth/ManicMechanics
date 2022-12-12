// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.iznaroth.manicmechanics.entity.model;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.PinchEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PinchModel extends AnimatedGeoModel<PinchEntity> {
@Override
public ResourceLocation getModelResource(PinchEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "geo/pinch.geo.json");
        }

@Override
public ResourceLocation getTextureResource(PinchEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/entity/pinch.png");
        }

@Override
public ResourceLocation getAnimationResource(PinchEntity animatable) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "animations/pinch.animation.json");
        }
}