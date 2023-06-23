package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    private static final String TYPE = "type";
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "damage")
    public void injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity entity = (ItemEntity)(Object)this;

        if (entity.getStack().isOf(ModItems.ELEMENTAL_GEM)) {
            NbtCompound nbt = entity.getStack().getOrCreateNbt();
            if (nbt.getInt(TYPE) != ElementalType.getId(ElementalType.BLANK)) return;
            if (source.isIn(DamageTypeTags.IS_FIRE)){ //Convert to Fire
                if (!entity.getWorld().isClient) {
                    for (BlockPos pos : BlockPos.iterateOutwards(entity.getBlockPos(), 1, 1, 1)) {
                        if (entity.getWorld().getBlockState(pos).isOf(Blocks.FIRE)) {
                            entity.getEntityWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                        }
                    }

                    entity.getWorld().playSound(null, entity.getBlockPos(),
                            SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS);
                }
                nbt.putInt(TYPE, ElementalType.getId(ElementalType.FIRE));
                ElementalArsenal.LOGGER.info(ElementalType.toCasedString(ElementalType.fromId(nbt.getInt(TYPE))));
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void injectTick(CallbackInfo ci) {
        ItemEntity entity = (ItemEntity)(Object)this;

        if (entity.getStack().isOf(ModItems.ELEMENTAL_GEM)) {
            NbtCompound nbt = entity.getStack().getOrCreateNbt();
            if (nbt.getInt(TYPE) != ElementalType.getId(ElementalType.BLANK)) return;

            BlockState state = entity.getWorld().getBlockState(entity.getBlockPos()); // (very) Small microoptimisation, don't have to read the block state 5 times

            if (state.isOf(Blocks.GRAVEL)) {
                nbt.putInt(TYPE, ElementalType.getId(ElementalType.EARTH));
                ElementalArsenal.LOGGER.info(ElementalType.toCasedString(ElementalType.fromId(nbt.getInt(TYPE))));
                return;
            }

            if (state.isOf(Blocks.POWDER_SNOW)) {
                nbt.putInt(TYPE, ElementalType.getId(ElementalType.ICE));
                ElementalArsenal.LOGGER.info(ElementalType.toCasedString(ElementalType.fromId(nbt.getInt(TYPE))));
                if (!entity.getWorld().isClient) {
                    entity.getWorld().playSound(null,
                            entity.getBlockPos(), SoundEvents.BLOCK_POWDER_SNOW_STEP, SoundCategory.BLOCKS);
                }
                return;
            }

            if (state.isOf(Blocks.WATER)) {
                nbt.putInt(TYPE, ElementalType.getId(ElementalType.WATER));
                ElementalArsenal.LOGGER.info(ElementalType.toCasedString(ElementalType.fromId(nbt.getInt(TYPE))));
                if (!entity.getWorld().isClient) {
                    entity.getWorld().playSound(null,
                            entity.getBlockPos(), SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS);
                }
                return;
            }

            if (state.isOf(Blocks.LIGHTNING_ROD)) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, entity.getWorld());
                lightning.setPosition(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ());
                lightning.setCosmetic(true);
                entity.getWorld().spawnEntity(lightning);
                nbt.putInt(TYPE, ElementalType.getId(ElementalType.LIGHTNING));
                ElementalArsenal.LOGGER.info(ElementalType.toCasedString(ElementalType.fromId(nbt.getInt(TYPE))));
                return;
            }

            if (fallDistance >= 50f) {
                nbt.putInt(TYPE, ElementalType.getId(ElementalType.AIR));
                ElementalArsenal.LOGGER.info(ElementalType.toCasedString(ElementalType.fromId(nbt.getInt(TYPE))));
                if (!entity.getWorld().isClient) {
                    entity.getWorld().playSound(null,
                            entity.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS);
                }
            }
        }

    }
}
