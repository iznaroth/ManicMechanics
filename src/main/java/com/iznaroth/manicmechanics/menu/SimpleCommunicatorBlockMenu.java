package com.iznaroth.manicmechanics.menu;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.blockentity.SimpleCommunicatorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Arrays;
import java.util.List;

public class SimpleCommunicatorBlockMenu extends AbstractContainerMenu {

    private BlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private Player playerEntity;
    private IItemHandler playerInventory;

    private static final Minecraft minecraft = Minecraft.getInstance();

    public SimpleCommunicatorBlockMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public SimpleCommunicatorBlockMenu(int windowId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(MMMenus.SIMPLE_COMMUNICATOR_MENU.get(), windowId);
        blockEntity = entity;
        this.level = inv.player.level;
        this.data = data;

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 82, 43));
            });
        }
        layoutPlayerInventorySlots(10, 84);
    }


    public SimpleCommunicatorBlockEntity getBlockEntity(){
        return (SimpleCommunicatorBlockEntity) this.blockEntity;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, MMBlocks.SIMPLE_COMMUNICATOR.get());
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (stack.getItem() == Items.BRICK) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }



    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private final List<Item> valid_for_housing = Arrays.asList(MMItems.TUBE_HOUSING.get());
    private final List<Item> valid_for_insertion = Arrays.asList(MMItems.SEALANT.get());

    public boolean isValidHousing(Item what){
        return valid_for_housing.contains(what);
    }

    public boolean isValidInsertion(Item what){
        return valid_for_insertion.contains(what);
    }


}
