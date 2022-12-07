package com.iznaroth.manicmechanics.client.gui;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Renders this mod's HUDs.
 *
 * @author Choonster
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ManicMechanics.MOD_ID)
public class HUDEventHandler {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final GuiCurrencyHUD currencyHUD = new GuiCurrencyHUD();

    /**
     * Render the current spell when the SpellBook is held in the players hand
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void renderCurrencyHud(final RenderGuiOverlayEvent.Post event) {

        final Player player = minecraft.player;
        currencyHUD.drawHUD(event.getPoseStack(), event.getPartialTick());

    }

}
