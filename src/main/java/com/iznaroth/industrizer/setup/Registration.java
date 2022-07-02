package com.iznaroth.industrizer.setup;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.block.BureauBlockContainer;
import com.iznaroth.industrizer.block.GeneratorBlockContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
    /*
    A bit of a note -
     - The first part of this mod (ModBlocks, ModItems, IndustrizerMod) were made with one tutorial, and
     - the rest were made with McJty's content. The old tutorials used a registration style that registered blocks and items
     - alongside some logic for it in order to 'automate' it, whereas McJty just rolled it into this file. I'm not sure which approach is better
     - so we're gonna keep working and see. For now, this file is kinda just registering tileentities, containers and entities (which is horrendous practice)
     SO CHANGE THIS SHIT ASAP!
     */


    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, IndustrizerMod.MOD_ID);

    public static void init() {
        //BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

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
}
