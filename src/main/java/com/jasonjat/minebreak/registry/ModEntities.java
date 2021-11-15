package com.jasonjat.minebreak.registry;

import com.jasonjat.minebreak.entity.NewVehicleEntity;
import com.jasonjat.minebreak.entity.VehicleEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.jasonjat.minebreak.Minebreak.MODID;

public class ModEntities {

    public static final EntityType<VehicleEntity> VEHICLE_ENTITY =  Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MODID, "vehicle"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, VehicleEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build()
    );

    public static final EntityType<NewVehicleEntity> NEWVEHICLE_ENTITY =  Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MODID, "newvehicle"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NewVehicleEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build()
    );

    public static void init() {
        FabricDefaultAttributeRegistry.register(VEHICLE_ENTITY, VehicleEntity.createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0));
    }
}
