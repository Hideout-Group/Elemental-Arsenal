package com.hideout.elementalarsenal.util;

import com.hideout.elementalarsenal.effect.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class ElementalOnHitEffects {
    public static void performOnHitEffect(ElementalType type, LivingEntity target, LivingEntity attacker) {
        switch (type) {
            case AIR -> ElementalOnHitEffects.air(target, attacker);
            case EARTH -> ElementalOnHitEffects.earth(target, attacker);
            case WATER -> ElementalOnHitEffects.water(target, attacker);
            case FIRE -> ElementalOnHitEffects.fire(target, attacker);
            case LIGHTNING -> ElementalOnHitEffects.lightning(target, attacker);
            case NATURE -> ElementalOnHitEffects.nature(target, attacker);
            case ICE -> ElementalOnHitEffects.ice(target, attacker);
        }
    }

    private static void air(LivingEntity target, LivingEntity attacker) {

    }
    private static void earth(LivingEntity target, LivingEntity attacker) {

    }
    private static void water(LivingEntity target, LivingEntity attacker) {
        attacker.heal(2);
        if (attacker.getWorld().isClient) {
            attacker.getWorld().playSound(null, attacker.getBlockPos(),
                    SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.PLAYERS);
        }
    }
    private static void fire(LivingEntity target, LivingEntity attacker) {

    }
    private static void lightning(LivingEntity target, LivingEntity attacker) {
        if (target.getWorld().getRandom().nextFloat() < 0.3) {
            target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.ELECTRIFIED, 60, 1));
        }
    }
    private static void nature(LivingEntity target, LivingEntity attacker) {

    }
    private static void ice(LivingEntity target, LivingEntity attacker) {

    }
}
