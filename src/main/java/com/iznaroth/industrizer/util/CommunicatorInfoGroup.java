package com.iznaroth.industrizer.util;

import com.iznaroth.industrizer.IndustrizerMod;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public abstract class CommunicatorInfoGroup {
    public static CommunicatorInfoGroup[] TABS = new CommunicatorInfoGroup[4];


    public static final CommunicatorInfoGroup TAB_EMAIL = (new CommunicatorInfoGroup(0, "email") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Items.GLASS_PANE);
        }
    }).setBackgroundImage(new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/communicator_gui.png"));


    public static final CommunicatorInfoGroup TAB_WIKI = (new CommunicatorInfoGroup(1, "wiki") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Items.BOOKSHELF);
        }
    }).setBackgroundImage(new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/communicator_gui.png"));


    public static final CommunicatorInfoGroup TAB_EMPLOYMENT = (new CommunicatorInfoGroup(2, "employment") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Items.COMPASS);
        }
    }).setBackgroundImage(new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/communicator_gui.png"));


    public static final CommunicatorInfoGroup TAB_CONTRACTS = (new CommunicatorInfoGroup(3, "contracts") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Items.PAPER);
        }
    }).setBackgroundImage(new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/communicator_gui.png"));


    private final int id;
    private final String langId;
    private final ITextComponent displayName;
    @Deprecated
    private String backgroundSuffix = "items.png";
    private net.minecraft.util.ResourceLocation backgroundLocation;
    private boolean canScroll = true;
    private boolean showTitle = true;
    private ItemStack iconItemStack;

    public CommunicatorInfoGroup(String label) {
        this(-1, label);
    }

    public CommunicatorInfoGroup(int p_i1853_1_, String p_i1853_2_) {
        this.langId = p_i1853_2_;
        this.displayName = new TranslationTextComponent("commGroup." + p_i1853_2_);
        this.iconItemStack = ItemStack.EMPTY;
        this.id = addGroupSafe(p_i1853_1_, this);
    }

    @OnlyIn(Dist.CLIENT)
    public int getId() {
        return this.id;
    }

    @OnlyIn(Dist.CLIENT)
    public ITextComponent getDisplayName() {
        return this.displayName;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIconItem() {
        if (this.iconItemStack.isEmpty()) {
            this.iconItemStack = this.makeIcon();
        }

        return this.iconItemStack;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract ItemStack makeIcon();

    public CommunicatorInfoGroup setBackgroundImage(net.minecraft.util.ResourceLocation texture) {
        this.backgroundLocation = texture;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean showTitle() {
        return this.showTitle;
    }

    public CommunicatorInfoGroup hideTitle() {
        this.showTitle = false;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean canScroll() {
        return this.canScroll;
    }

    public CommunicatorInfoGroup hideScroll() {
        this.canScroll = false;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public int getColumn() {
        if (id > 11) return ((id - 12) % 10) % 5;
        return this.id % 6;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isTopRow() {
        if (id > 11) return ((id - 12) % 10) < 5;
        return this.id < 6;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isAlignedRight() {
        return this.getColumn() == 5;
    }

    public int getTabPage() {
        return id < 12 ? 0 : ((id - 12) / 10) + 1;
    }

    public boolean hasSearchBar() {
        if(this == CommunicatorInfoGroup.TAB_EMPLOYMENT) {
            return false;
        }else {
            return true; //afaik all other Communicator tabs have a search bar.
        }
    }

    /**
     * Gets the width of the search bar of the creative tab, use this if your
     * creative tab name overflows together with a custom texture.
     *
     * @return The width of the search bar, 89 by default
     */
    public int getSearchbarWidth() {
        return 89;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getBackgroundImage() {
        if (backgroundLocation != null) return backgroundLocation; //FORGE: allow custom namespace
        return new net.minecraft.util.ResourceLocation("textures/gui/container/creative_inventory/tab_" + this.getBackgroundSuffix());
    }

    private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation(IndustrizerMod.MOD_ID, "textures/gui/communicator_tabs.png");

    @OnlyIn(Dist.CLIENT)
    public net.minecraft.util.ResourceLocation getTabsImage() {
        return CREATIVE_INVENTORY_TABS;
    }


    @OnlyIn(Dist.CLIENT)
    public String getBackgroundSuffix() {
        return this.backgroundSuffix;
    }

    public CommunicatorInfoGroup setBackgroundSuffix(String p_78025_1_) {
        this.backgroundSuffix = p_78025_1_;
        return this;
    }

    public int getLabelColor() {
        return 4210752;
    }

    public int getSlotColor() {
        return -2130706433;
    }

    public static synchronized int getGroupCountSafe() {
        return ItemGroup.TABS.length;
    }

    private static synchronized int addGroupSafe(int index, CommunicatorInfoGroup newGroup) {
        if(index == -1) {
            index = TABS.length;
        }
        if (index >= TABS.length) {
            CommunicatorInfoGroup[] tmp = new CommunicatorInfoGroup[index + 1];
            System.arraycopy(TABS, 0, tmp, 0, TABS.length);
            TABS = tmp;
        }
        TABS[index] = newGroup;
        return index;
    }
}
