package com.hideout.elementalarsenal.entity.client.renderer;

import com.hideout.elementalarsenal.entity.EarthWallEntity;
import com.hideout.elementalarsenal.entity.client.model.EarthWallModel;
import com.hideout.elementalarsenal.entity.client.model.ModEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class EarthWallRenderer extends EntityRenderer<EarthWallEntity> {
    private final EarthWallModel model;
    private final BlockRenderManager manager;
    public EarthWallRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new EarthWallModel(ctx.getPart(ModEntityModelLayers.EARTH_WALL_LAYER));
        manager = ctx.getBlockRenderManager();
    }

    @Override
    public Identifier getTexture(EarthWallEntity entity) {
        System.out.println(Registries.BLOCK.getId(entity.getBlockState().getBlock()).withPrefixedPath("block/"));
        return Registries.BLOCK.getId(entity.getBlockState().getBlock()).withPrefixedPath("block/");
    }

    @Override
    public void render(EarthWallEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getSolid());

        matrices.push();
        matrices.translate(0f, entity.getBoundingBox().maxY, 0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));
        matrices.pop();


//        this.model.render(matrices, vertexConsumer, light, 0, 1f, 1f, 1f, 1f);
        BakedModel blockModel = this.manager.getModel(entity.getBlockState());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Box boundingBox = entity.getBoundingBox();
                BlockPos blockPos = BlockPos.ofFloored(
                        ((boundingBox.maxX - boundingBox.minX) * ((double) i / 3)),
                        ((boundingBox.maxY - boundingBox.minY) * ((double) j / 3)),
                        ((boundingBox.maxZ - boundingBox.minZ) * ((double) i / 3)));
                matrices.push();
                matrices.scale(Math.max(2f - ((float) j / 3), 1), 1, Math.max(2f - ((float) j / 3), 1));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(360 - (135 * ((float) j / 3))));
                this.manager.getModelRenderer().render(entity.getWorld(), blockModel, entity.getBlockState(), blockPos, matrices, vertexConsumer, false, Random.create(), entity.getBlockState().getRenderingSeed(entity.getBlockPos()), OverlayTexture.DEFAULT_UV);
                matrices.pop();
            }
        }
    }
}
