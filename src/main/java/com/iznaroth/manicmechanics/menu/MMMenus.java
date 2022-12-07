package com.iznaroth.manicmechanics.menu;

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

    public static DeferredRegister<MenuType<?>> MENU_TYPES
            = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ManicMechanics.MOD_ID);

    public static final RegistryObject<MenuType<GeneratorBlockMenu>> GENERATOR_MENU = MENU_TYPES.register("generator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new GeneratorBlockMenu(windowId, inv, data);
    }));

    public static final RegistryObject<MenuType<BureauBlockMenu>> BUREAU_MENU = MENU_TYPES.register("bureau", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new BureauBlockMenu(windowId, inv, data);
    }));

    public static final RegistryObject<MenuType<CommunicatorBlockMenu>> COMMUNICATOR_MENU = MENU_TYPES.register("communicator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new CommunicatorBlockMenu(windowId, inv, data);
    }));

    public static final RegistryObject<MenuType<SealingChamberBlockMenu>> SEALER_MENU = MENU_TYPES.register("sealing_chamber", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new SealingChamberBlockMenu(windowId, inv, data);
    }));

    public static final RegistryObject<MenuType<SimpleCommunicatorBlockMenu>> SIMPLE_COMMUNICATOR_MENU = MENU_TYPES.register("simple_communicator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new SimpleCommunicatorBlockMenu(windowId, inv, data);
    }));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
