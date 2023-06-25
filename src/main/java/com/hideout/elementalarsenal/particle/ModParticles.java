package com.hideout.elementalarsenal.particle;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType ELECTRIC_SPARK_PARTICLE = registerParticle(
            "electric_spark", FabricParticleTypes.simple());

    public static void registerParticles() {

    }

    private static DefaultParticleType registerParticle(String name, DefaultParticleType type) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(ElementalArsenal.MOD_ID, name), type);
    }
}
