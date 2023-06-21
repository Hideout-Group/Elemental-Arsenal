package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.BASE_ELEMENTAL_SWORD, new Identifier("type"),
                (stack, world, entity, seed) -> (float) stack.getOrCreateNbt().getInt("type"));
    }
}