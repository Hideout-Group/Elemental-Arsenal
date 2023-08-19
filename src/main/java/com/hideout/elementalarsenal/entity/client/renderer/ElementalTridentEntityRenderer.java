package com.hideout.elementalarsenal.entity.client.renderer;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.entity.ElementalTridentEntity;
import com.hideout.elementalarsenal.entity.client.model.ElementalTridentEntityModel;
import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.util.ElementalType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class ElementalTridentEntityRenderer extends EntityRenderer<ElementalTridentEntity> {
    private final ElementalTridentEntityModel model;

    public ElementalTridentEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new ElementalTridentEntityModel(ctx.getPart(EntityModelLayers.TRIDENT));
    }

    public Identifier getTexture(ElementalType type) {
        return new Identifier(ElementalArsenal.MOD_ID, "textures/entity/" + Registries.ITEM.getId(ModItems.ELEMENTAL_TRIDENT).getPath() + "_" + type.toString().toLowerCase() + ".png");
    }

    @Override
    public void render(ElementalTridentEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90f));
        VertexConsumer consumer =
                ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, entity.isEnchanted());
        this.model.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ElementalTridentEntity entity) {
        return getTexture(entity.getElementalType());
    }
}
