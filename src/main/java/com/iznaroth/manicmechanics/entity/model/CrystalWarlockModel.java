package com.iznaroth.manicmechanics.entity.model;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.CrystalWarlockEntity;
import com.iznaroth.manicmechanics.entity.custom.GridSkaterEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CrystalWarlockModel extends AnimatedGeoModel<CrystalWarlockEntity> {
    @Override
    public ResourceLocation getModelResource(CrystalWarlockEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "geo/crystal_warlock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrystalWarlockEntity object) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "textures/entity/crystal_warlock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrystalWarlockEntity animatable) {
        return new ResourceLocation(ManicMechanics.MOD_ID, "animations/crystal_wizard.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(CrystalWarlockEntity entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
    }
}
