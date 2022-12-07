package com.iznaroth.manicmechanics.menu;

import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import com.iznaroth.manicmechanics.tools.BlockValueGenerator;
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

import java.util.HashMap;

public class BureauBlockMenu extends AbstractContainerMenu {
    private BlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private Player playerEntity;
    private IItemHandler playerInventory;

    private static final Minecraft minecraft = Minecraft.getInstance();

    public BureauBlockMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public BureauBlockMenu(int windowId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(MMMenus.BUREAU_MENU.get(), windowId);
        blockEntity = entity;
        this.level = inv.player.level;
        this.data = data;

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 79, 99));
            });
        }
        layoutPlayerInventorySlots(8, 130);

        addDataSlots(data);
    }


    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, MMBlocks.CURRENCY_BUREAU.get());
    }

    @Override
    public boolean clickMenuButton(Player player, int k) { //On click, sell item for displayed amount.

        System.out.println("Let's sell da bit");

        Slot sellSlot = this.slots.get(0);

        Item to_sell = sellSlot.getItem().getItem();
        int quantity = sellSlot.getItem().getCount();

        if (to_sell == null) {
            System.out.println("Empty slot.");
            return false;
        } else {

            HashMap<Item, Integer> mappings = BlockValueGenerator.populateEconomyMapping(1, 1); //TEMP - how the fuck do I get the world seed?

            Integer profit = mappings.get(to_sell);

            if (profit == null) {
                System.out.println("I don't got a value for that item");
                return false;
            } else {
                player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(currency -> {
                    currency.addCurrency( profit * quantity);
                    sellSlot.set(Items.AIR.getDefaultInstance());

                });
                return true;
            }
        }
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
                if (!this.moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
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
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
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
}
