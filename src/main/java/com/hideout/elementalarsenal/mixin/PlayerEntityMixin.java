package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.item.custom.interfaces.ElementalItem;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    public void checkForAirItem(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (getStackInHand(Hand.MAIN_HAND).getItem() instanceof ElementalItem item) {
            if (damageSource.isOf(DamageTypes.FALL) || damageSource.isOf(DamageTypes.FLY_INTO_WALL)) {
                if (item.getType(getStackInHand(Hand.MAIN_HAND)) == ElementalType.AIR) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void injectTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getMainHandStack();

        if (stack.getItem() instanceof ElementalItem item) {
            switch (item.getType(stack)) {
                case LIGHTNING -> {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 3, 2, false, false));
                }
                case FIRE -> {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3, 0, false, false));
                }
                case WATER -> {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 3, 0, false, false));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 3, 0, false, false));
                }
                case EARTH -> {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 3, 0, false, false));
                }
            }
        }
    }
}
