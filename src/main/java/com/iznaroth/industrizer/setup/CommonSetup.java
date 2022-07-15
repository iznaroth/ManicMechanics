package com.iznaroth.industrizer.setup;

import com.iznaroth.industrizer.block.ModBlocks;
import com.iznaroth.industrizer.render.TileRenderTester;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonSetup {

    /*
    public static TileEntityType<TileRenderTester> tileEntityDataTypeRenderTester;

    @SubscribeEvent
    public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event) {
        tileEntityDataTypeRenderTester =
                TileEntityType.Builder.of(TileRenderTester::new, ModBlocks.RENDER_TESTER.get()).build(null);  // you probably don't need a datafixer --> null should be fine
        tileEntityDataTypeRenderTester.setRegistryName("industrizer:render_tester_tile");
        event.getRegistry().register(tileEntityDataTypeRenderTester);
    }*/

}
