package com.hideout.elementalarsenal.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;

public abstract class CustomParticleStatusEffect extends StatusEffect {

    protected CustomParticleStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getWorld().isClient && entity.getWorld().random.nextInt(getChance()) == 0) {
            entity.getWorld().addParticle(getParticle(), entity.offsetX((entity.getWorld().random.nextDouble() - 0.5) * 2),
                    entity.getRandomBodyY(), entity.offsetZ((entity.getWorld().random.nextDouble() - 0.5) * 2),
                    0d, 0d, 0d);
        }
    }

    public abstract DefaultParticleType getParticle();
    protected int getChance() {
        return 3;
    }
}
