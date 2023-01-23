package com.iznaroth.manicmechanics.screen.renderer;

import com.iznaroth.manicmechanics.tools.BlockValueGenerator;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import java.util.List;

public class MarketEntry extends GuiComponent {

    private Item entryrep;
    private int price;
    private int rankAnchor; //which position this occupies in the list, kept here so-as to nest the draw function here rather than duplicate it

    protected Rect2i area;

    public MarketEntry(Item init, int currAnchor, int xMin, int yMin){
        System.out.println(xMin + " " + yMin);
        area = new Rect2i(xMin + 92, yMin + 21 + (currAnchor * 18), 10, 50);
        entryrep = init;
        price = BlockValueGenerator.getValOrPopulate(init);
        rankAnchor = currAnchor;
    }

    public void rebuildOnCycle(Item newEntryItem, int newAnchor, int xMin, int yMin){
        //Whenever a list of market entries moves one step, it just repurposes the cycled entry into the new addition
        //Necessary optimizations for long-dragging will ideally happen outside of this script.
        entryrep = newEntryItem;
        price = BlockValueGenerator.getValOrPopulate(newEntryItem);
        rankAnchor = newAnchor;


        area = new Rect2i(xMin + 92, yMin + 21 + (newAnchor * 16), 10, 50);
    }

    public Component getTooltip() {
        return Component.literal(entryrep.toString());
    }

    public void draw(PoseStack transform) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.renderGuiItem(entryrep.getDefaultInstance(), area.getX() + 1, area.getY());
        Component tc = Component.literal(" :  " + price);
        Minecraft.getInstance().font.drawShadow(transform, tc, area.getX() + 18, area.getY() + 4, 0x09FF00); //Basic impl - icon and cash
    }
}
