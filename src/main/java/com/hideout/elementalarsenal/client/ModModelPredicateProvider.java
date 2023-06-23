package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.item.custom.interfaces.IElementalItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.ELEMENTAL_SWORD, new Identifier("type"),
                (stack, world, entity, seed) -> (float) stack.getOrCreateNbt().getInt(IElementalItem.TYPE)/10);

        ModelPredicateProviderRegistry.register(ModItems.ELEMENTAL_GEM, new Identifier("type"),
                (stack, world, entity, seed) -> ((float) stack.getOrCreateNbt().getInt(IElementalItem.TYPE)/10));
    }
}
