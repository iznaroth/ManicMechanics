package com.iznaroth.industrizer.screen;


import com.google.common.collect.Maps;
import com.iznaroth.industrizer.container.CommunicatorBlockContainer;
import com.iznaroth.industrizer.util.CommunicatorInfoGroup;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeCraftingListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class CommunicatorBlockScreen extends ContainerScreen<CommunicatorBlockContainer> {

    public static final Inventory CONTAINER = new Inventory(45);
    private static int selectedTab = CommunicatorInfoGroup.TAB_EMAIL.getId();
    private float scrollOffs;
    private boolean scrolling;
    private TextFieldWidget searchBox;

    //@Nullable
    //private List<Slot> originalSlots;

    private CreativeCraftingListener listener;

    private boolean ignoreTextInput;
    private static int tabPage = 0;
    private int maxPages = 0;
    private boolean hasClickedOutside;
    private final Map<ResourceLocation, ITag<Item>> visibleTags = Maps.newTreeMap();

    public CommunicatorBlockScreen(CommunicatorBlockContainer container, PlayerInventory inv, ITextComponent name)
    {
        super(container, inv, name);
        this.passEvents = true;
        this.imageHeight = 136;
        this.imageWidth = 195;
    }

    public void tick() { //Required to update on keyboard input. The commented portion would replace the existing inventory with this screen, which is not intended.
        //if (!this.minecraft.gameMode.hasInfiniteItems()) {
        //    this.minecraft.setScreen(new InventoryScreen(this.minecraft.player));
        //} else

        if (this.searchBox != null) {
            this.searchBox.tick();
        }

    }

    //THE FOLLOWING COMMENTS ARE IDEALLY A TODO LIST TO GET THIS THING WORKING

    protected void init() {
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            super.init();
            int tabCount = CommunicatorInfoGroup.TABS.length;  //TODO - LINK TO COMMUNICATOR'S TAB HANDLER - where should that go?
            if (tabCount > 12) { //This loop ensures that extra tabs are appended onto subsequent pages. May not be necessary. Keep for now.
                addButton(new net.minecraft.client.gui.widget.button.Button(leftPos,              topPos - 50, 20, 20, new StringTextComponent("<"), b -> tabPage = Math.max(tabPage - 1, 0       )));
                addButton(new net.minecraft.client.gui.widget.button.Button(leftPos + imageWidth - 20, topPos - 50, 20, 20, new StringTextComponent(">"), b -> tabPage = Math.min(tabPage + 1, maxPages)));
                maxPages = (int) Math.ceil((tabCount - 12) / 10D);
            }
            this.minecraft.keyboardHandler.setSendRepeatsToGui(true); //Searchbox setup.
            this.searchBox = new TextFieldWidget(this.font, this.leftPos + 82, this.topPos + 6, 80, 9, new TranslationTextComponent("itemGroup.search"));
            this.searchBox.setMaxLength(50);
            this.searchBox.setBordered(false);
            this.searchBox.setVisible(false);
            this.searchBox.setTextColor(16777215);
            this.children.add(this.searchBox); //EventListener hooks for search responses, I assume.


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
        if (this.minecraft.player != null && this.minecraft.player.inventory != null) {
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


        boolean flag1 = InputMappings.getKey(p_231046_1_, p_231046_2_).getNumericKeyValue().isPresent();
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

    private void refreshSearchResults() { //TODO - Basically this entire function needs to be rewritten once the tabs are set-up.
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

        //This appears to be logic for populating creative search queries, so it's commented, but I'm not 100% sure it's unnecessary, so it stays.
        /*
        String s = this.searchBox.getValue();
        if (s.isEmpty()) {
            for(Item item : Registry.ITEM) {
                item.fillItemCategory(CommunicatorInfoGroup.TAB_SEARCH, (this.menu).items);
            }
        } else {
            ISearchTree<ItemStack> isearchtree;
            if (s.startsWith("#")) {
                s = s.substring(1);
                isearchtree = this.minecraft.getSearchTree(SearchTreeManager.CREATIVE_TAGS);
                this.updateVisibleTags(s);
            } else {
                isearchtree = this.minecraft.getSearchTree(SearchTreeManager.CREATIVE_NAMES);
            }

            (this.menu).items.addAll(isearchtree.search(s.toLowerCase(Locale.ROOT)));
        }
        */


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

        ITagCollection<Item> itagcollection = ItemTags.getAllTags();
        itagcollection.getAvailableTags().stream().filter(predicate).forEach((p_214082_2_) -> {
            ITag itag = this.visibleTags.put(p_214082_2_, itagcollection.getTag(p_214082_2_));
        });
    }

    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) { //Hovertext(?) labels for tabs. Keep!
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
        }

        return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
    }

    private boolean canScroll() { //TODO - Keep, change tab identifiers.
        if (CommunicatorInfoGroup.TABS[selectedTab] == null) return false;
        return CommunicatorInfoGroup.TABS[selectedTab].canScroll() && this.menu.canScroll(); //Removed the Inventory flag for scroll capability.
    }

    private void selectTab(CommunicatorInfoGroup p_147050_1_) { //TODO - Obviously needs a deep rework.
        if (p_147050_1_ == null) return;
        int i = selectedTab;
        selectedTab = p_147050_1_.getId();
        slotColor = p_147050_1_.getSlotColor();
        this.quickCraftSlots.clear();
        (this.menu).items.clear();

        //NOTE - This segment relates to the Hotbar tab, and is awaiting deletion.
        /*
        if (p_147050_1_ == CommunicatorInfoGroup.TAB_HOTBAR) {
            CreativeSettings creativesettings = this.minecraft.getHotbarManager();

            for(int j = 0; j < 9; ++j) {
                HotbarSnapshot hotbarsnapshot = creativesettings.get(j);
                if (hotbarsnapshot.isEmpty()) {
                    for(int k = 0; k < 9; ++k) {
                        if (k == j) {
                            ItemStack itemstack = new ItemStack(Items.PAPER);
                            itemstack.getOrCreateTagElement("CustomCreativeLock");
                            ITextComponent itextcomponent = this.minecraft.options.keyHotbarSlots[j].getTranslatedKeyMessage();
                            ITextComponent itextcomponent1 = this.minecraft.options.keySaveHotbarActivator.getTranslatedKeyMessage();
                            itemstack.setHoverName(new TranslationTextComponent("inventory.hotbarInfo", itextcomponent1, itextcomponent));
                            (this.menu).items.add(itemstack);
                        } else {
                            (this.menu).items.add(ItemStack.EMPTY);
                        }
                    }
                } else {
                    (this.menu).items.addAll(hotbarsnapshot);
                }
            }


         //NOTE - This just populates a tab with all items. Maybe keep it for the Wiki?
        if (p_147050_1_ != CommunicatorInfoGroup.TAB_SEARCH) {
            p_147050_1_.fillItemList((this.menu).items);
        }

        //NOTE - This segment is for activating the INVENTORY tab of the creative menu. It should not be necessary. Preserved in case of error.

        if (p_147050_1_ == ItemGroup.TAB_INVENTORY) {
            Container container = this.minecraft.player.inventoryMenu;
            if (this.originalSlots == null) {
                this.originalSlots = ImmutableList.copyOf((this.menu).slots);
            }

            (this.menu).slots.clear();

            for(int l = 0; l < container.slots.size(); ++l) {
                int i1;
                int j1;
                if (l >= 5 && l < 9) {
                    int l1 = l - 5;
                    int j2 = l1 / 2;
                    int l2 = l1 % 2;
                    i1 = 54 + j2 * 54;
                    j1 = 6 + l2 * 27;
                } else if (l >= 0 && l < 5) {
                    i1 = -2000;
                    j1 = -2000;
                } else if (l == 45) {
                    i1 = 35;
                    j1 = 20;
                } else {
                    int k1 = l - 9;
                    int i2 = k1 % 9;
                    int k2 = k1 / 9;
                    i1 = 9 + i2 * 18;
                    if (l >= 36) {
                        j1 = 112;
                    } else {
                        j1 = 54 + k2 * 18;
                    }
                }

                Slot slot = new CreativeScreen.CreativeSlot(container.slots.get(l), l, i1, j1);
                (this.menu).slots.add(slot);
            }

            this.destroyItemSlot = new Slot(CONTAINER, 0, 173, 112);
            (this.menu).slots.add(this.destroyItemSlot);
        } else if (i == ItemGroup.TAB_INVENTORY.getId()) {
            (this.menu).slots.clear();
            (this.menu).slots.addAll(this.originalSlots);
            this.originalSlots = null;
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
            this.scrollOffs = MathHelper.clamp(this.scrollOffs, 0.0F, 1.0F);
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
            this.scrollOffs = MathHelper.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.menu.scrollTo(this.scrollOffs);
            return true;
        } else {
            return super.mouseDragged(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
        }
    }

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) { //TODO - Some cleanup. Not everything here needs to be rendered.
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
            ITextComponent page = new StringTextComponent(String.format("%d / %d", tabPage + 1, maxPages + 1));
            RenderSystem.disableLighting();
            this.setBlitOffset(300);
            this.itemRenderer.blitOffset = 300.0F;
            font.drawShadow(p_230430_1_, page.getVisualOrderText(), leftPos + (imageWidth / 2) - (font.width(page) / 2), topPos - 44, -1);
            this.setBlitOffset(0);
            this.itemRenderer.blitOffset = 0.0F;
        }

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    protected void renderTooltip(MatrixStack p_230457_1_, ItemStack p_230457_2_, int p_230457_3_, int p_230457_4_) { //TODO - Probably needs a full rewrite.

        //NOTE - I believe this is meant to show advanced info on the search tab, since certain tooltips/rendering utils aren't used on all tabs. Should be useless in our case unless those specific things come up.

        /*if (selectedTab == CommunicatorInfoGroup.TAB_SEARCH.getId()) {
            List<ITextComponent> list = p_230457_2_.getTooltipLines(this.minecraft.player, this.minecraft.options.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
            List<ITextComponent> list1 = Lists.newArrayList(list);
            Item item = p_230457_2_.getItem();
            CommunicatorInfoGroup comgroup = item.getItemCategory();
            if (comgroup== null && item == Items.ENCHANTED_BOOK) {
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(p_230457_2_);
                if (map.size() == 1) {
                    Enchantment enchantment = map.keySet().iterator().next();

                    for(CommunicatorInfoGroup comgroup1 : CommunicatorInfoGroup.TABS) {
                        if (comgroup1.hasEnchantmentCategory(enchantment.category)) {
                            comgroup= comgroup1;
                            break;
                        }
                    }
                }
            }

            this.visibleTags.forEach((p_214083_2_, p_214083_3_) -> {
                if (p_214083_3_.contains(item)) {
                    list1.add(1, (new StringTextComponent("#" + p_214083_2_)).withStyle(TextFormatting.DARK_PURPLE));
                }

            });
            if (comgroup!= null) {
                list1.add(1, comgroup.getDisplayName().copy().withStyle(TextFormatting.BLUE));
            }

            net.minecraft.client.gui.FontRenderer font = p_230457_2_.getItem().getFontRenderer(p_230457_2_);
            net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(p_230457_2_);
            this.renderWrappedToolTip(p_230457_1_, list1, p_230457_3_, p_230457_4_, (font == null ? this.font : font));
            net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
        } else { */

        super.renderTooltip(p_230457_1_, p_230457_2_, p_230457_3_, p_230457_4_);

        //}

    }

    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) { //TODO - mimick the structure, but each Communicator tab is a little different.
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        CommunicatorInfoGroup comgroup = CommunicatorInfoGroup.TABS[selectedTab];

        int start = tabPage * 10;
        int end = Math.min(CommunicatorInfoGroup.TABS.length, ((tabPage + 1) * 10 + 2));
        if (tabPage != 0) start += 2;

        for (int idx = start; idx < end; idx++) {
            CommunicatorInfoGroup comgroup1 = CommunicatorInfoGroup.TABS[idx];
            if (comgroup1 != null && comgroup1.getId() != selectedTab) {
                this.minecraft.getTextureManager().bind(comgroup1.getTabsImage());
                this.renderTabButton(p_230450_1_, comgroup1);
            }
        }

        /*if (tabPage != 0) { //This is a special block for rendering the SEARCH and INVENTORY tabs when scrolled to a new tab page. It is therefore unnecessary.
            if (comgroup != CommunicatorInfoGroup.TAB_SEARCH) {
                this.minecraft.getTextureManager().bind(CommunicatorInfoGroup.TAB_SEARCH.getTabsImage());
                renderTabButton(p_230450_1_, CommunicatorInfoGroup.TAB_SEARCH);
            }
            if (comgroup != CommunicatorInfoGroup.TAB_INVENTORY) {
                this.minecraft.getTextureManager().bind(CommunicatorInfoGroup.TAB_INVENTORY.getTabsImage());
                renderTabButton(p_230450_1_, CommunicatorInfoGroup.TAB_INVENTORY);
            }
        }*/

        this.minecraft.getTextureManager().bind(comgroup.getBackgroundImage());
        this.blit(p_230450_1_, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.searchBox.render(p_230450_1_, p_230450_3_, p_230450_4_, p_230450_2_);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.leftPos + 175;
        int j = this.topPos + 18;
        int k = j + 112;
        this.minecraft.getTextureManager().bind(comgroup.getTabsImage());

        if (comgroup.canScroll()) { //BLIT FOR SCROLLBAR
            this.blit(p_230450_1_, i, j + (int)((float)(k - j - 17) * this.scrollOffs), 232 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        }

        //TODO - This might be a problem... keep an eye here during debug
        if ((comgroup == null || comgroup.getTabPage() != tabPage))// && (comgroup != CommunicatorInfoGroup.TAB_SEARCH && comgroup != CommunicatorInfoGroup.TAB_INVENTORY))
            return;

        this.renderTabButton(p_230450_1_, comgroup);

        //if (comgroup == CommunicatorInfoGroup.TAB_INVENTORY) {
        //    InventoryScreen.renderEntityInInventory(this.leftPos + 88, this.topPos + 45, 20, (float)(this.leftPos + 88 - p_230450_3_), (float)(this.topPos + 45 - 30 - p_230450_4_), this.minecraft.player);
        //}

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

    protected boolean checkTabHovering(MatrixStack p_238809_1_, CommunicatorInfoGroup p_238809_2_, int p_238809_3_, int p_238809_4_) {
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

    protected void renderTabButton(MatrixStack p_238808_1_, CommunicatorInfoGroup p_238808_2_) {
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

        RenderSystem.color3f(1F, 1F, 1F); //Forge: Reset color in case Items change it.
        RenderSystem.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
        this.blit(p_238808_1_, l, i1, j, k, 28, 32);
        this.itemRenderer.blitOffset = 100.0F;
        l = l + 6;
        i1 = i1 + 8 + (flag1 ? 1 : -1);
        RenderSystem.enableRescaleNormal();
        ItemStack itemstack = p_238808_2_.getIconItem();
        this.itemRenderer.renderAndDecorateItem(itemstack, l, i1);
        this.itemRenderer.renderGuiItemDecorations(this.font, itemstack, l, i1);
        this.itemRenderer.blitOffset = 0.0F;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

}
