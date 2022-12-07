package com.iznaroth.manicmechanics.networking.packet;


import com.iznaroth.manicmechanics.menu.SealingChamberBlockMenu;
import com.iznaroth.manicmechanics.blockentity.SealingChamberBlockEntity;
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
            if(blockEntity instanceof SealingChamberBlockEntity) { //TODO - All machines ought to generally implement this thru hasCapability or something
                ((SealingChamberBlockEntity) blockEntity).setEnergy(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof SealingChamberBlockMenu &&
                        ((SealingChamberBlockMenu) Minecraft.getInstance().player.containerMenu).getBlockEntity().getBlockPos().equals(pos)) {
                    ((SealingChamberBlockEntity) blockEntity).setEnergy(energy);
                }
            }
        });
        return true;
    }
}