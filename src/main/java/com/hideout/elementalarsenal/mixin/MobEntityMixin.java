package com.hideout.elementalarsenal.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    // THIS IS SO UGLY LORD SAVE ME :pray:
    @Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
    public void modifyAttackDamage(Entity target, CallbackInfoReturnable<Boolean> cir) {
        MobEntity mobEntity = (MobEntity) (Object) this;
        float f;

        try {
            f = (float) mobEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        } catch (IllegalArgumentException exception) {
            f = 3.0f;
        }

        float g = (float)mobEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity) {
            f += EnchantmentHelper.getAttackDamage(mobEntity.getMainHandStack(), ((LivingEntity)target).getGroup());
            g += (float)EnchantmentHelper.getKnockback(mobEntity);
        }

        int i = EnchantmentHelper.getFireAspect(mobEntity);
        if (i > 0) {
            target.setOnFireFor(i * 4);
        }

        boolean bl = target.damage(mobEntity.getDamageSources().mobAttack(mobEntity), f);
        if (bl) {
            if (g > 0.0F && target instanceof LivingEntity) {
                ((LivingEntity)target).takeKnockback((g * 0.5F), MathHelper.sin(mobEntity.getYaw() * 0.017453292F), (double)(-MathHelper.cos(mobEntity.getYaw() * 0.017453292F)));
                mobEntity.setVelocity(mobEntity.getVelocity().multiply(0.6, 1.0, 0.6));
            }

            if (target instanceof PlayerEntity playerEntity) {
                mobEntity.disablePlayerShield(playerEntity, mobEntity.getMainHandStack(), playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
            }

            mobEntity.applyDamageEffects(mobEntity, target);
            mobEntity.onAttacking(target);
        }

        cir.setReturnValue(bl);
    }
}
