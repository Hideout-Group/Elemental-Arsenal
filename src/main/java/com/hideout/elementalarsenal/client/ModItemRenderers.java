package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.entity.ModEntities;
import com.hideout.elementalarsenal.entity.client.renderer.ElementalTridentEntityRenderer;
import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.item.client.renderer.ElementalTridentItemRenderer;
import com.hideout.elementalarsenal.util.ElementalType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModItemRenderers {
    public static void register() {
        ElementalTridentItemRenderer itemRenderer = new ElementalTridentItemRenderer(EntityModelLayers.TRIDENT);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(itemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.ELEMENTAL_TRIDENT, itemRenderer);

        EntityRendererRegistry.register(ModEntities.ELEMENTAL_TRIDENT, ElementalTridentEntityRenderer::new);

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) ->
                out.accept(new ModelIdentifier(Registries.ITEM.getId(ModItems.ELEMENTAL_TRIDENT).withSuffixedPath("_in_inventory"), "inventory")));
    }
}
