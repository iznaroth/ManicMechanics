package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.container.HEPCContainer;
import com.iznaroth.m4.common.container.ManufactorumContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class M4Containers {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, M4.MODID);

    public static final Supplier<MenuType<ManufactorumContainer>> MANUFACTORUM_CONTAINER = MENU_TYPES.register("manufactorum",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new ManufactorumContainer(windowId, inv.player, data.readBlockPos())));

    public static final Supplier<MenuType<HEPCContainer>> HEPC_CONTAINER = MENU_TYPES.register("hyperfield_extract_polarization_chamber",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new HEPCContainer(windowId, inv.player, data.readBlockPos())));

}
