package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.ECPBlock;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.item.MMItems;
import net.minecraft.block.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.chat.Component;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleCommunicatorBlockTile extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private static TranslationTextComponent[] messages = {
            new TranslationTextComponent("simplemsg.manicmechanics.001")
    };

    private static Item[] reqToMsgMap = {
            PatchouliAPI.get().getBookStack(new ResourceLocation(ManicMechanics.MOD_ID, "mm_dev_archive")).getItem()
    };

    int counter = 5;
    int latest_message = 0;


    public SimpleCommunicatorBlockTile() {
        super(MMBlockEntities.SIMPLE_COMMUNICATOR_TILE.get());
    }


    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }


    @Override
    public void load(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        counter = tag.getInt("counter");
        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("counter", counter);
        return super.save(tag);
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

    @Override
    public void tick() {
        readInput();
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
                boolean success = ((ECPBlock) level.getBlockState(above).getBlock()).strikeAndAffirm(above, (ServerWorld) level);
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


}
