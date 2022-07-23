package com.iznaroth.industrizer.setup;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.container.IndustrizerContainers;
import com.iznaroth.industrizer.render.AnimationTickCounter;
import com.iznaroth.industrizer.render.TileRendererRT;
import com.iznaroth.industrizer.screen.BureauBlockScreen;
import com.iznaroth.industrizer.screen.CommunicatorBlockScreen;
import com.iznaroth.industrizer.screen.GeneratorBlockScreen;
import com.iznaroth.industrizer.tile.IndustrizerTileEntities;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.client.renderer.texture.AtlasTexture.LOCATION_BLOCKS;

public class ClientSetup {


    public static void init(final FMLClientSetupEvent event) {
        ScreenManager.register(IndustrizerContainers.GENERATOR_CONTAINER.get(), GeneratorBlockScreen::new);

        ScreenManager.register(IndustrizerContainers.BUREAU_CONTAINER.get(), BureauBlockScreen::new);

        ScreenManager.register(IndustrizerContainers.COMMUNICATOR_CONTAINER.get(), CommunicatorBlockScreen::new);
    }


    /**
     * @param event
     */
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        // Tell the renderer that the base is rendered using CUTOUT_MIPPED (to match the Block Hopper)
        RenderTypeLookup.setRenderLayer(IndustrizerBlocks.RENDER_TESTER.get(), RenderType.cutoutMipped());
        // Register the custom renderer for our tile entity
        System.out.println("Binding renderer.");
        ClientRegistry.bindTileEntityRenderer(IndustrizerTileEntities.RENDER_TESTER_TILE.get(), TileRendererRT::new);

        MinecraftForge.EVENT_BUS.register(AnimationTickCounter.class);  // counts ticks, used for animation
    }

    //Intercept baked block models from modelRegistry before they're sent to BlockModelShapes.
    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event){

    }

    //Add models to registry that are not referenced in any JSONs.
    @SubscribeEvent
    public static void onModelRegistryEvent(ModelBakeEvent event){
        //Cable core is added by JSON.
        //Tube may not be, depending on how implementation goes - we'll see.
    }

    //For textures that aren't referenced by a block model JSON.
    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.getMap().location() == LOCATION_BLOCKS) {
            //Any model added above is gonna need this.
        }
    }


    @SubscribeEvent
    public void onTooltipPre(RenderTooltipEvent.Pre event) {
        Item item = event.getStack().getItem();
        if (item.getRegistryName().getNamespace().equals(IndustrizerMod.MOD_ID)) {
            event.setMaxWidth(200);
        }
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
