package com.jasonjat.minebreak.networking;

import com.jasonjat.minebreak.entity.VehicleEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPacketsC2S {
    
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.INPUT_PACKET, ModPacketsC2S::clientToServerInput);
    }

    private static void clientToServerInput(MinecraftServer minecraftServer, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf p, PacketSender packetSender) {
        Entity entity = player.getServerWorld().getEntityById(p.getInt(5));
        if (entity instanceof VehicleEntity vehicle) {

//            System.out.printf("%s, %s, %s, %s, %s, %d \n", p.getBoolean(0), p.getBoolean(1), p.getBoolean(2), p.getBoolean(3), p.getBoolean(4), p.getInt(5));

            vehicle.setInputs(p.getBoolean(0), p.getBoolean(1), p.getBoolean(2), p.getBoolean(3), p.getBoolean(4));
        }
    }
}
