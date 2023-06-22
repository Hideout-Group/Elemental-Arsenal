package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
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
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "damage")
    public void injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity entity = (ItemEntity)(Object)this;

        if (entity.getStack().isOf(ModItems.ELEMENTAL_GEM)) {
            NbtCompound nbt = entity.getStack().getOrCreateNbt();
            if (nbt.getInt("type") != ElementalType.getId(ElementalType.BLANK)) return;
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
                nbt.putInt("type", ElementalType.getId(ElementalType.FIRE));
                ElementalArsenal.LOGGER.info(String.valueOf(nbt.getInt("type")));
            }
        }
    }
    @Inject(at = @At("HEAD"), method = "tick")
    public void injectTick(CallbackInfo ci) {
        ItemEntity entity = (ItemEntity)(Object)this;

        if (entity.getStack().isOf(ModItems.ELEMENTAL_GEM)) {
            NbtCompound nbt = entity.getStack().getOrCreateNbt();
            if (nbt.getInt("type") != ElementalType.getId(ElementalType.BLANK)) return;

            if (entity.getWorld().getBlockState(entity.getBlockPos()).isOf(Blocks.GRAVEL)) {
                nbt.putInt("type", ElementalType.getId(ElementalType.EARTH));
                ElementalArsenal.LOGGER.info(String.valueOf(nbt.getInt("type")));
                return;
            }
            if (entity.getWorld().getBlockState(entity.getBlockPos()).isOf(Blocks.POWDER_SNOW)) {
                nbt.putInt("type", ElementalType.getId(ElementalType.ICE));
                ElementalArsenal.LOGGER.info(String.valueOf(nbt.getInt("type")));
                return;
            }
        }

    }
}
