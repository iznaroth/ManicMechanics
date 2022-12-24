package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.menu.CondenserBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.ButtonCycleC2SPacket;
import com.iznaroth.manicmechanics.screen.renderer.EnergyInfoArea;
import com.iznaroth.manicmechanics.screen.renderer.FluidTankRenderer;
import com.iznaroth.manicmechanics.util.MouseUtil;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;

public class CondenserBlockScreen extends AbstractContainerScreen<CondenserBlockMenu> {

    private ResourceLocation GUI = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/condenser.png");

    private EnergyInfoArea energyInfoArea;

    public CondenserBlockScreen(CondenserBlockMenu container, Inventory inv, Component name) {
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
    }

    private void renderEnergyAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 12, 17, 12, 48)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
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
    }

    private void renderButtonModes(PoseStack stack, int x, int y){
        blit(stack, x + 44, y + 12, 180 + (13 * menu.getModeFor(0)), 11, 12, 10); //BUTTON 1
        blit(stack, x + 69, y + 12, 180 + (13 * menu.getModeFor(1)), 11, 12, 10); //BUTTON 2
        blit(stack, x + 94, y + 12, 180 + (13 * menu.getModeFor(2)), 11, 12, 10); //BUTTON 3
        blit(stack, x + 119, y + 12, 180 + (13 * menu.getModeFor(3)), 11, 12, 10); //BUTTON 4
        blit(stack, x + 144, y + 12, 180 + (13 * menu.getModeFor(4)), 11, 12, 10); //BUTTON 5
        blit(stack, x + 56, y + 52, 180 + (13 * menu.getModeFor(5)), 23, 12, 10); //BUTTON 6
        blit(stack, x + 82, y + 52, 180 + (13 * menu.getModeFor(6)), 23, 12, 10); //BUTTON 7
        blit(stack, x + 107, y + 52, 180 + (13 * menu.getModeFor(7)), 23, 12, 10); //BUTTON 8
        blit(stack, x + 132, y + 52, 180 + (13 * menu.getModeFor(8)), 23, 12, 10); //BUTTON 9
    }

    private void renderProgressArrow(PoseStack stack, int x, int y){
        blit(stack, x + 64, y + 29, 181, 12, 16, menu.getScaledProgress());
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

        //TOP LAYER - ITEM OPERATIONS
        if((p_97748_ > x+42 && p_97748_ < x+57) && (p_97749_ > y+10 && p_97749_ < y+23)){ //in BUTTON 1
            this.menu.getBlockEntity().cycleForward(0);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(0, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }else if((p_97748_ > x+67 && p_97748_ < x+82) && (p_97749_ > y+10 && p_97749_ < y+23)){ //in BUTTON 2
            this.menu.getBlockEntity().cycleForward(1);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(1, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        } else if((p_97748_ > x+92 && p_97748_ < x+107) && (p_97749_ > y+10 && p_97749_ < y+23)){ //in BUTTON 3
            this.menu.getBlockEntity().cycleForward(2);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(2, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        } else if((p_97748_ > x+117 && p_97748_ < x+132) && (p_97749_ > y+10 && p_97749_ < y+23)){ //in BUTTON 3
            this.menu.getBlockEntity().cycleForward(3);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(3, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        } else if((p_97748_ > x+142 && p_97748_ < x+157) && (p_97749_ > y+10 && p_97749_ < y+23)){ //in BUTTON 3
            this.menu.getBlockEntity().cycleForward(4);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(4, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }

        //BOTTOM LAYER - TOOL OPERATIONS
        if((p_97748_ > x+54 && p_97748_ < x+69) && (p_97749_ > y+50 && p_97749_ < y+63)){ //in BUTTON 1
            this.menu.getBlockEntity().cycleForward(5);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(5, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }else if((p_97748_ > x+80 && p_97748_ < x+95) && (p_97749_ > y+50 && p_97749_ < y+63)){ //in BUTTON 2
            this.menu.getBlockEntity().cycleForward(6);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(6, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        } else if((p_97748_ > x+105 && p_97748_ < x+120) && (p_97749_ > y+50 && p_97749_ < y+63)){ //in BUTTON 3
            this.menu.getBlockEntity().cycleForward(7);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(7, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        } else if((p_97748_ > x+130 && p_97748_ < x+145) && (p_97749_ > y+50 && p_97749_ < y+63)){ //in BUTTON 3
            this.menu.getBlockEntity().cycleForward(8);
            MMMessages.sendToServer(new ButtonCycleC2SPacket(8, 1, this.menu.getBlockEntity().getBlockPos()));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }

        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }

    @Override
    public boolean mouseReleased(double p_97812_, double p_97813_, int p_97814_) {
        return super.mouseReleased(p_97812_, p_97813_, p_97814_);
    }
}
