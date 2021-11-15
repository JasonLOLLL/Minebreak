package com.jasonjat.minebreak.entity.model;

import com.jasonjat.minebreak.Minebreak;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.GeoBuilder;
import software.bernie.geckolib3.geo.render.built.GeoCube;

public class WheelModel extends Model {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(new Identifier(Minebreak.MODID, "textures/entity/vehicle/wheel"), "main");

    private final ModelPart main;
    private final ModelPart part2;
    private final ModelPart part3;
    private final ModelPart part4;


    public WheelModel(EntityRendererFactory.Context ctx) {
        super(RenderLayer::getEntityCutout);
        this.main = ctx.getPart(MODEL_LAYER).getChild("main");
        this.part2 = main.getChild("part2");
        this.part3 = main.getChild("part3");
        this.part4 = main.getChild("part4");
    }

    private void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

    public static TexturedModelData createModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        ModelPartData partData = root.addChild("main", ModelPartBuilder.create().uv(0,3).cuboid(-3,0,-1,6,1,2), ModelTransform.pivot(0,0,0));
        partData.addChild("part2", ModelPartBuilder.create().uv(0,0).cuboid(-3,5,-1,6,1,2), ModelTransform.pivot(0,0,0));
        partData.addChild("part3", ModelPartBuilder.create().uv(6,6).cuboid(-3,1,-1,1,4,2), ModelTransform.pivot(0,0,0));
        partData.addChild("part4", ModelPartBuilder.create().uv(6,6).cuboid(2,1,-1,1,4,2), ModelTransform.pivot(0,0,0));

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
        matrices.translate(1,0,0);
        main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }
}
