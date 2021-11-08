package com.jasonjat.minebreak.client;

import com.jasonjat.minebreak.entity.render.VehicleEntityRenderer;
import com.jasonjat.minebreak.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class MinebreakClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.VEHICLE_ENTITY, VehicleEntityRenderer::new);
    }
}
