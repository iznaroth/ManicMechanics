package com.iznaroth.manicmechanics.item.custom;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.blockentity.AnimatedBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedBlockItemModel extends AnimatedGeoModel<AnimatedBlockItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedBlockItem object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "geo/thracking_pylon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedBlockItem object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/block/animated/gradient_1px.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedBlockItem animatable) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "animations/thracking_pylon.animation.json");
    }
}
