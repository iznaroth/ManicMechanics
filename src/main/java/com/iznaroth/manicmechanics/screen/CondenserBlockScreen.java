package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.menu.InfuserBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.ButtonCycleC2SPacket;
import com.iznaroth.manicmechanics.screen.renderer.EnergyInfoArea;
import com.iznaroth.manicmechanics.screen.renderer.FluidTankRenderer;
import com.iznaroth.manicmechanics.util.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.Optional;

public class InfuserBlockScreen extends AbstractContainerScreen<InfuserBlockMenu> {

    private ResourceLocation GUI = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/infuser.png");

    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer frenderer;

    public InfuserBlockScreen(InfuserBlockMenu container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        frenderer = new FluidTankRenderer(16000, true, 16, 67);
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
        renderFluidAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderEnergyAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 12, 17, 12, 48)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderFluidAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 115, 9)) {
            renderTooltip(pPoseStack, frenderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
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
        renderButtonModes(matrixStack, relX, relY);
        renderProgressArrow(matrixStack, relX, relY);
        frenderer.render(matrixStack, relX + 115, relY + 9, menu.getFluidStack());
    }

    private void renderButtonModes(PoseStack stack, int x, int y){
        blit(stack, x + 33, y + 20, 180 + (13 * menu.getModeFor(0)), 36, 12, 10); //BUTTON 1
        blit(stack, x + 33, y + 36, 180 + (13 * menu.getModeFor(1)), 36, 12, 10); //BUTTON 2
        blit(stack, x + 33, y + 52, 180 + (13 * menu.getModeFor(2)), 36, 12, 10); //BUTTON 3
    }

    private void renderProgressArrow(PoseStack stack, int x, int y){
        blit(stack, x + 64, y + 29, 181, 12, 16, menu.getScaledProgress());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, frenderer.getWidth(), frenderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }


    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        System.out.println("CLICKED AT " + p_97748_ + " " + p_97749_);


        //TODO - this likely fails to sync for any OTHER clients.
        if((p_97748_ > x+31 && p_97748_ < x+46) && (p_97749_ > y+18 && p_97749_ < y+31)){ //in BUTTON 1
            this.menu.getBlockEntity().cycleForward(0);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(0, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }else if((p_97748_ > x+31 && p_97748_ < x+46) && (p_97749_ > y+34 && p_97749_ < y+47)){ //in BUTTON 2
            this.menu.getBlockEntity().cycleForward(1);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(1, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        } else if((p_97748_ > x+31 && p_97748_ < x+46) && (p_97749_ > y+50 && p_97749_ < y+63)){ //in BUTTON 3
            this.menu.getBlockEntity().cycleForward(2);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(2, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }

        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }

    @Override
    public boolean mouseReleased(double p_97812_, double p_97813_, int p_97814_) {
        return super.mouseReleased(p_97812_, p_97813_, p_97814_);
    }
}
