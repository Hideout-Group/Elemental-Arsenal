package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.event.ModEvents;
import com.hideout.elementalarsenal.network.ModMessages;
import io.netty.handler.codec.DecoderException;
import net.fabricmc.api.ClientModInitializer;

public class ElementalArsenalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicateProvider.registerModelPredicates();
        ModColorProvider.registerColors();
        ModEvents.registerClientEvents();

        ModMessages.registerS2CPackets();
    }
}
