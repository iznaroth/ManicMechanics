package com.iznaroth.manicmechanics.networking.packet;


import com.iznaroth.manicmechanics.container.SealingChamberBlockContainer;
import com.iznaroth.manicmechanics.blockentity.SealingChamberBlockTile;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {
    private final int energy;
    private final BlockPos pos;

    public EnergySyncS2CPacket(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public EnergySyncS2CPacket(PacketBuffer buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            TileEntity tileEntity = Minecraft.getInstance().level.getBlockEntity(pos);
            if(tileEntity instanceof SealingChamberBlockTile) { //TODO - All machines ought to generally implement this thru hasCapability or something
                ((SealingChamberBlockTile) tileEntity).setEnergy(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof SealingChamberBlockContainer &&
                        ((SealingChamberBlockContainer) Minecraft.getInstance().player.containerMenu).getTileEntity().getBlockPos().equals(pos)) {
                    ((SealingChamberBlockTile) tileEntity).setEnergy(energy);
                }
            }
        });
        return true;
    }
}