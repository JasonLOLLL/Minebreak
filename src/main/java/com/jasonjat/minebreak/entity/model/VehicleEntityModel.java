package com.jasonjat.minebreak.entity.model;

import com.jasonjat.minebreak.Minebreak;
import com.jasonjat.minebreak.entity.VehicleEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VehicleEntityModel extends AnimatedGeoModel<VehicleEntity> {
    @Override
    public Identifier getModelLocation(VehicleEntity object) {
        return new Identifier(Minebreak.MODID, "geo/vehicle.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VehicleEntity object) {
        return new Identifier(Minebreak.MODID, "textures/entity/vehicle/vehicle.png");
    }

    @Override
    public Identifier getAnimationFileLocation(VehicleEntity animatable) {
        return new Identifier(Minebreak.MODID, "animations/vehicle.animation.json");
    }
}
