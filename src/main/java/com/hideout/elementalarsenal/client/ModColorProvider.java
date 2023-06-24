package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.item.custom.interfaces.ElementalItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;

import java.util.Objects;

public class ModColorProvider {

    public static void registerColors() {
        registerElementalColorProvider(ModItems.ELEMENTAL_GEM, 0);
        registerElementalColorProvider(ModItems.ELEMENTAL_SWORD, 1);
    }

    private static void registerElementalColorProvider(Item item, int tintIndexToTint) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            boolean current = tintIndex == tintIndexToTint;
            if (current) {
                return Objects.requireNonNull(((ElementalItem) item).getType(stack).getStyle().getColor()).getRgb();
            }
            return 0xFFFFFF;
        }, item);
    }
}
