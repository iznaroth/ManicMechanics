package com.iznaroth.manicmechanics.container;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMMenus {

    public static DeferredRegister<MenuType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ManicMechanics.MOD_ID);

    public static final RegistryObject<MenuType<GeneratorBlockContainer>> GENERATOR_CONTAINER = CONTAINERS.register("generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new GeneratorBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<BureauBlockMenu>> BUREAU_CONTAINER = CONTAINERS.register("bureau", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BureauBlockMenu(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<CommunicatorBlockContainer>> COMMUNICATOR_CONTAINER = CONTAINERS.register("communicator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new CommunicatorBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<SealingChamberBlockContainer>> SEALER_CONTAINER = CONTAINERS.register("sealing_chamber", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new SealingChamberBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<SimpleCommunicatorBlockContainer>> SIMPLE_COMMUNICATOR_CONTAINER = CONTAINERS.register("simple_communicator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new SimpleCommunicatorBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
