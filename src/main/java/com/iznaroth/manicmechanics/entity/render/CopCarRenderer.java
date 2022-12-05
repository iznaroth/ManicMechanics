package com.iznaroth.manicmechanics.entity.render;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.CopCarEntity;
import com.iznaroth.manicmechanics.entity.model.CopCarModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CopCarRenderer extends MobRenderer<CopCarEntity, CopCarModel<CopCarEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(ManicMechanics.MOD_ID, "textures/entity/cop_car.png");

    public CopCarRenderer(EntityRendererManager renderManagerIn){
        super(renderManagerIn, new CopCarModel(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(CopCarEntity entity){
        return TEXTURE;
    }
}
