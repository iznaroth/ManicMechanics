package com.iznaroth.industrizer.setup;

import com.iznaroth.industrizer.block.IndustrizerBlocks;
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
            RenderTypeLookup.setRenderLayer(IndustrizerBlocks.VACUUM_HIGHWAY_SEGMENT.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(IndustrizerBlocks.TRANSPORT_TUBE.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(IndustrizerBlocks.ETCHER.get(), RenderType.translucent());
        });
    }
}


