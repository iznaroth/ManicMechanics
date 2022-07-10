package com.iznaroth.industrizer.setup;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.container.ModContainers;
import com.iznaroth.industrizer.screen.BureauBlockScreen;
import com.iznaroth.industrizer.screen.CommunicatorBlockScreen;
import com.iznaroth.industrizer.screen.GeneratorBlockScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {


    public static void init(final FMLClientSetupEvent event) {
        ScreenManager.register(ModContainers.GENERATOR_CONTAINER.get(), GeneratorBlockScreen::new);

        ScreenManager.register(ModContainers.BUREAU_CONTAINER.get(), BureauBlockScreen::new);

        ScreenManager.register(ModContainers.COMMUNICATOR_CONTAINER.get(), CommunicatorBlockScreen::new);
    }


    @SubscribeEvent
    public void onTooltipPre(RenderTooltipEvent.Pre event) {
        Item item = event.getStack().getItem();
        if (item.getRegistryName().getNamespace().equals(IndustrizerMod.MOD_ID)) {
            event.setMaxWidth(200);
        }
    }
}
