package com.jasonjat.minebreak.networking;

import net.minecraft.util.Identifier;

import static com.jasonjat.minebreak.Minebreak.MODID;

public class ModPackets {
    // Client To Server
    public static final Identifier INPUT_PACKET = new Identifier(MODID, "input_packet");

    // Server to Client
    public static final Identifier SYNC_VEHICLE_POSITION_PACKET = new Identifier(MODID, "sync_vehicle_position_packet");
}
