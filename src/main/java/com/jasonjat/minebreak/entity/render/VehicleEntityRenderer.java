package com.jasonjat.minebreak.entity.render;

import com.jasonjat.minebreak.entity.VehicleEntity;
import com.jasonjat.minebreak.entity.model.VehicleEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VehicleEntityRenderer extends GeoEntityRenderer<VehicleEntity> {
    public VehicleEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new VehicleEntityModel());
        this.shadowRadius = 0.7F;
    }
}
