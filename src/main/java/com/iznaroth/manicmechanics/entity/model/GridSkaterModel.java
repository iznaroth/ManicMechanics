package com.iznaroth.manicmechanics.entity.model;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.GridSkaterEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GridSkaterModel extends AnimatedGeoModel<GridSkaterEntity> {
    @Override
    public ResourceLocation getModelResource(GridSkaterEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "geo/grid_skater.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GridSkaterEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/entity/grid_skater.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GridSkaterEntity animatable) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "animations/grid_skater.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(GridSkaterEntity entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
