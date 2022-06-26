package com.iznaroth.industrizer.client.gui;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.api.ICurrency;
import com.iznaroth.industrizer.api.client.IDisplayCurrency;
import com.iznaroth.industrizer.capability.Currency;
import com.iznaroth.industrizer.client.ClientInfo;
import net.minecraft.client.gui.AbstractGui;
import com.iznaroth.industrizer.capability.CurrencyCapability;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiCurrencyHUD extends AbstractGui {
    private static final Minecraft minecraft = Minecraft.getInstance();

    public boolean shouldDisplayBar(){
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();
        return (mainHand.getItem() instanceof IDisplayCurrency && ((IDisplayCurrency) mainHand.getItem()).shouldDisplay(mainHand))
                || (offHand.getItem() instanceof IDisplayCurrency && ((IDisplayCurrency) offHand.getItem()).shouldDisplay(offHand));
    }

    public void drawHUD(MatrixStack ms, float pt) {
        if(!shouldDisplayBar()) {
            System.out.println("Cannot display balance.");
            return;
        }

        ICurrency curr = CurrencyCapability.getBalance(minecraft.player).orElse(null);
        if(curr == null)
            return;

        int offsetLeft = 10;
        int manaLength = 96;
        manaLength = (int) ((manaLength) * ((curr.getCurrentBalance()) / ((double) curr.getCurrentBalance() - 0.0)));

        int height = minecraft.getWindow().getGuiScaledHeight() - 5;

        Minecraft.getInstance().textureManager.bind(new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/mdd.png"));
        blit(ms,offsetLeft, height - 18, 0, 0, 108, 18, 256, 256);
        int manaOffset = (int) (((ClientInfo.ticksInGame + pt) / 3 % (33))) * 6;

        // 96
        //Minecraft.getInstance().textureManager.bind(new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/manabar_gui_mana.png"));
        //blit(ms,offsetLeft + 9, height - 9, 0, manaOffset, manaLength,6, 256, 256);

        //Minecraft.getInstance().textureManager.bind(new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/manabar_gui_border.png"));
        //blit(ms,offsetLeft, height - 17, 0, 18, 108, 20, 256, 256);
    }
}

