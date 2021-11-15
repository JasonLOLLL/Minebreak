package com.jasonjat.minebreak.entity;

import com.jasonjat.minebreak.networking.ModPackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;

public class VehicleEntity extends PathAwareEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    private boolean isAccelerating;
    private boolean pressingForward;
    private boolean pressingBack;
    private boolean pressingLeft;
    private boolean pressingRight;
    private boolean pressingJump;

    private final int acceleration = 10;
    private int steer;

    public VehicleEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(100);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private<E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {
        if (pressingForward) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.vehicle.spinwheels", false));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        if (!this.world.isClient) {
            player.startRiding(this);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean canTakeDamage() {
        return false;
    }

    public void setInputs(boolean pressingForward, boolean pressingBack, boolean pressingLeft, boolean pressingRight, boolean pressingJump) {
        // receives this client method call from the ClientPlayerEntity mixin for input
        if (world.isClient) {
            PacketByteBuf inputPacket = new PacketByteBuf(Unpooled.buffer());
            inputPacket.writeBoolean(pressingForward).writeBoolean(pressingBack).writeBoolean(pressingLeft).writeBoolean(pressingRight).writeBoolean(pressingJump);
            inputPacket.writeInt(this.getId());

            //sends input to server then recalls this method on the server
            ClientPlayNetworking.send(ModPackets.INPUT_PACKET, inputPacket);
        }

        //set client and server fields
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.pressingJump = pressingJump;
    }

    @Override
    public void tick() {
        if (this.hasPlayerRider() && !this.world.isClient) {
            movementTick();
            steeringTick();
        }
        super.tick();
    }

    private void movementTick() {
        if (this.pressingForward) {
            accelerate();
        }
    }

    private void accelerate() {
        setYaw(getYaw()+steer);
        setVelocity(-Math.sin(Math.toRadians(getYaw())),0, Math.cos(Math.toRadians(getYaw())));
    }

    private void steeringTick() {
        if (pressingLeft && steer > -90) {
            steer-=5;
        } else if (pressingRight && steer < 90) {
            steer+=5;
        } else if (!pressingLeft && !pressingRight) {
            steer = 0;
        }
    }


}
