package com.iznaroth.manicmechanics.setup;

import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderTypeRegistry {
    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(MMBlocks.VACUUM_HIGHWAY_SEGMENT.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(MMBlocks.TRANSPORT_TUBE.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(MMBlocks.ETCHER.get(), RenderType.translucent());
        });
    }
}


