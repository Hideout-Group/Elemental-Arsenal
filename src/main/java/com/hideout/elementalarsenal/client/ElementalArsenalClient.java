package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.event.ModEvents;
import com.hideout.elementalarsenal.network.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class ElementalArsenalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicateProvider.registerModelPredicates();
        ModEvents.registerClientEvents();

        ModMessages.registerS2CPackets();
    }
}
