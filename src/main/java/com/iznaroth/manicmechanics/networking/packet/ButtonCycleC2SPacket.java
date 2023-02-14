package com.iznaroth.manicmechanics.networking.packet;

import com.iznaroth.manicmechanics.blockentity.interfaces.IHasCyclableButton;
import com.iznaroth.manicmechanics.blockentity.interfaces.IHasEnergyStorage;
import com.iznaroth.manicmechanics.menu.SealingChamberBlockMenu;
//import com.iznaroth.manicmechanics.blockentity.SealingChamberBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ButtonCycleC2SPacket {
    private final int which;
    private final int dir;
    private final BlockPos pos;

    public ButtonCycleC2SPacket(int which, int dir, BlockPos pos) {
        this.which = which;
        this.dir = dir;
        this.pos = pos;
    }

    public ButtonCycleC2SPacket(FriendlyByteBuf buf) {
        this.which = buf.readInt();
        this.dir = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(which);
        buf.writeInt(dir);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerLevel destination = context.getSender().getLevel();
        context.enqueueWork(() -> {
            BlockEntity blockEntity = destination.getBlockEntity(pos);
            if(blockEntity instanceof IHasCyclableButton) {
                switch(this.dir){
                    case 0: ((IHasCyclableButton) blockEntity).cycleBackward(which);
                    case 1: ((IHasCyclableButton) blockEntity).cycleForward(which);
                }

                System.out.println("Updating button state for entity! " + Minecraft.getInstance().level.isClientSide);
            }
        });
        return true;
    }
}