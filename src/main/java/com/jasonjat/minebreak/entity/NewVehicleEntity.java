package com.jasonjat.minebreak.entity;

import com.jasonjat.minebreak.networking.ModPackets;
import com.jasonjat.minebreak.networking.ModPacketsS2C;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NewVehicleEntity extends Entity {

    private boolean isAccelerating;
    private boolean pressingForward;
    private boolean pressingBack;
    private boolean pressingLeft;
    private boolean pressingRight;
    private boolean pressingJump;

    private final int acceleration = 10;
    private float steer;
    private Vec3d lastVelocity = Vec3d.ZERO;

    public NewVehicleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        System.out.println("hi nbt");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        System.out.println("write nbt!");
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!this.hasPlayerRider()) {
            return ActionResult.success(player.startRiding(this));
        }
        return ActionResult.PASS;
    }

    @Override
    public void baseTick() {
        if (this.hasPlayerRider()) {
            steeringTick();
            movementTick();
        }

        if (!this.world.isClient) {
            forNearbyPlayers(400, true, player -> ModPacketsS2C.sendServerToClientVehiclePosition(this, player));
        }
    }

    private void movementTick() {
        setYaw(getYaw()+steer*12);
        if (this.pressingForward) {
            accelerate();
        }
    }

    private void accelerate() {
        float angle = (float) Math.toRadians(getYaw());

        Vec3d movement = new Vec3d(-Math.sin(angle),0, Math.cos(angle)).multiply(0.2);
        System.out.println(movement);
        this.move(MovementType.SELF, movement);

//        if (this.getFirstPassenger() instanceof PlayerEntity player && this.world.isClient) {
//            player.setYaw(MathHelper.wrapDegrees(player.getYaw() + steer*12));
//            player.setBodyYaw(MathHelper.wrapDegrees(player.getYaw() + steer*12));
//        }
    }

    private void steeringTick() {
        if (pressingLeft == pressingRight) {
            steer = 0;
        } else if (pressingLeft) {
            steer-=0.42f;
            steer = Math.max(-1, steer);
        } else {
            steer+=0.42f;
            steer = Math.min(1, steer);
        }
    }

    public void setInputs(boolean pressingForward, boolean pressingBack, boolean pressingLeft, boolean pressingRight, boolean pressingJump) {
        // receives this client method call from the ClientPlayerEntity mixin for input
        if (world.isClient &&
                !(this.pressingForward == pressingForward &&
                this.pressingBack == pressingBack &&
                this.pressingLeft == pressingLeft &&
                this.pressingRight == pressingRight &&
                this.pressingJump == pressingJump)
        ) {
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

    private void forNearbyPlayers(int radius, boolean ignoreDriver, Consumer<ServerPlayerEntity> action) {
        for (PlayerEntity p : world.getPlayers()) {
            if (ignoreDriver && p == getFirstPassenger()) {
                continue;
            }
            if (p.getPos().distanceTo(getPos()) < radius && p instanceof ServerPlayerEntity player) {
                action.accept(player);
            }
        }
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public void updatePositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        super.updatePositionAndAngles(x, y, z, yaw, pitch);
    }

    @Nullable
    @Override
    public Entity getPrimaryPassenger() {
        return getFirstPassenger();
    }
}
