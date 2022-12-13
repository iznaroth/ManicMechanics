package com.iznaroth.manicmechanics.networking.packet;


import com.iznaroth.manicmechanics.blockentity.interfaces.IHasCraftProgress;
import com.iznaroth.manicmechanics.menu.SealingChamberBlockMenu;
import com.iznaroth.manicmechanics.blockentity.SealingChamberBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProgressSyncS2CPacket {
    private final int progress;
    private final BlockPos pos;

    public ProgressSyncS2CPacket(int progress, BlockPos pos) {
        this.progress = progress;
        this.pos = pos;
    }

    public ProgressSyncS2CPacket(FriendlyByteBuf buf) {
        this.progress = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(progress);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
            if(blockEntity instanceof IHasCraftProgress) {
                ((IHasCraftProgress) blockEntity).setProgress(progress);

                //if(Minecraft.getInstance().player.containerMenu instanceof SealingChamberBlockMenu &&
                //        ((SealingChamberBlockMenu) Minecraft.getInstance().player.containerMenu).getBlockEntity().getBlockPos().equals(pos)) {
                //    ((SealingChamberBlockEntity) blockEntity).setProgress(progress);
                //}
            }
        });
        return true;
    }
}