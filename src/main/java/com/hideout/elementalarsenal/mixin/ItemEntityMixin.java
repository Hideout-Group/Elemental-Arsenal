package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.item.custom.interfaces.IElementalItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract void setCovetedItem();

    @Shadow public abstract void setStack(ItemStack stack);

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "damage")
    public void injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity entity = (ItemEntity)(Object)this;

        if (entity.getStack().isOf(ModItems.ELEMENTAL_GEM)) {
            ItemStack stack = entity.getStack();
            IElementalItem item = (IElementalItem) stack.getItem();
            if (item.getType(stack) != ElementalType.BLANK) return;
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
                item.setType(stack, ElementalType.FIRE.getId());
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void injectTick(CallbackInfo ci) {
        ItemEntity entity = (ItemEntity)(Object)this;

        if (entity.getStack().isOf(ModItems.ELEMENTAL_GEM)) {
            ItemStack stack = entity.getStack();
            IElementalItem item = (IElementalItem) stack.getItem();
            if (item.getType(stack) != ElementalType.BLANK) return;

            BlockState state = entity.getWorld().getBlockState(entity.getBlockPos()); // (very) Small microoptimisation, don't have to read the block state 5 times

            if (state.isOf(Blocks.GRAVEL)) {
                item.setType(stack, ElementalType.EARTH);
                ElementalArsenal.LOGGER.info(stack.getName().getString());
                if (!entity.getWorld().isClient) {
                    entity.getWorld().playSound(null,
                            entity.getBlockPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS);
                }
                return;
            }

            if (state.isOf(Blocks.POWDER_SNOW)) {
                item.setType(stack, ElementalType.ICE);
                ElementalArsenal.LOGGER.info(stack.getName().getString());
                if (!entity.getWorld().isClient) {
                    entity.getWorld().playSound(null,
                            entity.getBlockPos(), SoundEvents.BLOCK_POWDER_SNOW_FALL, SoundCategory.BLOCKS);
                }
                return;
            }

            if (state.isOf(Blocks.WATER)) {
                item.setType(stack, ElementalType.WATER);
                ElementalArsenal.LOGGER.info(stack.getName().getString());
                if (!entity.getWorld().isClient) {
                    entity.getWorld().playSound(null,
                            entity.getBlockPos(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.BLOCKS);
                }
                return;
            }

            if (state.isOf(Blocks.LIGHTNING_ROD)) {
                item.setType(stack, ElementalType.LIGHTNING);
                ElementalArsenal.LOGGER.info(stack.getName().getString());
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, entity.getWorld());
                lightning.setPosition(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ());
                lightning.setCosmetic(true);
                entity.getWorld().spawnEntity(lightning);
                return;
            }

            if (fallDistance >= 50f) {
                item.setType(stack, ElementalType.AIR);
                ElementalArsenal.LOGGER.info(stack.getName().getString());
                if (!entity.getWorld().isClient) {
                    entity.getWorld().playSound(null,
                            entity.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS);
                }
            }
        }
    }
}
