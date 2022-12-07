package com.iznaroth.manicmechanics.screen;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.menu.CommunicatorBlockMenu;
import com.iznaroth.manicmechanics.util.CommunicatorInfoGroup;
import com.iznaroth.manicmechanics.util.EmailHandler;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.world.item.ItemStack;


import java.util.*;
import java.util.function.Predicate;

public class CommunicatorBlockScreen extends AbstractContainerScreen<CommunicatorBlockMenu> {

    public static final SimpleContainer CONTAINER = new SimpleContainer(45);
    private static int selectedTab = CommunicatorInfoGroup.TAB_EMAIL.getId();
    private float scrollOffs;
    private boolean scrolling;
    private EditBox searchBox;

    //@Nullable
    //private List<Slot> originalSlots;

    private CreativeInventoryListener listener;

    private boolean ignoreTextInput;
    private static int tabPage = 0;
    private int maxPages = 0;
    private boolean hasClickedOutside;
    private final Set<TagKey<Item>> visibleTags = new HashSet<>();

    //MY STUFF
    private boolean email_expand;
    private int open_mail = -1;

    private boolean wiki_expand;
    private int open_wiki = -1;

    public CommunicatorBlockScreen(CommunicatorBlockMenu container, Inventory inv, Component name)
    {
        super(container, inv, name);
        this.passEvents = true;
        this.imageHeight = 136;
        this.imageWidth = 195;
    }

    public void containerTick() {
        super.containerTick();
        if (!this.minecraft.gameMode.hasInfiniteItems()) {
            this.minecraft.setScreen(new InventoryScreen(this.minecraft.player));
        } else if (this.searchBox != null) {
            this.searchBox.tick();
        }

    }

    //THE FOLLOWING COMMENTS ARE IDEALLY A TODO LIST TO GET THIS THING WORKING

    protected void init() {
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            super.init();
            int tabCount = CommunicatorInfoGroup.TABS.length;  //TODO - LINK TO COMMUNICATOR'S TAB HANDLER - where should that go?
            if (tabCount > 12) { //This loop ensures that extra tabs are appended onto subsequent pages. May not be necessary. Keep for now.
                addRenderableWidget(new net.minecraft.client.gui.components.Button(leftPos,              topPos - 50, 20, 20, Component.literal("<"), b -> tabPage = Math.max(tabPage - 1, 0       )));
                addRenderableWidget(new net.minecraft.client.gui.components.Button(leftPos + imageWidth - 20, topPos - 50, 20, 20, Component.literal(">"), b -> tabPage = Math.min(tabPage + 1, maxPages)));
                maxPages = (int) Math.ceil((tabCount - 12) / 10D);
            }
            this.minecraft.keyboardHandler.setSendRepeatsToGui(true); //Searchbox setup.
            this.searchBox = new EditBox(this.font, this.leftPos + 82, this.topPos + 6, 80, 9, Component.translatable("itemGroup.search"));
            this.searchBox.setMaxLength(50);
            this.searchBox.setBordered(false);
            this.searchBox.setVisible(false);
            this.searchBox.setTextColor(16777215);
            this.addWidget(this.searchBox); //EventListener hooks for search responses, I assume.


            int i = selectedTab; //TODO - Change this to communicator's tab handler.
            selectedTab = -1;
            this.selectTab(CommunicatorInfoGroup.TABS[i]);


           /* this.minecraft.player.inventoryMenu.removeSlotListener(this.listener); //Normally, creative requires two listeners, so it has to do this?
            this.listener = new CreativeCraftingListener(this.minecraft);
            this.minecraft.player.inventoryMenu.addSlotListener(this.listener);
        } else {
            this.minecraft.setScreen(new InventoryScreen(this.minecraft.player)); This bit would probably just set it back to normal. */
        }

    }

    public void resize(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) { //Seems necessary for search.
        String s = this.searchBox.getValue();
        this.init(p_231152_1_, p_231152_2_, p_231152_3_);
        this.searchBox.setValue(s);
        if (!this.searchBox.getValue().isEmpty()) {
            this.refreshSearchResults();
        }

    }

    public void removed() { //Not sure. TODO: Try removing once it works and see what happens?
        super.removed();
        if (this.minecraft.player != null && this.minecraft.player.getInventory() != null) {
            this.minecraft.player.inventoryMenu.removeSlotListener(this.listener);
        }

        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    public boolean charTyped(char p_231042_1_, int p_231042_2_) { //Searchbar stuff. TODO - Consider how searchbar is gonna change based on wiki/email search stuff.
        if (this.ignoreTextInput) {
            return false;
        } else if (!CommunicatorInfoGroup.TABS[selectedTab].hasSearchBar()) {
            return false;
        } else {
            String s = this.searchBox.getValue();
            if (this.searchBox.charTyped(p_231042_1_, p_231042_2_)) {
                if (!Objects.equals(s, this.searchBox.getValue())) {
                    this.refreshSearchResults();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) { //THIS FUNCTION HAS BEEN MODIFIED - Removed "switch tab focus on non-search keypress"
        this.ignoreTextInput = false;

        boolean flag;
        if(this.hoveredSlot != null){
            flag = this.hoveredSlot.hasItem(); //REMOVED: !this.isCreativeSlot(this.hoveredSlot) ||
        } else {
            flag = false;
        };


        boolean flag1 = InputConstants.getKey(p_231046_1_, p_231046_2_).getNumericKeyValue().isPresent();
        if (flag && flag1 && this.checkHotbarKeyPressed(p_231046_1_, p_231046_2_)) {
            this.ignoreTextInput = true;
            return true;
        } else {
            String s = this.searchBox.getValue();
            if (this.searchBox.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
                if (!Objects.equals(s, this.searchBox.getValue())) {
                    this.refreshSearchResults();
                }

                return true;
            } else {
                return this.searchBox.isFocused() && this.searchBox.isVisible() && p_231046_1_ != 256 ? true : super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
            }

        }
    }

    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) { //Stuff for doublepress prevention
        this.ignoreTextInput = false;
        return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
    }

    private void refreshSearchResults() { //TODO - Basically this entire function needs to be rewritten once the tabs are set-up. Look at CreativeScreen's implementation for reference.
        (this.menu).items.clear();
        this.visibleTags.clear();

        CommunicatorInfoGroup tab = CommunicatorInfoGroup.TABS[selectedTab];
        if (tab.hasSearchBar()) {
            //tab.fillItemList(menu.items);     We will use this later to populate Emails, Wiki Entries and Available Contracts.
            if (!this.searchBox.getValue().isEmpty()) {
                System.out.println("We'll be searching eventually!");
                //This is where search logic goes. It'll have to be split into separate functions based on the active tab.
            }
            this.scrollOffs = 0.0F;
            menu.scrollTo(0.0F);
            return;
        }


        this.scrollOffs = 0.0F;
        this.menu.scrollTo(0.0F);
    }

    private void updateVisibleTags(String p_214080_1_) { //Literally no clue. Not sure what a visible tag is. Appears to be involved in search, so we'll prolly figure out the problem there.
        int i = p_214080_1_.indexOf(58);
        Predicate<ResourceLocation> predicate;
        if (i == -1) {
            predicate = (p_214084_1_) -> {
                return p_214084_1_.getPath().contains(p_214080_1_);
            };
        } else {
            String s = p_214080_1_.substring(0, i).trim();
            String s1 = p_214080_1_.substring(i + 1).trim();
            predicate = (p_214081_2_) -> {
                return p_214081_2_.getNamespace().contains(s) && p_214081_2_.getPath().contains(s1);
            };
        }

        Registry.ITEM.getTagNames().filter((p_205410_) -> {
            return predicate.test(p_205410_.location());
        }).forEach(this.visibleTags::add);
    }

    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) { //Hovertext(?) labels for tabs. Keep!
        CommunicatorInfoGroup comgroup = CommunicatorInfoGroup.TABS[selectedTab];
        if (comgroup != null && comgroup.showTitle()) {
            RenderSystem.disableBlend();
            this.font.draw(p_230451_1_, comgroup.getDisplayName(), 8.0F, 6.0F, comgroup.getLabelColor());
        }

    }

    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) { //Event fire for clicking basically anything. If the click-coordinate matches a tab, it calls that tab's function.
        if (p_231044_5_ == 0) {
            double d0 = p_231044_1_ - (double)this.leftPos;
            double d1 = p_231044_3_ - (double)this.topPos;

            for(CommunicatorInfoGroup comgroup : CommunicatorInfoGroup.TABS) {
                if (comgroup != null && this.checkTabClicked(comgroup, d0, d1)) { //Remember that this is just a handler that needs to fire a success if it did something with the click.
                    return true;                                                    //The logic for tab-switching will probably happen in checkTabClicked, or set a flag for selectedTab.
                }
            }

            if(CommunicatorInfoGroup.TABS[selectedTab] == CommunicatorInfoGroup.TAB_EMAIL){
                for(int i = 0; i < 5; i++){
                    if(d0 > 8.0D && d0 < 168.0D && d1 > 18.0D + (i*18.0D) && d1 < 18.0D + ((i+1)*18.0D)){ //This horrific block SHOULD check in 18x160px increments across the five possible email slots.
                        return true;
                    }
                }
            }

            if (this.insideScrollbar(p_231044_1_, p_231044_3_)) { //After prior statement, a selected tab is operated on.
                this.scrolling = this.canScroll();                //In this case, activating the scrollbar if necessary. Removed a conditional related to the Inventory tab.
                return true;
            }
        }

        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_); //if you got here, it's a click that isn't otherwise relevant to this particular screen.
    }

    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) { //This is specifically for mouse-hold scrolling & waiting to switch tabs until release.
        if (p_231048_5_ == 0) {
            double d0 = p_231048_1_ - (double)this.leftPos;
            double d1 = p_231048_3_ - (double)this.topPos;
            this.scrolling = false;

            for(CommunicatorInfoGroup comgroup: CommunicatorInfoGroup.TABS) {
                if (comgroup!= null && this.checkTabClicked(comgroup, d0, d1)) {
                    this.selectTab(comgroup);
                    return true;
                }
            }

            if(CommunicatorInfoGroup.TABS[selectedTab] == CommunicatorInfoGroup.TAB_EMAIL){
                for(int i = 0; i < 5; i++){
                    if(d0 > 8.0D && d0 < 168.0D && d1 > 18.0D + (i*18.0D) && d1 < 18.0D + ((i+1)*18.0D)){ //This horrific block SHOULD check in 18x160px increments across the five possible email slots.
                        email_expand = openEmail(i);
                        return true;
                    }
                }
            }

        }

        return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
    }

    private boolean openEmail(int to_open){
        System.out.println(to_open);
        System.out.println(EmailHandler.isSlotEmpty(to_open));
        System.out.println(EmailHandler.active_entries[0]);

        if(EmailHandler.isSlotEmpty(to_open)){
            return false;
        } else {
            open_mail = to_open;
            return true;
        }
    }

    private boolean canScroll() { //TODO - Keep, change tab identifiers.
        if (CommunicatorInfoGroup.TABS[selectedTab] == null) return false;
        return CommunicatorInfoGroup.TABS[selectedTab].canScroll() && this.menu.canScroll(); //Removed the Inventory flag for scroll capability.
    }

    private void selectTab(CommunicatorInfoGroup p_147050_1_) { //TODO - Obviously needs a deep rework.
        System.out.println("SHOULD NOT FIRE ON EMAIL RESET!");

        if (p_147050_1_ == null) return;
        int i = selectedTab;
        selectedTab = p_147050_1_.getId();
        slotColor = p_147050_1_.getSlotColor();
        this.quickCraftSlots.clear();
        (this.menu).items.clear();

        /*
         //NOTE - This just populates a tab with all items. Maybe keep it for the Wiki?
        if (p_147050_1_ != CommunicatorInfoGroup.TAB_SEARCH) {
            p_147050_1_.fillItemList((this.menu).items);
        }*/

        if (this.searchBox != null) { //This bit should be good as-is. Might need tweaking.
            if (p_147050_1_.hasSearchBar()) {
                this.searchBox.setVisible(true);
                this.searchBox.setCanLoseFocus(false);
                this.searchBox.setFocus(true);
                if (i != p_147050_1_.getId()) {
                    this.searchBox.setValue("");
                }
                this.searchBox.setWidth(p_147050_1_.getSearchbarWidth());
                this.searchBox.x = this.leftPos + (82 /*default left*/ + 89 /*default width*/) - this.searchBox.getWidth();

                this.refreshSearchResults();
            } else {
                this.searchBox.setVisible(false);
                this.searchBox.setCanLoseFocus(true);
                this.searchBox.setFocus(false);
                this.searchBox.setValue("");
            }
        }

        this.scrollOffs = 0.0F;
        this.menu.scrollTo(0.0F);
    }

    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) { //Don't change anything.
        if (!this.canScroll()) {
            return false;
        } else {
            int i = ((this.menu).items.size() + 9 - 1) / 9 - 5;
            this.scrollOffs = (float)((double)this.scrollOffs - p_231043_5_ / (double)i);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.menu.scrollTo(this.scrollOffs);
            return true;
        }
    }

    protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) { //Unfocus from searchbar.
        boolean flag = p_195361_1_ < (double)p_195361_5_ || p_195361_3_ < (double)p_195361_6_ || p_195361_1_ >= (double)(p_195361_5_ + this.imageWidth) || p_195361_3_ >= (double)(p_195361_6_ + this.imageHeight);
        this.hasClickedOutside = flag && !this.checkTabClicked(CommunicatorInfoGroup.TABS[selectedTab], p_195361_1_, p_195361_3_);
        return this.hasClickedOutside;
    }

    protected boolean insideScrollbar(double p_195376_1_, double p_195376_3_) { //Is probably called per-tick to see if the cursor can grab the scrollbar.
        int i = this.leftPos;
        int j = this.topPos;
        int k = i + 175;
        int l = j + 18;
        int i1 = k + 14;
        int j1 = l + 112;
        return p_195376_1_ >= (double)k && p_195376_3_ >= (double)l && p_195376_1_ < (double)i1 && p_195376_3_ < (double)j1;
    }

    public boolean mouseDragged(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) { //Mouse moved while click is held?
        if (this.scrolling) {
            int i = this.topPos + 18;
            int j = i + 112;
            this.scrollOffs = ((float)p_231045_3_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.menu.scrollTo(this.scrollOffs);
            return true;
        } else {
            return super.mouseDragged(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
        }
    }

    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) { //TODO - Some cleanup. Not everything here needs to be rendered.
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);

        int start = tabPage * 10;
        int end = Math.min(CommunicatorInfoGroup.TABS.length, ((tabPage + 1) * 10) + 2);
        if (tabPage != 0) start += 2;
        boolean rendered = false;

        for (int x = start; x < end; x++) {
            CommunicatorInfoGroup comgroup = CommunicatorInfoGroup.TABS[x];
            if (comgroup != null && this.checkTabHovering(p_230430_1_, comgroup, p_230430_2_, p_230430_3_)) {
                rendered = true;
                break;
            }
        }
        //if (!rendered && !this.checkTabHovering(p_230430_1_, CommunicatorInfoGroup.TAB_SEARCH, p_230430_2_, p_230430_3_))
        //    this.checkTabHovering(p_230430_1_, CommunicatorInfoGroup.TAB_INVENTORY, p_230430_2_, p_230430_3_);

        //if (this.destroyItemSlot != null && selectedTab == CommunicatorInfoGroup.TAB_INVENTORY.getId() && this.isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, (double)p_230430_2_, (double)p_230430_3_)) {
        //    this.renderTooltip(p_230430_1_, TRASH_SLOT_TOOLTIP, p_230430_2_, p_230430_3_);
        //}

        if (maxPages != 0) {
            Component page = Component.literal(String.format("%d / %d", tabPage + 1, maxPages + 1));
            this.setBlitOffset(300);
            this.itemRenderer.blitOffset = 300.0F;
            font.drawShadow(p_230430_1_, page.getVisualOrderText(), leftPos + (imageWidth / 2) - (font.width(page) / 2), topPos - 44, -1);
            this.setBlitOffset(0);
            this.itemRenderer.blitOffset = 0.0F;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    protected void renderTooltip(PoseStack p_230457_1_, ItemStack p_230457_2_, int p_230457_3_, int p_230457_4_) { //TODO - Probably needs a full rewrite.

        //NOTE: Original implementation was for unique tooltips in the search bar. Can possibly be deleted?
        super.renderTooltip(p_230457_1_, p_230457_2_, p_230457_3_, p_230457_4_);

        //}

    }

    protected void renderBg(PoseStack ms, float p_230450_2_, int p_230450_3_, int p_230450_4_) { //TODO - mimick the structure, but each Communicator tab is a little different.
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        CommunicatorInfoGroup comgroup = CommunicatorInfoGroup.TABS[selectedTab];

        int start = tabPage * 10;
        int end = Math.min(CommunicatorInfoGroup.TABS.length, ((tabPage + 1) * 10 + 2));
        if (tabPage != 0) start += 2;

        for (int idx = start; idx < end; idx++) {
            CommunicatorInfoGroup comgroup1 = CommunicatorInfoGroup.TABS[idx];
            if (comgroup1 != null && comgroup1.getId() != selectedTab) {
                this.minecraft.getTextureManager().bindForSetup(comgroup1.getTabsImage());
                this.renderTabButton(ms, comgroup1);
            }
        }

        this.minecraft.getTextureManager().bindForSetup(comgroup.getBackgroundImage());
        this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.searchBox.render(ms, p_230450_3_, p_230450_4_, p_230450_2_);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.leftPos + 175;
        int j = this.topPos + 18;
        int k = j + 112;
        this.minecraft.getTextureManager().bindForSetup(comgroup.getTabsImage());

        if (comgroup.canScroll()) { //BLIT FOR SCROLLBAR
            this.blit(ms, i, j + (int)((float)(k - j - 17) * this.scrollOffs), 232 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        }

        //NOTE - THIS NEEDS TO BE PLACED SOMEWHERE THAT THE COMGROUP TAB IMAGE IS BOUND TO THE TEXTURE MANAGER. MOVE WITH CAUTION!
        if ((comgroup == null || comgroup.getTabPage() != tabPage))// && (comgroup != CommunicatorInfoGroup.TAB_SEARCH && comgroup != CommunicatorInfoGroup.TAB_INVENTORY))
            return;

        this.renderTabButton(ms, comgroup);
        //ENDNOTE

        if(comgroup == CommunicatorInfoGroup.TAB_EMAIL && !email_expand){
            open_mail = 0;
            for(int l = 0; l < 5; l++){

                this.minecraft.getTextureManager().bindForSetup(comgroup.getTabsImage());
                this.blit(ms, leftPos + 9, topPos + 17 + ((l) * 18), 0, 139 + (EmailHandler.isSlotEmpty(open_mail) ? 18 : 0), 160, 18);

                if(!EmailHandler.isSlotEmpty(l)){ //Draw subjects & mail icon.
                    this.minecraft.getTextureManager().bindForSetup(new ResourceLocation(ManicMechanics.MOD_ID, "textures/gui/mail_icon.png"));
                    blit(ms, leftPos + 12, topPos + 18 + (l * 18), 0, 0, 16, 16, 16, 16);
                    this.minecraft.font.drawShadow(ms, EmailHandler.active_entries[l].subject, leftPos + (float) 30, topPos + (float) 22 + (l * 18), 16777215);
                }
                open_mail++;
            }
        } else if(comgroup == CommunicatorInfoGroup.TAB_EMAIL && email_expand){

            //TODO - DO NOT DO FRIGGIN' STRING FORMATTING IN YOUR RENDER LOOP MORON!
            Component tc = EmailHandler.active_entries[open_mail].body;

            int tx_width = 160;
            int tx_height = 88;

            ArrayList<String> split_list = new ArrayList<String>();
            String latest = tc.getString();

            while(this.minecraft.font.width(latest) > tx_width){
                int idx = this.minecraft.font.getSplitter().plainIndexAtWidth(latest, tx_width, tc.getStyle());

                split_list.add(latest.substring(0, idx).trim());
                latest = latest.substring(idx);
                //System.out.println("Sure do hope this don't loop infinitely!");
            }

            split_list.add(latest);

            for(int inc = 0; inc < 9; inc++) { //THE MAX LINES FOR GUI HEIGHT IS CURRENTLY 9. SCROLLING IS NOT IMPLEMENTED.
                this.minecraft.font.drawShadow(ms, split_list.get(inc), leftPos + (float) 10, topPos + (float) 20 + (inc * this.minecraft.font.lineHeight), 16777215);
                //System.out.println(split_list.get(inc));
            }
        }



    }

    protected boolean checkTabClicked(CommunicatorInfoGroup p_195375_1_, double p_195375_2_, double p_195375_4_) {
        if (p_195375_1_.getTabPage() != tabPage) return false; //&& p_195375_1_ != CommunicatorInfoGroup.TAB_SEARCH && p_195375_1_ != CommunicatorInfoGroup.TAB_INVENTORY) return false;
        int i = p_195375_1_.getColumn();
        int j = 28 * i;
        int k = 0;
        if (p_195375_1_.isAlignedRight()) {
            j = this.imageWidth - 28 * (6 - i) + 2;
        } else if (i > 0) {
            j += i;
        }

        if (p_195375_1_.isTopRow()) {
            k = k - 32;
        } else {
            k = k + this.imageHeight;
        }

        return p_195375_2_ >= (double)j && p_195375_2_ <= (double)(j + 28) && p_195375_4_ >= (double)k && p_195375_4_ <= (double)(k + 32);
    }

    protected boolean checkTabHovering(PoseStack p_238809_1_, CommunicatorInfoGroup p_238809_2_, int p_238809_3_, int p_238809_4_) {
        int i = p_238809_2_.getColumn();
        int j = 28 * i;
        int k = 0;
        if (p_238809_2_.isAlignedRight()) {
            j = this.imageWidth - 28 * (6 - i) + 2;
        } else if (i > 0) {
            j += i;
        }

        if (p_238809_2_.isTopRow()) {
            k = k - 32;
        } else {
            k = k + this.imageHeight;
        }

        if (this.isHovering(j + 3, k + 3, 23, 27, (double)p_238809_3_, (double)p_238809_4_)) {
            this.renderTooltip(p_238809_1_, p_238809_2_.getDisplayName(), p_238809_3_, p_238809_4_);
            return true;
        } else {
            return false;
        }
    }

    protected void renderTabButton(PoseStack p_238808_1_, CommunicatorInfoGroup p_238808_2_) {
        boolean flag = p_238808_2_.getId() == selectedTab;
        boolean flag1 = p_238808_2_.isTopRow();
        int i = p_238808_2_.getColumn();
        int j = i * 28;
        int k = 0;
        int l = this.leftPos + 28 * i;
        int i1 = this.topPos;
        int j1 = 32;
        if (flag) {
            k += 32;
        }

        if (p_238808_2_.isAlignedRight()) {
            l = this.leftPos + this.imageWidth - 28 * (6 - i);
        } else if (i > 0) {
            l += i;
        }

        if (flag1) {
            i1 = i1 - 28;
        } else {
            k += 64;
            i1 = i1 + (this.imageHeight - 4);
        }

        RenderSystem.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
        this.blit(p_238808_1_, l, i1, j, k, 28, 32);
        this.itemRenderer.blitOffset = 100.0F;
        l = l + 6;
        i1 = i1 + 8 + (flag1 ? 1 : -1);
        ItemStack itemstack = p_238808_2_.getIconItem();
        this.itemRenderer.renderAndDecorateItem(itemstack, l, i1);
        this.itemRenderer.renderGuiItemDecorations(this.font, itemstack, l, i1);
        this.itemRenderer.blitOffset = 0.0F;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

}
