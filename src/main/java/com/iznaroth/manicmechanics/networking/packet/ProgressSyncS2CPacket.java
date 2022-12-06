package com.iznaroth.manicmechanics.networking.packet;


import com.iznaroth.manicmechanics.container.SealingChamberBlockContainer;
import com.iznaroth.manicmechanics.blockentity.SealingChamberBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ProgressSyncS2CPacket {
    private final int progress;
    private final BlockPos pos;

    public ProgressSyncS2CPacket(int progress, BlockPos pos) {
        this.progress = progress;
        this.pos = pos;
    }

    public ProgressSyncS2CPacket(PacketBuffer buf) {
        this.progress = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(progress);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            TileEntity tileEntity = Minecraft.getInstance().level.getBlockEntity(pos);
            if(tileEntity instanceof SealingChamberBlockEntity) { //TODO - All machines ought to generally implement this thru hasCapability or something
                ((SealingChamberBlockEntity) tileEntity).setProgress(progress);

                if(Minecraft.getInstance().player.containerMenu instanceof SealingChamberBlockContainer &&
                        ((SealingChamberBlockContainer) Minecraft.getInstance().player.containerMenu).getTileEntity().getBlockPos().equals(pos)) {
                    ((SealingChamberBlockEntity) tileEntity).setProgress(progress);
                }
            }
        });
        return true;
    }
}