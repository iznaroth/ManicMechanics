package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.blockentity.interfaces.IHasEnergyStorage;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasInvHandler;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.menu.GeneratorBlockMenu;
import com.iznaroth.manicmechanics.networking.MMMessages;
import com.iznaroth.manicmechanics.networking.packet.EnergySyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ItemStackSyncS2CPacket;
import com.iznaroth.manicmechanics.setup.Config;
import com.iznaroth.manicmechanics.tools.CustomEnergyStorage;
import com.mojang.math.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorBlockEntity extends BlockEntity implements MenuProvider, IHasInvHandler, IHasEnergyStorage {
    private ItemStackHandler itemHandler = new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
                if(!level.isClientSide()) {
                    MMMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
                }
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == MMItems.DYSPERSIUM_DUST.get();
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != MMItems.DYSPERSIUM_DUST.get()) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
    };

    private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(Config.FIRSTBLOCK_MAXPOWER.get(), 0) {
        @Override
        protected void onEnergyChanged() {
            setChanged();
            if(!level.isClientSide()) {
                MMMessages.sendToClients(new EnergySyncS2CPacket(this.getEnergyStored(), worldPosition));
            }
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            if (!canExtract())
                return 0;

            int energyExtracted = Math.min(energyStorage.getEnergyStored(), Math.min(this.maxExtract, maxExtract));
            if (!simulate)
                energyStorage.consumeEnergy(energyExtracted);
            return energyExtracted;
        }

    };

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    private static int counter;
    private int maxExtract;

    protected final ContainerData data;


    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(MMBlockEntities.GENERATOR_TILE.get(), pos, state);
        this.maxExtract = 1000;
        this.data = new ContainerData() { //For passing any important info through to the Menu on creation.
            @Override
            public int get(int p_39284_) {
                return 0;
            }

            @Override
            public void set(int p_39285_, int p_39286_) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }


    public static void tick(Level level, BlockPos pos, BlockState state, GeneratorBlockEntity pEntity) {
        if (level.isClientSide) {
            return;
        }

        if (counter > 0) {
            counter--;
            if (counter <= 0) {
                pEntity.energyStorage.addEnergy(Config.FIRSTBLOCK_GENERATE.get());
            }
            setChanged(level, pos, state);
        }

        if (counter <= 0) {
            ItemStack stack = pEntity.itemHandler.getStackInSlot(0);
            if (stack.getItem() == MMItems.DYSPERSIUM_DUST.get()) {
                pEntity.itemHandler.extractItem(0, 1, false);
                counter = Config.FIRSTBLOCK_TICKS.get();
                setChanged(level, pos, state);
            }
        }

        BlockState blockState = level.getBlockState(pos);
        if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
            level.setBlock(pos, blockState.setValue(BlockStateProperties.POWERED, counter > 0),
                    3);
        }

        //sendOutPower();
    }


    /**
     * Old impl for energy transfer.
     *
     * private void sendOutPower() {
     *         AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
     *         if (capacity.get() > 0) {
     *             for (Direction direction : Direction.values()) {
     *                 BlockEntity te = level.getBlockEntity(worldPosition.relative(direction));
     *                 if (te != null) {
     *                     boolean doContinue = te.getCapability(ForgeCapabilities.ENERGY, direction).map(handler -> {
     *                                 if (handler.canReceive()) {
     *                                     int received = handler.receiveEnergy(Math.min(capacity.get(), Config.FIRSTBLOCK_SEND.get()), false);
     *                                     capacity.addAndGet(-received);
     *                                     energyStorage.consumeEnergy(received);
     *                                     setChanged();
     *                                     return capacity.get() > 0;
     *                                 } else {
     *                                     return true;
     *                                 }
     *                             }
     *                     ).orElse(true);
     *                     if (!doContinue) {
     *                         return;
     *                     }
     *                 }
     *             }
     *         }
     *     }
     * @param tag
     */

    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        energyStorage.deserializeNBT(tag.getCompound("energy"));

        counter = tag.getInt("counter");
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());

        tag.putInt("counter", counter);
        super.saveAdditional(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.manicmechanics.hep");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new GeneratorBlockMenu(p_39954_, p_39955_, this, this.data);
    }

    @Override
    public void setEnergy(int to) {
        this.energyStorage.setEnergy(to);
    }
}
