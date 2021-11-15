package com.jasonjat.minebreak.networking;

import com.jasonjat.minebreak.entity.NewVehicleEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPacketsS2C {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SYNC_VEHICLE_POSITION_PACKET, ModPacketsS2C::syncClientPositionWithServer);
    }

    private static void syncClientPositionWithServer(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        double x = packetByteBuf.readDouble();
        double y = packetByteBuf.readDouble();
        double z = packetByteBuf.readDouble();
        float yaw = packetByteBuf.readFloat();
        int entityId = packetByteBuf.readInt();

        client.execute(() -> {
            if (client.player.world.getEntityById(entityId) instanceof NewVehicleEntity vehicle) {
                vehicle.updatePositionAndAngles(x, y, z, yaw, 0);
            }
        });
    }

    public static void sendServerToClientVehiclePosition(NewVehicleEntity vehicle, ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeDouble(vehicle.getX());
        buf.writeDouble(vehicle.getY());
        buf.writeDouble(vehicle.getZ());
        buf.writeFloat(vehicle.getYaw());
        buf.writeInt(vehicle.getId());
        ServerPlayNetworking.send(player, ModPackets.SYNC_VEHICLE_POSITION_PACKET, buf);
    }
}
