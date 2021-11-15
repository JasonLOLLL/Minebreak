package com.jasonjat.minebreak.entity.render;

import com.jasonjat.minebreak.Minebreak;
import com.jasonjat.minebreak.entity.NewVehicleEntity;
import com.jasonjat.minebreak.entity.model.WheelModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class NewVehicleEntityRenderer extends EntityRenderer<NewVehicleEntity> {
    private final Identifier TEXTURE = new Identifier(Minebreak.MODID, "textures/entity/vehicle/wheel.png");
    private final EntityRendererFactory.Context ctx;
    private final Model model;

    public NewVehicleEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.ctx = ctx;
        this.model = new WheelModel(ctx);
    }

    @Override
    public Identifier getTexture(NewVehicleEntity entity) {
        return null;
    }

    @Override
    public void render(NewVehicleEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1,1,1,1);
    }
}
