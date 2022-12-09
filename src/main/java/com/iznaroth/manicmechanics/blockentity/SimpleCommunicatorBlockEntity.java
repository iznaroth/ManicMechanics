package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.ECPBlock;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasInvHandler;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.menu.SimpleCommunicatorBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.ItemStackSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleCommunicatorBlockEntity extends BlockEntity implements MenuProvider, IHasInvHandler {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                MMMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == Items.BRICK;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private static Component[] messages = {
            Component.translatable("simplemsg.manicmechanics.001")
    };

    private static Item[] reqToMsgMap = {
            PatchouliAPI.get().getBookStack(new ResourceLocation(ManicMechanics.MOD_ID, "mm_dev_archive")).getItem()
    };

    int counter = 5;
    int latest_message = 0;

    protected final ContainerData data;


    public SimpleCommunicatorBlockEntity(BlockPos pos, BlockState state) {
        super(MMBlockEntities.SIMPLE_COMMUNICATOR_TILE.get(), pos, state);
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

    public static void tick(Level level, BlockPos pos, BlockState state, SimpleCommunicatorBlockEntity pEntity) {
        pEntity.readInput();
    }

    private void readInput(){
        if(level.isClientSide()){
            return;
        }

        Item in = itemHandler.getStackInSlot(0).getItem();
        if(in.equals(Items.AIR))
            return;


        BlockPos above = this.getBlockPos().relative(Direction.UP);
        if(level.getBlockState(above).getBlock().equals(MMBlocks.ELECTROSTATIC_COMMUNICATION_PYLON.get())) {

            if (in.equals(Items.BRICK)) {
                boolean success = ((ECPBlock) level.getBlockState(above).getBlock()).strikeAndAffirm(above, (ServerLevel) level);
                if(success)
                    itemHandler.setStackInSlot(0, MMItems.AUTHORIZED_SECURITY_BRICK.get().getDefaultInstance());
            }
            else
                updateHelpMsg(); //not empty and has a legal progression item?

        }


    }

    public Component getTextForDisplay(){
        return messages[latest_message];
    }

    private void updateHelpMsg(){

    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.manicmechanics.simple_communicator");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        System.out.println("CREATING MENU FROM BE");
        return new SimpleCommunicatorBlockMenu(p_39954_, p_39955_, this, this.data);
    }
}
