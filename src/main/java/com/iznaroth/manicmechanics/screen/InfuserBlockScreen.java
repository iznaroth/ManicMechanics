package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.menu.SealingChamberBlockMenu;
import com.iznaroth.manicmechanics.screen.renderer.EnergyInfoArea;
import com.iznaroth.manicmechanics.util.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;

public class SealingChamberBlockScreen extends AbstractContainerScreen<SealingChamberBlockMenu> {

    private ResourceLocation GUI = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/sealing_chamber.png");

    private EnergyInfoArea energyInfoArea;

    public SealingChamberBlockScreen(SealingChamberBlockMenu container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea(){
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(relX + 12, relY + 17, menu.getBlockEntity().getCapability(ForgeCapabilities.ENERGY).orElse(null));
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseX);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);
        renderOperationModeTooltips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderEnergyAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 12, 17, 12, 48)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderOperationModeTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 33, 17, 16, 16)) {
            renderTooltip(pPoseStack, Component.literal("OPERATION MODE: Unimplemented :)"),
                    pMouseX - x, pMouseY - y);
        }
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        energyInfoArea.draw(matrixStack);
        renderProgressArrow(matrixStack, relX, relY);
    }

    private void renderProgressArrow(PoseStack stack, int x, int y){
        blit(stack, x + 83, y + 24, 182, 2, menu.getScaledProgress(), 36);
    }


    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

}
