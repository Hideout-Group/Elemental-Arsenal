package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.entity.ModEntities;
import com.hideout.elementalarsenal.entity.client.model.EarthWallModel;
import com.hideout.elementalarsenal.entity.client.model.ModEntityModelLayers;
import com.hideout.elementalarsenal.entity.client.renderer.EarthWallRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModClientEntities {
    public static void register() {
        EntityRendererRegistry.register(ModEntities.EARTH_WALL, EarthWallRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.EARTH_WALL_LAYER, EarthWallModel::getTexturedModelData);
    }
}
