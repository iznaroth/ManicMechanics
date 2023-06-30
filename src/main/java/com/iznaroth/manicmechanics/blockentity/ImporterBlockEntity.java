package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.api.ICurrency;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasInvHandler;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasPayloadButton;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import com.iznaroth.manicmechanics.menu.ImporterBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.ItemStackSyncS2CPacket;
import com.iznaroth.manicmechanics.tools.BlockValueGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;

public class ImporterBlockEntity extends BlockEntity implements IHasInvHandler, MenuProvider, IHasPayloadButton {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                MMMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return true;
        }
    };

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    int counter = 5;
    int latest_message = 0;

    protected final ContainerData data;


    public ImporterBlockEntity(BlockPos pos, BlockState state) {
        super(MMBlockEntities.IMPORTER_BE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        counter = tag.getInt("counter");
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("counter", counter);
        super.saveAdditional(tag);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ImporterBlockEntity pEntity) {
        //pEntity.readInput();
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.manicmechanics.importer");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        System.out.println("CREATING IMPORTER MENU FROM BE");
        return new ImporterBlockMenu(p_39954_, p_39955_, this, this.data);
    }

    public void pressPayloadButton(int which, int payload){
        if(which == 0){
            orderItem(payload);
        }
    }

    public void orderItem(int data){

        //NOTE - Welcome back. Current issue-to-fix is using the MC paradigm of screen button -> ContainerButtonClick handle -> menu logic. You need to relocate info and sync so that the quantity and item selected are accessible.
        //DATA stores the Item as an ID reference * 100 + the quantity
        //We floor data / 100 to get block ID, and we %100 to get quantity.
        int blockID = (int) Math.floor((double)data / 100);
        int quantity = data % 100;

        System.out.println(quantity);

        Item toBuy = (Item)ForgeRegistries.ITEMS.getValues().toArray()[blockID]; //THIS IS STUPID!
        System.out.println(toBuy + " sold!");


        assert this.level != null;
        ICurrency curr = CurrencyCapability.getBalance(Minecraft.getInstance().player).orElse(null);
        if(curr == null) {
            System.out.println("This dude has no money.");
        }

        if(curr.getCurrentBalance() <= 0){
            System.out.println("Handle below-zero debt-acc order here (ImporterBlockEntity line 184");
        }

        curr.removeCurrency(BlockValueGenerator.getValOrPopulate(toBuy) * quantity);

        this.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null).insertItem(0, new ItemStack(toBuy, quantity), false);
    }

}
