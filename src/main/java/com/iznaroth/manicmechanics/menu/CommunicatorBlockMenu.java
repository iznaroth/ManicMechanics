package com.iznaroth.manicmechanics.menu;

import com.iznaroth.manicmechanics.screen.CommunicatorBlockScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommunicatorBlockMenu extends AbstractContainerMenu {
    public final NonNullList<ItemStack> items = NonNullList.create();


    private BlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;


    public CommunicatorBlockMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public CommunicatorBlockMenu(int windowId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(MMMenus.COMMUNICATOR_MENU.get(), windowId);
        blockEntity = entity;
        this.level = inv.player.level;
        this.data = data;


        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inv, k, 9 + k * 18, 112));
        }

        this.scrollTo(0.0F);
    }

    public boolean stillValid(Player p_75145_1_) {
        return true;
    }

    public void scrollTo(float p_148329_1_) {
        int i = (this.items.size() + 9 - 1) / 9 - 5;
        int j = (int) ((double) (p_148329_1_ * (float) i) + 0.5D);
        if (j < 0) {
            j = 0;
        }

        for (int k = 0; k < 5; ++k) {
            for (int l = 0; l < 9; ++l) {
                int i1 = l + (k + j) * 9;
                if (i1 >= 0 && i1 < this.items.size()) {
                    CommunicatorBlockScreen.CONTAINER.setItem(l + k * 9, this.items.get(i1));
                } else {
                    CommunicatorBlockScreen.CONTAINER.setItem(l + k * 9, ItemStack.EMPTY);
                }
            }
        }

    }

    public boolean canScroll() {
        return this.items.size() > 45;
    }

    public ItemStack quickMoveStack(Player p_82846_1_, int p_82846_2_) {
        if (p_82846_2_ >= this.slots.size() - 9 && p_82846_2_ < this.slots.size()) {
            Slot slot = this.slots.get(p_82846_2_);
            if (slot != null && slot.hasItem()) {
                slot.set(ItemStack.EMPTY);
            }
        }

        return ItemStack.EMPTY;
    }

    public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
        return p_94530_2_.container != CommunicatorBlockScreen.CONTAINER;
    }

    public boolean canDragTo(Slot p_94531_1_) {
        return p_94531_1_.container != CommunicatorBlockScreen.CONTAINER;
    }
}
