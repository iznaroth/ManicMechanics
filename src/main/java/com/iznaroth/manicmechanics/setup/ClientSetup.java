package com.iznaroth.manicmechanics.setup;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.blockentity.MMBlockEntities;
import com.iznaroth.manicmechanics.blockentity.client.AnimatedBlockRenderer;
import com.iznaroth.manicmechanics.client.gui.GuiCurrencyHUD;
import com.iznaroth.manicmechanics.entity.MMEntityTypes;
import com.iznaroth.manicmechanics.entity.render.CrystalWarlockRenderer;
import com.iznaroth.manicmechanics.entity.render.GridSkaterRenderer;
import com.iznaroth.manicmechanics.render.AnimationTickCounter;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;

public class ClientSetup {


    public static void init(final FMLClientSetupEvent event) {
        //ScreenManager.register(MMMenus.GENERATOR_MENU.get(), GeneratorBlockScreen::new);

        //ScreenManager.register(MMMenus.EXPORTER_MENU.get(), BureauBlockScreen::new);

        //ScreenManager.register(MMMenus.COMMUNICATOR_MENU.get(), CommunicatorBlockScreen::new);

        //ScreenManager.register(MMMenus.SEALER_MENU.get(), SealingChamberBlockScreen::new);

        //ScreenManager.register(MMMenus.SIMPLE_COMMUNICATOR_MENU.get(), SimpleCommunicatorBlockScreen::new);
    }


    /**
     * @param event
     */
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(AnimationTickCounter.class);  // counts ticks, used for animation
    }

    //For textures that aren't referenced by a block model JSON.
    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location() == LOCATION_BLOCKS) {
            //Any model added above is gonna need this.
        }
    }


    @SubscribeEvent
    public void onTooltipPre(RenderTooltipEvent.Pre event) {
        Item item = event.getItemStack().getItem();

        if (Registry.ITEM.getKey(item).getNamespace().equals(ManicMechanics.MOD_ID)) {
            event.setX(200);
        }
    }

    private static final Logger LOGGER = LogManager.getLogger();

    @Mod.EventBusSubscriber(modid = ManicMechanics.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            System.out.println("REGISTER CURRENCY!");
            event.registerAboveAll("currency", GuiCurrencyHUD.HUD_CURR);
        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            System.out.println("REGISTER RENDERERS!");
            event.registerBlockEntityRenderer(MMBlockEntities.ANIMATED_BE.get(), AnimatedBlockRenderer::new);
            event.registerEntityRenderer(MMEntityTypes.GRID_SKATER.get(), GridSkaterRenderer::new);
            event.registerEntityRenderer(MMEntityTypes.CRYSTAL_WARLOCK.get(), CrystalWarlockRenderer::new);
        }



    }
}
