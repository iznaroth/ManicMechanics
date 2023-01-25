package com.iznaroth.manicmechanics.screen.renderer;

import com.iznaroth.manicmechanics.tools.BlockValueGenerator;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;

public class MarketEntry extends GuiComponent {

    private Item entryItem;
    private int price;
    private int rankAnchor; //which position this occupies in the list, kept here so-as to nest the draw function here rather than duplicate it

    protected Rect2i area;

    public MarketEntry(Item init, int currAnchor, int xMin, int yMin){
        System.out.println(xMin + " " + yMin);
        area = new Rect2i(xMin + 92, yMin + 21 + (currAnchor * 18), 10, 50);
        entryItem = init;
        price = BlockValueGenerator.getValOrPopulate(init);
        rankAnchor = currAnchor;
    }

    public void rebuildOnCycle(Item newEntryItem){
        //Whenever a list of market entries moves one step, it just repurposes the cycled entry into the new addition
        //Necessary optimizations for long-dragging will ideally happen outside of this script.
        entryItem = newEntryItem;
        price = BlockValueGenerator.getValOrPopulate(newEntryItem);
    }

    public void draw(PoseStack transform) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.renderGuiItem(entryItem.getDefaultInstance(), area.getX() + 1, area.getY());
        Component tc = Component.literal(" :  " + price);
        Minecraft.getInstance().font.drawShadow(transform, tc, area.getX() + 18, area.getY() + 4, 0x09FF00); //Basic impl - icon and cash
    }

    public List<Component> getTooltip() {
        return List.of(entryItem.getName(entryItem.getDefaultInstance()));
    }

    public Item getEntryItem(){
        return this.entryItem;
    }
}
