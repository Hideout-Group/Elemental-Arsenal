package com.hideout.elementalarsenal.client;

import com.hideout.elementalarsenal.particle.ElectricSparkParticle;
import com.hideout.elementalarsenal.particle.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.SpellParticle;

@Environment(EnvType.CLIENT)
public class ModClientParticles {
    public static void registerClientParticles() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.ELECTRIC_SPARK_PARTICLE,
                ElectricSparkParticle.EntityFactory::new);
    }
}
