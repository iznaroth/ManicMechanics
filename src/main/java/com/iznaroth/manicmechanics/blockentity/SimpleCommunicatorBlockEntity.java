package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.ECPBlock;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.item.MMItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleCommunicatorBlockEntity extends BlockEntity implements MenuProvider {

    private ItemStackHandler itemHandler = createHandler();

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private static Component[] messages = {
            Component.translatable("simplemsg.manicmechanics.001")
    };

    private static Item[] reqToMsgMap = {
            PatchouliAPI.get().getBookStack(new ResourceLocation(ManicMechanics.MOD_ID, "mm_dev_archive")).getItem()
    };

    int counter = 5;
    int latest_message = 0;


    public SimpleCommunicatorBlockEntity(BlockPos pos, BlockState state) {
        super(MMBlockEntities.SIMPLE_COMMUNICATOR_TILE.get(), pos, state);
    }


    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
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

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
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
        return null;
    }
}
