package com.iznaroth.manicmechanics.container;

import com.iznaroth.manicmechanics.screen.CommunicatorBlockScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

@OnlyIn(Dist.CLIENT)
public class CommunicatorBlockContainer extends Container {
    public final NonNullList<ItemStack> items = NonNullList.create();

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public CommunicatorBlockContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(IndustrizerContainers.COMMUNICATOR_CONTAINER.get(), windowId);
        PlayerInventory playerinventory = player.inventory;

        tileEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);


        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerinventory, k, 9 + k * 18, 112));
        }

        this.scrollTo(0.0F);
    }

    public boolean stillValid(PlayerEntity p_75145_1_) {
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

    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
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
