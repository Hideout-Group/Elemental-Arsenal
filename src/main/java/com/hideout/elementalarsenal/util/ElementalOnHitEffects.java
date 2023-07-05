package com.hideout.elementalarsenal.util;

import com.hideout.elementalarsenal.effect.ModStatusEffects;
import com.hideout.elementalarsenal.entity.goals.AttackUntilDefeatedGoal;
import com.hideout.elementalarsenal.mixin.MobEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

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
        if (target.getWorld().getRandom().nextFloat() < 0.3f) {
            target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.ELECTRIFIED, 100, 1));
        }
    }
    private static void nature(LivingEntity target, LivingEntity attacker) {

        World world = attacker.getWorld();
        List<AnimalEntity> entities = world.getEntitiesByClass(AnimalEntity.class, new Box(target.getBlockPos()).expand(10), (entity) -> true);
        entities.forEach((entity -> {
            if (entity == target) return;
            MobEntityAccessor mobEntityAccessor = ((MobEntityAccessor) entity);
            Optional<PrioritizedGoal> attackUntilDefeated = mobEntityAccessor.getGoalSelector().getGoals().stream()
                    .filter(goal -> goal.getGoal() instanceof AttackUntilDefeatedGoal).findFirst();
            if (attackUntilDefeated.isEmpty()) {
//                if (!entity.getAttributes().hasAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
//                    NbtCompound attackDamage = new NbtCompound();
//                    attackDamage.putString("Name","minecraft:generic.attack_damage");
//                    attackDamage.putDouble("Base", 3.0);
//                    System.out.println(attackDamage);
//                    NbtList attributes = entity.getAttributes().toNbt();
//                    attributes.add(attackDamage);
//                    entity.getAttributes().readNbt(attributes);
//                    entity.getAttributeInstance()
//                    System.out.println(entity.getAttributes().toNbt().toString());
//                }
                mobEntityAccessor.getGoalSelector().add(0, new AttackUntilDefeatedGoal(entity, target));
            } else {
                ((AttackUntilDefeatedGoal)attackUntilDefeated.get().getGoal()).setTarget(target);
            }
        }));
    }
    private static void ice(LivingEntity target, LivingEntity attacker) {
        if (target.getWorld().getRandom().nextFloat() < 0.3f) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 2));
        }
    }
}
