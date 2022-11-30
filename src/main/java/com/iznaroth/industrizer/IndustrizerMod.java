package com.iznaroth.industrizer;

import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.capability.CurrencyCapability;
import com.iznaroth.industrizer.capability.SuspicionCapability;
import com.iznaroth.industrizer.container.IndustrizerContainers;
import com.iznaroth.industrizer.entity.ModEntityTypes;
import com.iznaroth.industrizer.entity.render.CopCarRenderer;
import com.iznaroth.industrizer.item.IndustrizerItems;
import com.iznaroth.industrizer.logistics.ActiveConnectionQueue;
import com.iznaroth.industrizer.networking.IndustrizerMessages;
import com.iznaroth.industrizer.setup.ClientSetup;
import com.iznaroth.industrizer.setup.Config;
import com.iznaroth.industrizer.tile.IndustrizerTileEntities;
import com.iznaroth.industrizer.util.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
@Mod(IndustrizerMod.MOD_ID)

public class IndustrizerMod
{
    public static final String MOD_ID = "industrizer";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public IndustrizerMod() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        IndustrizerItems.register(eventBus);
        IndustrizerBlocks.register(eventBus);

        ModSoundEvents.register(eventBus);

        ModEntityTypes.register(eventBus);

        IndustrizerTileEntities.register(eventBus);

        IndustrizerContainers.register(eventBus); //NOTE - May need to rearrange for order compliance?

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
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.COP_CAR.get(), CopCarRenderer::new);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            IndustrizerMessages.register();
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
        eventBus.register(com.iznaroth.industrizer.setup.ClientSetup.class);
        eventBus.register(com.iznaroth.industrizer.setup.CommonSetup.class);
    }
}
