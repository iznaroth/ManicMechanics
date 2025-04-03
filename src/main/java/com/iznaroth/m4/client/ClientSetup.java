package com.iznaroth.m4.client;

import com.iznaroth.m4.client.model.CableTubeModelLoader;
import com.iznaroth.m4.client.render.ObliterationPlinthRenderer;
import com.iznaroth.m4.client.screen.HEPCScreen;
import com.iznaroth.m4.client.screen.ManufactorumScreen;
import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.registration.M4BlockEntities;
import com.iznaroth.m4.common.registration.M4Containers;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = M4.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientSetup {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(M4Containers.MANUFACTORUM_CONTAINER.get(), ManufactorumScreen::new);
        event.register(M4Containers.HEPC_CONTAINER.get(), HEPCScreen::new);
    }

    @SubscribeEvent
    public static void initClient(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(M4BlockEntities.OBLITERATION_PLINTH_ENTITY.get(), ObliterationPlinthRenderer::new);
    }

    @SubscribeEvent
    public static void modelInit(ModelEvent.RegisterGeometryLoaders event) {
        CableTubeModelLoader.register(event);
    }

    /*
    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register(new FacadeBlockColor(), Registration.FACADE_BLOCK.get());
    }

    */
}
