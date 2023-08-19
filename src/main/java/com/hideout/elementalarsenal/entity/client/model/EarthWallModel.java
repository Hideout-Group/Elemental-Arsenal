package com.hideout.elementalarsenal.entity.client.model;

import com.hideout.elementalarsenal.entity.EarthWallEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class EarthWallModel extends EntityModel<EarthWallEntity> {
    private final ModelPart segment;
    private final ModelPart segment2;
    private final ModelPart segment3;
    public EarthWallModel(ModelPart root) {
        this.segment = root.getChild("segment");
        this.segment2 = root.getChild("segment2");
        this.segment3 = root.getChild("segment3");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData segment = modelPartData.addChild("segment", ModelPartBuilder.create().uv(-14, -14).cuboid(-8.0F, -3.9578F, -4.5631F, 16.0F, 31.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0422F, -3.4369F));

        ModelPartData cube_r1 = segment.addChild("cube_r1", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.6F, -26.5F, -7.8F, 15.2F, 31.0F, 15.6F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r2 = segment.addChild("cube_r2", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.7F, -26.5F, -7.8F, 15.4F, 31.0F, 15.6F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.5838F, 1.9413F, 0.2094F, 0.0F, 0.0F));

        ModelPartData cube_r3 = segment.addChild("cube_r3", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.8F, -26.5F, -8.0F, 15.6F, 31.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 14.5422F, 2.9369F, 0.0873F, 0.0F, 0.0F));

        ModelPartData bone = segment.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData segment2 = modelPartData.addChild("segment2", ModelPartBuilder.create().uv(-14, -14).cuboid(-8.0F, -3.9578F, -4.5631F, 16.0F, 31.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(15.0F, -3.0422F, -3.4369F));

        ModelPartData cube_r4 = segment2.addChild("cube_r4", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.6F, -26.5F, -7.8F, 15.2F, 31.0F, 15.6F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r5 = segment2.addChild("cube_r5", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.7F, -26.5F, -7.8F, 15.4F, 31.0F, 15.6F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.5838F, 1.9413F, 0.2094F, 0.0F, 0.0F));

        ModelPartData cube_r6 = segment2.addChild("cube_r6", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.8F, -26.5F, -8.0F, 15.6F, 31.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 14.5422F, 2.9369F, 0.0873F, 0.0F, 0.0F));

        ModelPartData bone2 = segment2.addChild("bone2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData segment3 = modelPartData.addChild("segment3", ModelPartBuilder.create().uv(-14, -14).cuboid(-8.0F, -3.9578F, -4.5631F, 16.0F, 31.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(-15.0F, -3.0422F, -3.4369F));

        ModelPartData cube_r7 = segment3.addChild("cube_r7", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.6F, -26.5F, -7.8F, 15.2F, 31.0F, 15.6F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r8 = segment3.addChild("cube_r8", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.7F, -26.5F, -7.8F, 15.4F, 31.0F, 15.6F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.5838F, 1.9413F, 0.2094F, 0.0F, 0.0F));

        ModelPartData cube_r9 = segment3.addChild("cube_r9", ModelPartBuilder.create().uv(-14, -14).cuboid(-7.8F, -26.5F, -8.0F, 15.6F, 31.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 14.5422F, 2.9369F, 0.0873F, 0.0F, 0.0F));

        ModelPartData bone3 = segment3.addChild("bone3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(EarthWallEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        segment.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        segment2.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        segment3.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}