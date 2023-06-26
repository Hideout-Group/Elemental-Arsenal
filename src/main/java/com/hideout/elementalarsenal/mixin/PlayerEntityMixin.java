package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.item.custom.interfaces.ElementalItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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
import java.util.concurrent.atomic.AtomicBoolean;

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
                    FrostWalkerEnchantment.freezeWater(player, player.getWorld(), player.getBlockPos(), 2);
                }
                case NATURE -> {
                    if (player.isSneaking()) {
                        World world = player.getWorld();
                        breedAnimalsInRange(world, player, 5);
                        growCropsInRange(world, player, 5, 2);
                    }
                }

                case AIR -> {
                    if (player.isSneaking()) {
                        World world = player.getWorld();
                        Box area = new Box(player.getBlockPos()).expand(5);
                        List<ItemEntity> items = world.getEntitiesByType(EntityType.ITEM, area,
                                itemEntity -> itemEntity.isAlive() && (!world.isClient || itemEntity.getItemAge() > 1) &&
                                        (itemEntity.getOwner() == null || !itemEntity.getOwner().equals(player) || !itemEntity.cannotPickup()) &&
                                        !itemEntity.getStack().isEmpty());
                        items.forEach(itemEntity -> itemEntity.setPosition(player.getPos()));
                    }
                }
            }
        }
    }

    @Unique
    private void breedAnimalsInRange(World world, PlayerEntity player, int range) {
        List<AnimalEntity> entities = world.getEntitiesByClass(AnimalEntity.class,
                new Box(player.getBlockPos()).expand(range), (entity) -> true);
        entities.forEach((entity -> {
            if (entity.getLoveTicks() <= 0) {
                entity.lovePlayer(player);
            }
        }));
    }

    @Unique
    private void growCropsInRange(World world, PlayerEntity player, int hRange, int vRange) {
        BlockPos.iterateOutwards(player.getBlockPos(), hRange, vRange, hRange).forEach((pos) -> {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof CropBlock crop) {
                if (world.random.nextFloat() < 0.03f) {
                    BoneMealItem.useOnFertilizable(new ItemStack(Items.BONE_MEAL), world, pos);
                    if (!world.isClient) {
                        if (crop.isMature(state)) {
                            Item replant = state.getBlock().getPickStack(world, pos, state).getItem();
                            boolean[] removedReplant = {false};
                            Block.getDroppedStacks(state, (ServerWorld) world, pos, null).forEach(itemStack -> {
                                if (!removedReplant[0] && itemStack.isOf(replant)) {
                                    itemStack.setCount(itemStack.getCount()-1);
                                    removedReplant[0] = true;
                                }
                                Block.dropStack(world, pos, itemStack);
                            });

                            state.onStacksDropped((ServerWorld) world, pos, ItemStack.EMPTY, false);

                            world.setBlockState(pos, state.with(CropBlock.AGE, 0));
                        }
                        ((ServerWorld) world).spawnParticles((ServerPlayerEntity) player, ParticleTypes.HAPPY_VILLAGER, false,
                                pos.getX() + (world.random.nextDouble() - 0.5) * 2,
                                pos.getY() + (world.random.nextDouble() - 0.5) * 2,
                                pos.getZ() + (world.random.nextDouble() - 0.5) * 2, 4, 0, 0, 0, 3);
                    }
                }
            } else if (world.getBlockState(pos).getBlock() instanceof FarmlandBlock) {
                if (world.random.nextFloat() < 0.1f) {
                    world.setBlockState(pos, world.getBlockState(pos).with(FarmlandBlock.MOISTURE, FarmlandBlock.MAX_MOISTURE));
                }
            }
        });
    }
}
