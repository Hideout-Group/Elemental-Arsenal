package com.hideout.elementalarsenal.effect;

import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class ElectrifiedStatusEffect extends StatusEffect {
    public ElectrifiedStatusEffect() {
        super(StatusEffectCategory.HARMFUL,
                Objects.requireNonNull(ElementalType.LIGHTNING.getStyle().getColor()).getRgb());
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        final int i = 20; // Time between applications
        return duration % i == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 0) {
            entity.damage(entity.getDamageSources().lightningBolt(), MathHelper.clamp(amplifier, 1, 10));
        }
    }
}
