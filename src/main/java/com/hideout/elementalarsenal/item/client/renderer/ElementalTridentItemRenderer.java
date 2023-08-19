package com.hideout.elementalarsenal.item.client.renderer;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.entity.client.model.ElementalTridentEntityModel;
import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.util.ElementalType;
import com.hideout.elementalarsenal.util.ElementalUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;

@Environment(EnvType.CLIENT)
public class ElementalTridentItemRenderer implements SimpleSynchronousResourceReloadListener, BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final Identifier id;
    private final EntityModelLayer modelLayer;
    private ItemRenderer itemRenderer;
    private ElementalTridentEntityModel tridentModel;
    private BakedModel inventoryTridentModel;

    public ElementalTridentItemRenderer(EntityModelLayer modelLayer) {
        this.id = new Identifier(ElementalArsenal.MOD_ID, "elemental_trident_renderer");
        this.modelLayer = modelLayer;
    }

    public Identifier getTexture(ElementalType type) {
        return new Identifier(ElementalArsenal.MOD_ID, "textures/entity/" + Registries.ITEM.getId(ModItems.ELEMENTAL_TRIDENT).getPath() + "_" + type.toString().toLowerCase() + ".png");
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        assert this.tridentModel != null;
        if (mode == ModelTransformationMode.GUI || mode == ModelTransformationMode.GROUND || mode == ModelTransformationMode.FIXED) {
            matrices.pop();
            matrices.push();
            itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryTridentModel);
        } else {
            matrices.push();
            matrices.scale(1f, -1f, -1f);
            ElementalType type = ElementalUtils.getType(stack);

            VertexConsumer consumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.tridentModel.getLayer(getTexture(type)), false, stack.hasGlint());
            this.tridentModel.render(matrices, consumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            matrices.pop();
        }
    }

    @Override
    public Identifier getFabricId() {
        return id;
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.MODELS);
    }

    @Override
    public void reload(ResourceManager manager) {
        MinecraftClient client = MinecraftClient.getInstance();
        this.itemRenderer = client.getItemRenderer();
        this.tridentModel = new ElementalTridentEntityModel(client.getEntityModelLoader().getModelPart(this.modelLayer));
        this.inventoryTridentModel = client.getBakedModelManager().getModel(new ModelIdentifier(Registries.ITEM.getId(ModItems.ELEMENTAL_TRIDENT).withSuffixedPath("_in_inventory"), "inventory"));
    }
}
