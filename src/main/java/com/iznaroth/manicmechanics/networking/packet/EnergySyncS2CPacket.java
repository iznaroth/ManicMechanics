package com.iznaroth.manicmechanics.networking.packet;


import com.iznaroth.manicmechanics.blockentity.interfaces.IHasEnergyStorage;
import com.iznaroth.manicmechanics.menu.SealingChamberBlockMenu;
//import com.iznaroth.manicmechanics.blockentity.SealingChamberBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {
    private final int energy;
    private final BlockPos pos;

    public EnergySyncS2CPacket(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public EnergySyncS2CPacket(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
            if(blockEntity instanceof IHasEnergyStorage) { //TODO - All machines ought to generally implement this thru hasCapability or something
                ((IHasEnergyStorage) blockEntity).setEnergy(energy);
                System.out.println("Updating energy for entity!");

                //if(Minecraft.getInstance().player.containerMenu instanceof SealingChamberBlockMenu &&
                //        ((SealingChamberBlockMenu) Minecraft.getInstance().player.containerMenu).getBlockEntity().getBlockPos().equals(pos)) {
                //     blockEntity).setEnergy(energy);
                //} NOTE - This might be redundant. If it isn't (if it doesn't update screen bars), just add an interface to menus with power indicators.
            }
        });
        return true;
    }
}