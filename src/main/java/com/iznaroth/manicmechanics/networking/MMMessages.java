package com.iznaroth.manicmechanics.networking;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.networking.packet.EnergySyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.FluidSyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ItemStackSyncS2CPacket;
import com.iznaroth.manicmechanics.networking.packet.ProgressSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class MMMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ManicMechanics.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncS2CPacket::new)
                .encoder(ItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(ItemStackSyncS2CPacket::handle)
                .add();


        net.messageBuilder(EnergySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncS2CPacket::new)
                .encoder(EnergySyncS2CPacket::toBytes)
                .consumerMainThread(EnergySyncS2CPacket::handle)
                .add();

        net.messageBuilder(ProgressSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ProgressSyncS2CPacket::new)
                .encoder(ProgressSyncS2CPacket::toBytes)
                .consumerMainThread(ProgressSyncS2CPacket::handle)
                .add();

        net.messageBuilder(FluidSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidSyncS2CPacket::new)
                .encoder(FluidSyncS2CPacket::toBytes)
                .consumerMainThread(FluidSyncS2CPacket::handle)
                .add();




    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

}
