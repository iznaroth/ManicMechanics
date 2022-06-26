package com.iznaroth.industrizer.capability.client;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.capability.CurrencyConfig;
import com.iznaroth.industrizer.capability.data.Currency;
import com.iznaroth.industrizer.item.ModItems;
import com.iznaroth.industrizer.item.WalletItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ClientBossInfo;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;

@OnlyIn(Dist.CLIENT)
public class CurrencyOverlay extends AbstractGui {

    private final Minecraft minecraft;

    private static final ResourceLocation MDD_LOGO_LOCATION = new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/mdd.png");

    private ITextComponent balance;

    public boolean shouldDisplayBar(){
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();
        return (mainHand.getItem() instanceof WalletItem
                || offHand.getItem() instanceof WalletItem);
    }


    public CurrencyOverlay (Minecraft minecraft) { this.minecraft = minecraft; }

    public void drawHUD(MatrixStack ms, float pt) {
        if(!shouldDisplayBar())
            return;

        Currency curr = CapabilityRegistry.getMana(minecraft.player).orElse(null);
        if(mana == null)
            return;

        int offsetLeft = 10;
        int manaLength = 96;
        manaLength = (int) ((manaLength) * ((mana.getCurrentMana()) / ((double) mana.getMaxMana() - 0.0)));

        int height = minecraft.getWindow().getGuiScaledHeight() - 5;

        Minecraft.getInstance().textureManager.bind(new ResourceLocation(ArsNouveau.MODID, "textures/gui/manabar_gui_border.png"));
        blit(ms,offsetLeft, height - 18, 0, 0, 108, 18, 256, 256);
        int manaOffset = (int) (((ClientInfo.ticksInGame + pt) / 3 % (33))) * 6;

        // 96
        Minecraft.getInstance().textureManager.bind(new ResourceLocation(ArsNouveau.MODID, "textures/gui/manabar_gui_mana.png"));
        blit(ms,offsetLeft + 9, height - 9, 0, manaOffset, manaLength,6, 256, 256);

        Minecraft.getInstance().textureManager.bind(new ResourceLocation(ArsNouveau.MODID, "textures/gui/manabar_gui_border.png"));
        blit(ms,offsetLeft, height - 17, 0, 18, 108, 20, 256, 256);
    }
}
