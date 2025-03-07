package com.iznaroth.m4.client;

import com.iznaroth.m4.client.render.ObliterationPlinthRenderer;
import com.iznaroth.m4.common.m4;
import com.iznaroth.m4.common.registration.M4BlockEntities;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = m4.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientSetup {
    @SubscribeEvent
    public static void initClient(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(M4BlockEntities.OBLITERATION_PLINTH_ENTITY.get(), ObliterationPlinthRenderer::new);
    }
}
