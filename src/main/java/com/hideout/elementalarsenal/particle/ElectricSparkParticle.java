package com.hideout.elementalarsenal.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ElectricSparkParticle extends SpellParticle {
    protected ElectricSparkParticle(ClientWorld clientWorld, double d, double e, double f,
                                    double dx, double dy, double dz, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, dx, dy, dz, spriteProvider);

        this.velocityMultiplier = 0.6f;
        this.x = dx;
        this.y = dy;
        this.z = dz;
        this.maxAge = 20;

        this.red = 1f;
        this.blue = 1f;
        this.green = 1f;
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = 1 - ((float) 1 /maxAge) * age;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            ElectricSparkParticle particle = new ElectricSparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.sprites);
            particle.setSprite(sprites);
            return particle;
        }
    }
}
