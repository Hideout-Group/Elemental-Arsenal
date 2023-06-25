package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.event.ModEvents;
import com.hideout.elementalarsenal.network.ModMessages;
import com.hideout.elementalarsenal.particle.ElectricSparkParticle;
import com.hideout.elementalarsenal.particle.ModParticles;
import io.netty.handler.codec.DecoderException;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

@Environment(EnvType.CLIENT)
public class ElementalArsenalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicateProvider.registerModelPredicates();
        ModColorProvider.registerColors();
        ModEvents.registerClientEvents();
        ModClientParticles.registerClientParticles();

        ModMessages.registerS2CPackets();
    }
}
