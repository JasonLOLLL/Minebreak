package com.jasonjat.minebreak.client;

import com.jasonjat.minebreak.entity.model.WheelModel;
import com.jasonjat.minebreak.entity.render.NewVehicleEntityRenderer;
import com.jasonjat.minebreak.entity.render.VehicleEntityRenderer;
import com.jasonjat.minebreak.networking.ModPacketsS2C;
import com.jasonjat.minebreak.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class MinebreakClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.VEHICLE_ENTITY, VehicleEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.NEWVEHICLE_ENTITY, NewVehicleEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(WheelModel.MODEL_LAYER, WheelModel::createModelData);

        ModPacketsS2C.init();
    }
}
