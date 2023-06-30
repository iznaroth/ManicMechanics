package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import com.iznaroth.manicmechanics.menu.ImporterBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.ButtonCycleC2SPacket;
import com.iznaroth.manicmechanics.networking.packet.PayloadButtonC2SPacket;
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
    private ResourceLocation GUI = new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/importer_upscale.png");

    private MarketEntry[] listed = new MarketEntry[7];

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
        this.imageWidth = 301;

        filteredItemSet = new ArrayList<>(BlockValueGenerator.getInitializeMap().keySet());
    }

    @Override
    protected void init() {
        super.init();

        //this.imageHeight = 512;
        //this.imageWidth = 512;

        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        System.out.println(relX + " " + relY);
        Iterator<Item> itr = filteredItemSet.iterator();

        for(int i = 0; i < 7; i++){
            listed[i] = new MarketEntry(itr.next(), i, relX, relY);
        }

        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.searchBox = new EditBox(this.font, this.leftPos + 30, this.topPos + 18, 69, 8, Component.translatable("itemGroup.search"));
        this.searchBox.setMaxLength(50);
        this.searchBox.setBordered(false);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);
        this.addWidget(this.searchBox);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        //super.renderLabels(pPoseStack, pMouseX, pMouseX);
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderMarketEntryTooltips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderMarketEntryTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 30, 31, 16, 16)) {
            renderTooltip(pPoseStack, listed[0].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 30, 49, 16, 16)) {
            renderTooltip(pPoseStack, listed[1].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 30, 67, 16, 16)) {
            renderTooltip(pPoseStack, listed[2].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 30, 85, 16, 16)) {
            renderTooltip(pPoseStack, listed[3].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 30, 103, 16, 16)) {
            renderTooltip(pPoseStack, listed[4].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 30, 121, 16, 16)) {
            renderTooltip(pPoseStack, listed[5].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 30, 139, 16, 16)) {
            renderTooltip(pPoseStack, listed[6].getTooltip(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        } else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 133, 10, 16, 16)){
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
        this.blit(poseStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight, 512, 256);

        for(MarketEntry entry : listed){
            if(entry != null)
                entry.draw(poseStack);
        }

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.renderGuiItem(selected, relX + 133, relY + 10);

        Minecraft.getInstance().font.drawShadow(poseStack, Component.literal(" " + order_quantity + " "), relX + 136, relY + 33, 0xFFFFFF);
        Minecraft.getInstance().font.drawShadow(poseStack, Component.literal(" " + tick_delay + " "), relX + 136, relY + 49, 0xFFFFFF);
        //Minecraft.getInstance().font.drawShadow(poseStack, Component.literal(" " + BlockValueGenerator.current_mapping.get(selected.getItem()) * order_quantity + " "), relX, relY + 28, 0xFF0000);

        this.searchBox.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        System.out.println("CLICKED AT " + p_97748_ + " " + p_97749_);
        if((p_97748_ > x+131 && p_97748_ < x+137) && (p_97749_ > y+34 && p_97749_ < y+40)){ //Set 1 - Increase quantity of import
            order_quantity--;
            if(order_quantity < 0){
                order_quantity = 64;
            }
        }
        if((p_97748_ > x+154 && p_97748_ < x+160) && (p_97749_ > y+34 && p_97749_ < y+40)){ //
            order_quantity++;
            if(order_quantity > 64){
                order_quantity = 0;
            }
        }

        if((p_97748_ > x+154 && p_97748_ < x+158) && (p_97749_ > y+51 && p_97749_ < y+57)){ //Set 2 - Increase delay on auto import
            tick_delay--;
            if(tick_delay < 0){
                tick_delay = 0;
            }
        }
        if((p_97748_ > x+164 && p_97748_ < x+170) && (p_97749_ > y+51 && p_97749_ < y+57)){ //
            tick_delay++; //TODO - wrap func, shift-click to x8
        }

        if((p_97748_ > x+30 && p_97748_ < x+117) && (p_97749_ > y+31 && p_97749_ < y+48)){ //Inside the scrolling market viewer - three entries
            selectEntry(0);
        }
        if((p_97748_ > x+30 && p_97748_ < x+117) && (p_97749_ > y+49 && p_97749_ < y+66)){ //
            selectEntry(1);
        }
        if((p_97748_ > x+30 && p_97748_ < x+117) && (p_97749_ > y+67 && p_97749_ < y+84)){ //
            selectEntry(2);
        }
        if((p_97748_ > x+30 && p_97748_ < x+117) && (p_97749_ > y+85 && p_97749_ < y+102)){
            selectEntry(3);
        }
        if((p_97748_ > x+30 && p_97748_ < x+117) && (p_97749_ > y+103 && p_97749_ < y+120)){ //
            selectEntry(4);
        }
        if((p_97748_ > x+30 && p_97748_ < x+117) && (p_97749_ > y+121 && p_97749_ < y+138)){ //
            selectEntry(5);
        }
        if((p_97748_ > x+30 && p_97748_ < x+117) && (p_97749_ > y+139 && p_97749_ < y+156)){ //
            selectEntry(6);
        }



        if((p_97748_ > x+119 && p_97748_ < x+123) && (p_97749_ > y+31 && p_97749_ < y+35)){ //Scroll up/down buttons
            this.scrollEntriesUp();
        }
        if((p_97748_ > x+119 && p_97748_ < x+123) && (p_97749_ > y+151 && p_97749_ < y+155)){ //
            this.scrollEntriesDown();
        }

        if((p_97748_ > x+51 && p_97748_ < x+79) && (p_97749_ > y+67 && p_97749_ < y+79)){ //click in scrollbar

        }

        if((p_97748_ > x+30 && p_97748_ < x+106) && (p_97749_ > y+18 && p_97749_ < y+25)){ //click in searchbar
            searchbarSelected = true;
            this.searchBox.setFocus(true);
        } else { //any other click unfocus
            searchbarSelected = false;
            this.searchBox.setFocus(false);
        }

        if((p_97748_ > x+198 && p_97748_ < x+235) && (p_97749_ > y+39 && p_97749_ < y+52)){ //ORDER
            ArrayList<Item> col = new ArrayList<>(ForgeRegistries.ITEMS.getValues());

            MMMessages.sendToServer(new PayloadButtonC2SPacket(0, (col.indexOf(selected.getItem()) * 100 + order_quantity), this.menu.getBlockEntity().getBlockPos()));

            //this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
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

        listed[6].rebuildOnCycle(filteredItemSet.get(pos+6));
        listed[5].rebuildOnCycle(filteredItemSet.get(pos+5));
        listed[4].rebuildOnCycle(filteredItemSet.get(pos+4));
        listed[3].rebuildOnCycle(filteredItemSet.get(pos+3));
        listed[2].rebuildOnCycle(filteredItemSet.get(pos+2));
        listed[1].rebuildOnCycle(filteredItemSet.get(pos+1));
        listed[0].rebuildOnCycle(filteredItemSet.get(pos));
    }

     public void scrollEntriesDown(){
        pos++;
         if(pos+6 > filteredItemSet.size()-1){
             pos--;
             return;
         }

         listed[0].rebuildOnCycle(filteredItemSet.get(pos));
         listed[1].rebuildOnCycle(filteredItemSet.get(pos + 1));
         listed[2].rebuildOnCycle(filteredItemSet.get(pos + 2));
         listed[3].rebuildOnCycle(filteredItemSet.get(pos + 3));
         listed[4].rebuildOnCycle(filteredItemSet.get(pos + 4));
         listed[5].rebuildOnCycle(filteredItemSet.get(pos + 5));
         listed[6].rebuildOnCycle(filteredItemSet.get(pos + 6));

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
        listed[3].rebuildOnCycle(filteredItemSet.size() > 1 ? filteredItemSet.get(pos+1) : Items.AIR);
        listed[4].rebuildOnCycle(filteredItemSet.size() > 2 ? filteredItemSet.get(pos+2) : Items.AIR);
        listed[5].rebuildOnCycle(filteredItemSet.size() > 1 ? filteredItemSet.get(pos+1) : Items.AIR);
        listed[6].rebuildOnCycle(filteredItemSet.size() > 2 ? filteredItemSet.get(pos+2) : Items.AIR);


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
            return true;//Testing capture
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

    @Override
    public int getXSize(){
        return 512;
    }

    @Override
    public int getYSize(){
        return 512;
    }
}
