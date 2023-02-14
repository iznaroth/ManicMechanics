package com.iznaroth.manicmechanics.networking.packet;

import com.iznaroth.manicmechanics.blockentity.interfaces.IHasCyclableButton;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasEnergyStorage;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasPayloadButton;
import com.iznaroth.manicmechanics.menu.SealingChamberBlockMenu;
//import com.iznaroth.manicmechanics.blockentity.SealingChamberBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PayloadButtonC2SPacket {
    private final int which;
    private final int payload;
    private final BlockPos pos;

    public PayloadButtonC2SPacket(int which, int payload, BlockPos pos) {
        this.which = which;
        this.payload = payload;
        this.pos = pos;
    }

    public PayloadButtonC2SPacket(FriendlyByteBuf buf) {
        this.which = buf.readInt();
        this.payload = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(which);
        buf.writeInt(payload);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerLevel destination = context.getSender().getLevel();
        context.enqueueWork(() -> {
            BlockEntity blockEntity = destination.getBlockEntity(pos);
            if(blockEntity instanceof IHasPayloadButton) {

                ((IHasPayloadButton) blockEntity).pressPayloadButton(this.which, this.payload);
                System.out.println("Updating button state for entity! " + Minecraft.getInstance().level.isClientSide);
            }
        });
        return true;
    }
}