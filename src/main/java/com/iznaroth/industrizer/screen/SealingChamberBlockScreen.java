package com.iznaroth.industrizer.screen;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.container.BureauBlockContainer;
import com.iznaroth.industrizer.container.SealingChamberBlockContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SealingChamberBlockScreen extends ContainerScreen<SealingChamberBlockContainer> {

    private ResourceLocation GUI = new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/sealing_chamber.png");

    public SealingChamberBlockScreen(SealingChamberBlockContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        renderPowerBar(matrixStack, relX, relY);
    }

    private void renderPowerBar(MatrixStack matrixStack, int x, int y){
        //NOTE - Power renderer will need to recieve this update thru a packet - client -> server communication.
        blit(matrixStack, x + 12, y + 65, 181, 62, 12, -this.getMenu().getEnergy());
    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;


        double d0 = p_231044_1_ - (double)(i + 17);
        double d1 = p_231044_3_ - (double)(j + 145 + 8);
        if (d0 >= 0.0D && d1 >= 0.0D && d0 < 59.0D && d1 < 8.0D && this.menu.clickMenuButton(this.minecraft.player, 0)) {
            System.out.println("Selling item.");
            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            return true;
        }

        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

}
