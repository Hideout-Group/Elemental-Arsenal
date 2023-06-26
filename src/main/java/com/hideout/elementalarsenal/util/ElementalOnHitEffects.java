package com.hideout.elementalarsenal.util;

import com.hideout.elementalarsenal.effect.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

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

        target.setOnFire(false);
    }
    private static void fire(LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(5);
    }
    private static void lightning(LivingEntity target, LivingEntity attacker) {
        if (target.getWorld().getRandom().nextFloat() < 0.3) {
            target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.ELECTRIFIED, 60, 1));
        }
    }
    private static void nature(LivingEntity target, LivingEntity attacker) {

        World world = attacker.getWorld();
        if (target instanceof PlayerEntity player) {
            List<AnimalEntity> entities = world.getEntitiesByClass(AnimalEntity.class, new Box(target.getBlockPos()).expand(10), (entity) -> true);
            entities.forEach((entity -> {
                entity.setAttacking(player);
            }));
        }
    }
    private static void ice(LivingEntity target, LivingEntity attacker) {
        if (target.getWorld().getRandom().nextFloat() < 0.3) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1));
        }
    }
}
