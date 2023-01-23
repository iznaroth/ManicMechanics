package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.menu.ExporterBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.ButtonCycleC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

public class ExporterBlockScreen extends AbstractContainerScreen<ExporterBlockMenu> {
    private ResourceLocation GUI = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/exporter.png");

    public ExporterBlockScreen(ExporterBlockMenu container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        System.out.println(relX + " " + relY);
        this.blit(poseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        System.out.println("CLICKED AT " + p_97748_ + " " + p_97749_);

        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //in BUTTON 1
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }

        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }
}
