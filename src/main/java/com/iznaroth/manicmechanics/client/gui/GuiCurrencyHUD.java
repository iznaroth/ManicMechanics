package com.iznaroth.manicmechanics.client.gui;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.api.client.IDisplayCurrency;
import net.minecraft.client.gui.AbstractGui;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

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
            return;
        }

        ICurrency curr = CurrencyCapability.getBalance(minecraft.player).orElse(null);
        if(curr == null)
            return;

        int height = minecraft.getWindow().getGuiScaledHeight() - 5;

        Minecraft.getInstance().textureManager.bind(new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/mdd.png"));
        blit(ms, 3, 3, 0, 0, 16, 16, 16, 16);

        StringTextComponent tc = new StringTextComponent(" " + curr.getCurrentBalance());

        int i = this.minecraft.getWindow().getGuiScaledWidth();
        int j = 12;

        int l = this.minecraft.font.width(tc);
        int i1 = i / 2 - l / 2;
        int j1 = j - 9;
        this.minecraft.font.drawShadow(ms, tc, (float)22, (float)8, 16777215);

        //Minecraft.getInstance().textureManager.bind(new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/manabar_gui_mana.png"));
        //blit(ms,offsetLeft + 9, height - 9, 0, manaOffset, manaLength,6, 256, 256);

        //Minecraft.getInstance().textureManager.bind(new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/manabar_gui_border.png"));
        //blit(ms,offsetLeft, height - 17, 0, 18, 108, 20, 256, 256);
    }
}

