package com.iznaroth.manicmechanics.container;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MMContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, ManicMechanics.MOD_ID);

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

    public static final RegistryObject<ContainerType<SealingChamberBlockContainer>> SEALER_CONTAINER = CONTAINERS.register("sealing_chamber", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new SealingChamberBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ContainerType<SimpleCommunicatorBlockContainer>> SIMPLE_COMMUNICATOR_CONTAINER = CONTAINERS.register("simple_communicator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new SimpleCommunicatorBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
