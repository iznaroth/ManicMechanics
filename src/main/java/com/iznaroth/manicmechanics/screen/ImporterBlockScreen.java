package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import com.iznaroth.manicmechanics.menu.ImporterBlockMenu;
import com.iznaroth.manicmechanics.screen.renderer.MarketEntry;
import com.iznaroth.manicmechanics.tools.BlockValueGenerator;
import com.iznaroth.manicmechanics.util.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ImporterBlockScreen extends AbstractContainerScreen<ImporterBlockMenu> {
    private ResourceLocation GUI = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/importer.png");

    private MarketEntry[] listed = new MarketEntry[3];

    private ArrayList<Item> filteredItemSet;
    int pos;

    int order_quantity = 1;

    int tick_delay = 20;

    private boolean scrollbarSelected = false;
    private boolean searchbarSelected = false;

    private ItemStack selected = ItemStack.EMPTY;

    private EditBox searchBox;

    public ImporterBlockScreen(ImporterBlockMenu container, Inventory inv, Component name) {
        super(container, inv, name);

        filteredItemSet = new ArrayList<>(BlockValueGenerator.getInitializeMap().keySet());
    }

    @Override
    protected void init() {
        super.init();

        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        System.out.println(relX + " " + relY);
        Iterator<Item> itr = filteredItemSet.iterator();

        for(int i = 0; i < 3; i++){
            listed[i] = new MarketEntry(itr.next(), i, relX, relY);
        }

        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.searchBox = new EditBox(this.font, this.leftPos + 92, this.topPos + 9, 69, 8, Component.translatable("itemGroup.search"));
        this.searchBox.setMaxLength(50);
        this.searchBox.setBordered(false);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);
        this.addWidget(this.searchBox);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseX);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderMarketEntryTooltips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderMarketEntryTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 92, 21, 16, 16)) {
            renderTooltip(pPoseStack, listed[0].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 92, 39, 16, 16)) {
            renderTooltip(pPoseStack, listed[1].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 92, 57, 16, 16)) {
            renderTooltip(pPoseStack, listed[2].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 65, 6, 16, 16)){
            renderTooltip(pPoseStack, selected.getTooltipLines(null, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
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

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.renderGuiItem(selected, relX + 65, relY + 6);

        Minecraft.getInstance().font.drawShadow(poseStack, Component.literal(" " + order_quantity + " "), relX + 63, relY + 27, 0xFFFFFF);
        Minecraft.getInstance().font.drawShadow(poseStack, Component.literal(" " + tick_delay + " "), relX + 63, relY + 36, 0xFFFFFF);
        //Minecraft.getInstance().font.drawShadow(poseStack, Component.literal(" " + BlockValueGenerator.current_mapping.get(selected.getItem()) * order_quantity + " "), relX, relY + 28, 0xFF0000);

        this.searchBox.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        System.out.println("CLICKED AT " + p_97748_ + " " + p_97749_);
        if((p_97748_ > x+60 && p_97748_ < x+66) && (p_97749_ > y+25 && p_97749_ < y+31)){ //Set 1 - Increase quantity of import
            order_quantity--;
            if(order_quantity < 0){
                order_quantity = 64;
            }
        }
        if((p_97748_ > x+79 && p_97748_ < x+85) && (p_97749_ > y+25 && p_97749_ < y+31)){ //
            order_quantity++;
            if(order_quantity > 64){
                order_quantity = 0;
            }
        }

        if((p_97748_ > x+60 && p_97748_ < x+66) && (p_97749_ > y+33 && p_97749_ < y+39)){ //Set 2 - Increase delay on auto import
            tick_delay--;
        }
        if((p_97748_ > x+79 && p_97748_ < x+85) && (p_97749_ > y+33 && p_97749_ < y+39)){ //
            tick_delay++; //TODO - wrap func, shift-click to x8
        }

        if((p_97748_ > x+91 && p_97748_ < x+160) && (p_97749_ > y+21 && p_97749_ < y+37)){ //Inside the scrolling market viewer - three entries
            selectEntry(0);
        }
        if((p_97748_ > x+91 && p_97748_ < x+160) && (p_97749_ > y+38 && p_97749_ < y+55)){ //
            selectEntry(1);
        }
        if((p_97748_ > x+91 && p_97748_ < x+160) && (p_97749_ > y+56 && p_97749_ < y+72)){ //
            selectEntry(2);
        }
        if((p_97748_ > x+161 && p_97748_ < x+168) && (p_97749_ > y+21 && p_97749_ < y+27)){ //Scroll up/down buttons
            this.scrollEntriesUp();
        }
        if((p_97748_ > x+161 && p_97748_ < x+168) && (p_97749_ > y+66 && p_97749_ < y+72)){ //
            this.scrollEntriesDown();
        }

        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //click in scrollbar

        }

        if((p_97748_ > x+92 && p_97748_ < x+168) && (p_97749_ > y+9 && p_97749_ < y+16)){ //click in searchbar
            searchbarSelected = true;
            this.searchBox.setFocus(true);
        } else { //any other click unfocus
            searchbarSelected = false;
            this.searchBox.setFocus(false);
        }

        if((p_97748_ > x+9 && p_97748_ < x+45) && (p_97749_ > y+54 && p_97749_ < y+66)){ //ORDER
            //orderItem();

            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
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

    public void scrollEntriesUp(){
        pos--;

        if(pos < 0){
            pos = 0;
            return;
        }

        listed[2].rebuildOnCycle(filteredItemSet.get(pos+2));
        listed[1].rebuildOnCycle(filteredItemSet.get(pos+1));
        listed[0].rebuildOnCycle(filteredItemSet.get(pos));
    }

     public void scrollEntriesDown(){
        pos++;
         if(pos+2 > filteredItemSet.size()-1){
             pos--;
             return;
         }

         listed[0].rebuildOnCycle(filteredItemSet.get(pos));
         listed[1].rebuildOnCycle(filteredItemSet.get(pos + 1));
         listed[2].rebuildOnCycle(filteredItemSet.get(pos + 2));

         for(MarketEntry entry : listed){
             if(entry != null)
                 System.out.println(entry.getTooltip().get(0).toString());
         }
     }

     public void searchboxUpdated(String full){
        System.out.println("Fildered itemset down to " + full);
        filteredItemSet = new ArrayList<>(BlockValueGenerator.getInitializeMap().keySet().stream().filter(item -> item.toString().contains(full)).toList()); //blegh

        pos = 0;

        listed[0].rebuildOnCycle(filteredItemSet.size() > 0 ? filteredItemSet.get(pos) : Items.AIR); //mediocre solution to overfilter
        listed[1].rebuildOnCycle(filteredItemSet.size() > 1 ? filteredItemSet.get(pos+1) : Items.AIR);
        listed[2].rebuildOnCycle(filteredItemSet.size() > 2 ? filteredItemSet.get(pos+2) : Items.AIR);
     }

     public void selectEntry(int which){
        selected = listed[which].getEntryItem().getDefaultInstance();
     }



    //TODO - export to static method
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    //TODO - inconsistent reaction to backspace, E is not captured and still closes menu
    @Override
    public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {

        if(searchbarSelected){
            String s = this.searchBox.getValue();
            if (this.searchBox.keyPressed(p_97765_, p_97766_, p_97767_)) {
                if (!Objects.equals(s, this.searchBox.getValue())) {
                    this.searchboxUpdated(searchBox.getValue());
                }
                return true;
            }
        }

        return super.keyPressed(p_97765_, p_97766_, p_97767_);
    }

    @Override
    public boolean charTyped(char p_94683_, int p_94684_) {

        if(searchbarSelected) {
            String s = this.searchBox.getValue();
            if (this.searchBox.charTyped(p_94683_, p_94684_)) {
                if (!Objects.equals(s, this.searchBox.getValue())) {
                    this.searchboxUpdated(this.searchBox.getValue());
                }

                return true;
            } else {
                return false;
            }
        }

        return super.charTyped(p_94683_, p_94684_);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (this.searchBox != null) {
            this.searchBox.tick();
        }
    }
}
