package com.iznaroth.manicmechanics;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import com.iznaroth.manicmechanics.client.capability.SuspicionCapability;
import com.iznaroth.manicmechanics.container.MMContainers;
import com.iznaroth.manicmechanics.entity.ModEntityTypes;
import com.iznaroth.manicmechanics.entity.custom.PinchEntity;
import com.iznaroth.manicmechanics.entity.render.CopCarRenderer;
import com.iznaroth.manicmechanics.entity.render.PinchRenderer;
import com.iznaroth.manicmechanics.events.ModEvents;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.logistics.ActiveConnectionQueue;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.recipe.MMRecipeTypes;
import com.iznaroth.manicmechanics.setup.ClientSetup;
import com.iznaroth.manicmechanics.setup.Config;
import com.iznaroth.manicmechanics.tile.MMTileEntities;
import com.iznaroth.manicmechanics.util.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ManicMechanics.MOD_ID)

public class ManicMechanics
{
    public static final String MOD_ID = "manicmechanics";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public ManicMechanics() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        MMItems.register(eventBus);
        MMBlocks.register(eventBus);

        ModSoundEvents.register(eventBus);
        ModEntityTypes.register(eventBus);

        MMTileEntities.register(eventBus);
        MMContainers.register(eventBus); //NOTE - May need to rearrange for order compliance?

        MMRecipeTypes.register(eventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);

        // Register the setup method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        registerCommonEvents(eventBus);

        forgeEventBus.register(ActiveConnectionQueue.class);
        forgeEventBus.register(new ModEvents());

        eventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        forgeEventBus.register(this);
    }

    public void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        CurrencyCapability.register();
        SuspicionCapability.register();
        EntitySpawnPlacementRegistry.register(ModEntityTypes.PINCH.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PinchEntity::checkSpawnRules);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.COP_CAR.get(), CopCarRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PINCH.get(), PinchRenderer::new);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MMMessages.register();
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    public void registerCommonEvents(IEventBus eventBus) {


        //----------------
        eventBus.register(com.iznaroth.manicmechanics.setup.ClientSetup.class);
        eventBus.register(com.iznaroth.manicmechanics.setup.CommonSetup.class);
    }
}