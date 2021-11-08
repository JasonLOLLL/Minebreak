package com.jasonjat.minebreak.mixin;

import com.jasonjat.minebreak.entity.VehicleEntity;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow public Input input;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method="tickRiding", at = @At("TAIL"))
    public void tickRiding(CallbackInfo ci) {
        if (this.getVehicle() instanceof VehicleEntity) {
            VehicleEntity vehicle = (VehicleEntity) this.getVehicle();
            vehicle.setInputs(input.pressingForward, input.pressingBack, input.pressingLeft, input.pressingRight, input.jumping);
        }
    }
}
