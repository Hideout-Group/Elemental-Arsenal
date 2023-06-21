package com.hideout.elementalarsenal.client;

import net.fabricmc.api.ClientModInitializer;

public class ElementalArsenalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicateProvider.registerModelPredicates();
    }
}
