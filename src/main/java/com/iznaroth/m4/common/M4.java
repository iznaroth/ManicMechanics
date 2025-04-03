package com.iznaroth.m4.common;

import com.iznaroth.m4.common.registration.*;
import com.iznaroth.m4.datagen.DataGeneration;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(M4.MODID)
public class M4
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "m4";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public M4(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (m4) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        M4Blocks.BLOCKS.register(modEventBus);
        M4Items.ITEMS.register(modEventBus);
        M4CreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        M4BlockEntities.BLOCK_ENTITIES.register(modEventBus);
        M4Containers.MENU_TYPES.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(M4CreativeTabs::addCreative);

        //capabilities
        modEventBus.addListener(this::registerCapabilities);

        modEventBus.addListener(DataGeneration::generate);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                M4BlockEntities.OBLITERATION_PLINTH_ENTITY.get(),
                (o, direction) -> o.getItemHandler()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                M4BlockEntities.MANUFACTORUM_BLOCK_ENTITY.get(),
                (o, direction) -> {
                    if (direction == null) {
                        return o.getItemHandler().get();
                    }
                    if (direction == Direction.DOWN) {
                        return o.getOutputItemHandler().get();
                    }
                    return o.getInputItemHandler().get();
            }
        );

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, M4BlockEntities.HEPC_BLOCK_ENTITY.get(), (o, direction) -> o.getItemHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, M4BlockEntities.HEPC_BLOCK_ENTITY.get(), (o, direction) -> o.getEnergyHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, M4BlockEntities.CHARGING_STATION_BLOCK_ENTITY.get(), (o, direction) -> o.getEnergyHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, M4BlockEntities.CABLE_BLOCK_ENTITY.get(), (o, direction) -> o.getEnergyHandler());

    }
}
