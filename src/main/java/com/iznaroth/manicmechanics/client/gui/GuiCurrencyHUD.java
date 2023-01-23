package com.iznaroth.manicmechanics.client.gui;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.api.client.IDisplayCurrency;
import com.iznaroth.manicmechanics.client.capability.Currency;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GuiCurrencyHUD {
    private static final Minecraft minecraft = Minecraft.getInstance();

    static int countdown = 0;
    static boolean dir;
    static int amount;

    static float currentBalance;

    private static final ResourceLocation CURRENCY_LOGO = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/mdd.png");

    public static boolean shouldDisplayBar(){
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();

        return (mainHand.getItem() instanceof IDisplayCurrency && ((IDisplayCurrency) mainHand.getItem()).shouldDisplay(mainHand))
                || (offHand.getItem() instanceof IDisplayCurrency && ((IDisplayCurrency) offHand.getItem()).shouldDisplay(offHand));
    }

    public static void triggerChangeCountdown(int amt, boolean upOrDown){
        countdown = 40;
        dir = upOrDown;
        amount = amt;
    }

    private static void updateStored(){
        ICurrency curr = minecraft.player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
        currentBalance = curr.getCurrentBalance();
    }


    public static final IGuiOverlay HUD_CURR = (((gui, poseStack, partialTick, screenWidth, screenHeight) ->  {
        if(!shouldDisplayBar()) {
            return;
        }

        int height = minecraft.getWindow().getGuiScaledHeight() - 5;
        minecraft.getWindow().getGuiScale();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CURRENCY_LOGO);
        GuiComponent.blit(poseStack, (int)((screenWidth*0.5) + 78 + 16), height - 14, 0, 0, 16, 16, 16, 16);

        Component tc = Component.literal(" " + currentBalance);

        int i = screenWidth;
        int j = 12;

        int l = minecraft.font.width(tc);
        int i1 = i / 2 - l / 2;
        int j1 = j - 9;

        int q = minecraft.font.width(tc);
        minecraft.font.drawShadow(poseStack, tc, (float)((screenWidth*0.5) + 78 + 32), (float)(height - 8), 16777215);

        Component ac = Component.literal(" " + amount);

        if(countdown > 0){
            if(dir){
                minecraft.font.drawShadow(poseStack, ac, (float)((screenWidth*0.5) + 78 + 32) + q, (float)(height - 8), 0x09FF00); //Add cash!
            } else {
                minecraft.font.drawShadow(poseStack, ac, (float)((screenWidth*0.5) + 78 + 32) + q, (float)(height - 8), 0xFF0000); //Lose cash!
            }

            countdown--;
        } else {
            updateStored(); //basically we halt checking currency while a visual update is happening
        }

        //Minecraft.getInstance().textureManager.bind(new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/manabar_gui_mana.png"));
        //blit(ms,offsetLeft + 9, height - 9, 0, manaOffset, manaLength,6, 256, 256);

        //Minecraft.getInstance().textureManager.bind(new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/manabar_gui_border.png"));
        //blit(ms,offsetLeft, height - 17, 0, 18, 108, 20, 256, 256);
    }));
}

