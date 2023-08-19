package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.event.ModEvents;
import com.hideout.elementalarsenal.network.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ElementalArsenalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicateProvider.registerModelPredicates();
        ModColorProvider.registerColors();
        ModEvents.registerClientEvents();
        ModClientParticles.registerClientParticles();
        ModClientEntities.register();
        ModItemRenderers.register();

        ModMessages.registerS2CPackets();
    }
}
