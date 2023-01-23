package com.iznaroth.manicmechanics;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.menu.MMMenus;
import com.iznaroth.manicmechanics.entity.ModEntityTypes;
import com.iznaroth.manicmechanics.entity.custom.PinchEntity;
import com.iznaroth.manicmechanics.entity.render.CopCarRenderer;
import com.iznaroth.manicmechanics.entity.render.PinchRenderer;
import com.iznaroth.manicmechanics.events.ModEvents;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.logistics.ActiveConnectionQueue;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.recipe.MMRecipeTypes;
import com.iznaroth.manicmechanics.screen.*;
import com.iznaroth.manicmechanics.setup.ClientSetup;
import com.iznaroth.manicmechanics.setup.Config;
import com.iznaroth.manicmechanics.blockentity.MMBlockEntities;
import com.iznaroth.manicmechanics.util.ModSoundEvents;
import com.iznaroth.manicmechanics.world.feature.MMConfiguredFeatures;
import com.iznaroth.manicmechanics.world.feature.MMPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
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

        MMConfiguredFeatures.register(eventBus);
        MMPlacedFeatures.register(eventBus);

        ModSoundEvents.register(eventBus);
        ModEntityTypes.register(eventBus);

        MMBlockEntities.register(eventBus);


        MMRecipeTypes.register(eventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);

        MMMenus.register(FMLJavaModLoadingContext.get().getModEventBus());

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
        SpawnPlacements.register(ModEntityTypes.PINCH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PinchEntity::checkSpawnRules);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client

        //NOTE - May need to rearrange for order compliance?
        FMLJavaModLoadingContext.get().getModEventBus().register(com.iznaroth.manicmechanics.setup.ClientSetup.class);

        EntityRenderers.register(ModEntityTypes.COP_CAR.get(), CopCarRenderer::new);
        EntityRenderers.register(ModEntityTypes.PINCH.get(), PinchRenderer::new);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MMMessages.register();
        });

        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
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


    public void registerCommonEvents(IEventBus eventBus) {


        //----------------
        eventBus.register(com.iznaroth.manicmechanics.setup.CommonSetup.class);
    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            System.out.println("Do we ever actually see onClientSetup?");
            MenuScreens.register(MMMenus.SIMPLE_COMMUNICATOR_MENU.get(), SimpleCommunicatorBlockScreen::new);
            MenuScreens.register(MMMenus.COMMUNICATOR_MENU.get(), CommunicatorBlockScreen::new);
            MenuScreens.register(MMMenus.GENERATOR_MENU.get(), GeneratorBlockScreen::new);
            MenuScreens.register(MMMenus.EXPORTER_MENU.get(), ExporterBlockScreen::new);
            MenuScreens.register(MMMenus.SEALER_MENU.get(), SealingChamberBlockScreen::new);
            MenuScreens.register(MMMenus.INFUSER_MENU.get(), InfuserBlockScreen::new);
            MenuScreens.register(MMMenus.CONDENSER_MENU.get(), CondenserBlockScreen::new);
            MenuScreens.register(MMMenus.ASSEMBLER_MENU.get(), AssemblerBlockScreen::new);
            MenuScreens.register(MMMenus.IMPORTER_MENU.get(), ImporterBlockScreen::new);

            //EntityRenderers.register(ModEntityTypes.PINCH.get(), ChomperRenderer::new);
        }
    }
}

