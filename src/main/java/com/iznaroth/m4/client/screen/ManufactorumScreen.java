package com.iznaroth.m4.client.screen;

import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.container.ManufactorumContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ManufactorumScreen extends AbstractContainerScreen<ManufactorumContainer> {
    private final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(M4.MODID, "textures/gui/manufactorum.png");

    public ManufactorumScreen(ManufactorumContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.inventoryLabelY = this.imageHeight - 110;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
