package com.iznaroth.manicmechanics.menu;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMMenus {

    public static DeferredRegister<MenuType<?>> MENU_TYPES
            = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ManicMechanics.MOD_ID);

    public static final RegistryObject<MenuType<SimpleCommunicatorBlockMenu>> SIMPLE_COMMUNICATOR_MENU =
            registerMenuType(SimpleCommunicatorBlockMenu::new, "simple_communicator_menu");

    public static final RegistryObject<MenuType<CommunicatorBlockMenu>> COMMUNICATOR_MENU =
            registerMenuType(CommunicatorBlockMenu::new, "communicator");

    public static final RegistryObject<MenuType<GeneratorBlockMenu>> GENERATOR_MENU =
            registerMenuType(GeneratorBlockMenu::new, "generator");

    public static final RegistryObject<MenuType<BureauBlockMenu>> BUREAU_MENU =
            registerMenuType(BureauBlockMenu::new, "bureau");

    public static final RegistryObject<MenuType<SealingChamberBlockMenu>> SEALER_MENU =
            registerMenuType(SealingChamberBlockMenu::new, "sealer");



    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
