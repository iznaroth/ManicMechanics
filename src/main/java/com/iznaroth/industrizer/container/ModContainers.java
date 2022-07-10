package com.iznaroth.industrizer.container;

import com.iznaroth.industrizer.IndustrizerMod;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, IndustrizerMod.MOD_ID);

    public static final RegistryObject<ContainerType<GeneratorBlockContainer>> GENERATOR_CONTAINER = CONTAINERS.register("generator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new GeneratorBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ContainerType<BureauBlockContainer>> BUREAU_CONTAINER = CONTAINERS.register("bureau", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new BureauBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ContainerType<CommunicatorBlockContainer>> COMMUNICATOR_CONTAINER = CONTAINERS.register("communicator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new CommunicatorBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
