package com.hideout.elementalarsenal.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.custom.interfaces.ElementalItem;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
                case ICE -> {
                    // TODO THINK OF NEW IDEA
                }
                case NATURE -> {
                    if (player.isSneaking()) {
                        World world = player.getWorld();
                        List<AnimalEntity> entities = world.getEntitiesByClass(AnimalEntity.class,
                                new Box(player.getBlockPos()).expand(10), (entity) -> true);
                        entities.forEach((entity -> {
                            if (entity.getLoveTicks() <= 0) {
                                entity.lovePlayer(player);
                            }
                        }));

                        BlockPos.iterateOutwards(player.getBlockPos(), 5, 3, 5).forEach((pos) -> {
                            if (world.getBlockState(pos).getBlock() instanceof CropBlock crop) {
                                if (world.random.nextFloat() < 0.1f) {
                                    crop.applyGrowth(world, pos, world.getBlockState(pos));
                                }
                            } else if (world.getBlockState(pos).getBlock() instanceof FarmlandBlock) {
                                if (world.random.nextFloat() < 0.1f) {
                                    world.setBlockState(pos, world.getBlockState(pos).with(FarmlandBlock.MOISTURE, FarmlandBlock.MAX_MOISTURE));
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
