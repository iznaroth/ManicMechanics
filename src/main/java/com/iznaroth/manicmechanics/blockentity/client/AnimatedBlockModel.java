package com.iznaroth.manicmechanics.blockentity.client;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.blockentity.AnimatedBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedBlockModel extends AnimatedGeoModel<AnimatedBlockEntity> {

    @Override
    public ResourceLocation getModelResource(AnimatedBlockEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "geo/thracking_pylon.geo.json");
    }

    public ResourceLocation getSecondaryModelResource(AnimatedBlockEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "geo/thracking_base_gecko.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedBlockEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/block/thracking_pylon_whole.png");
    }

    public ResourceLocation getSecondaryTextureResource(AnimatedBlockEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/block/thracking_base.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedBlockEntity animatable) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "animations/thracking_pylon.animation.json");
    }
}
