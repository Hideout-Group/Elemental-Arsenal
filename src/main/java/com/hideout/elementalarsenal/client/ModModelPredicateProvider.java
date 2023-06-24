package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.item.custom.interfaces.ElementalItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerModelPredicates() {

    }

    private static void registerElementalItem(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("type"),
                (stack, world, entity, seed) -> ((float) ((ElementalItem) item).getType(stack).getId()/10));
    }
}
