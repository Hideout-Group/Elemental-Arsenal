package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.util.ElementalUtils;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;

import java.util.Objects;

public class ModColorProvider {

    public static void registerColors() {
        registerElementalColorProvider(ModItems.ELEMENTAL_GEM, 0);
        registerElementalColorProvider(ModItems.ELEMENTAL_SWORD, 1);
        registerElementalColorProvider(ModItems.ELEMENTAL_TRIDENT, 1);
    }

    private static void registerElementalColorProvider(Item item, int layerToTint) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            boolean current = tintIndex == layerToTint;
            if (current) {
                return Objects.requireNonNull(ElementalUtils.getType(stack).getStyle().getColor()).getRgb();
            }
            return 0xFFFFFF;
        }, item);
    }
}
