package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.menu.ImporterBlockMenu;
import com.iznaroth.manicmechanics.screen.renderer.MarketEntry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Iterator;

public class ImporterBlockScreen extends AbstractContainerScreen<ImporterBlockMenu> {
    private ResourceLocation GUI = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/importer.png");

    private MarketEntry[] listed = new MarketEntry[3];

    private HashMap<Item, Integer> filteredSet;
    int pos;

    private boolean scrollbarSelected = false;

    public ImporterBlockScreen(ImporterBlockMenu container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    protected void init() {
        super.init();

        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        System.out.println(relX + " " + relY);
        Iterator<Item> itr = ForgeRegistries.ITEMS.getValues().iterator();

        for(int i = 0; i < 3; i++){
            listed[i] = new MarketEntry(itr.next(), i, relX, relY);
        }
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
        this.blit(poseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        for(MarketEntry entry : listed){
            if(entry != null)
                entry.draw(poseStack);
        }
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        System.out.println("CLICKED AT " + p_97748_ + " " + p_97749_);

        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //Set 1 - Increase quantity of import
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }

        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //Set 2 - Increase delay on auto import
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }

        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //Inside the scrolling market viewer - six entries
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //Scroll up/down buttons
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }
        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }

        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //click in scrollbar
            this.menu.getBlockEntity().sellEverything(this.minecraft.player);
        }


        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }

    @Override
    public boolean mouseScrolled(double p_98527_, double p_98528_, double p_98529_) {
        if (!scrollbarSelected) {
            return false;
        } else {
            //NOTE - This implementation ignores p_98529_, which vanilla uses to control distance.
            if(p_98529_ > 0){
                pos++;
            } else {
                pos--;
            }
        }

        return super.mouseScrolled(p_98527_, p_98528_, p_98529_);
    }

    private void updateMarketEntries(boolean dir){

    }
}
