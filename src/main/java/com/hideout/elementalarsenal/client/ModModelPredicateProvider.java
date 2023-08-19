package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.util.ElementalUtils;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.ELEMENTAL_TRIDENT, new Identifier("throwing"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerElementalItem(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("type"),
                (stack, world, entity, seed) -> ((float) (ElementalUtils.getType(stack).getId()/10)));
    }
}
