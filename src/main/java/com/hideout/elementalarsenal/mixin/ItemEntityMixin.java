package com.hideout.elementalarsenal.mixin;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(at = @At("HEAD"), method = "damage")
    public void injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity entity = (ItemEntity)(Object)this;
        if (entity.getEntityWorld().isClient) return;
        if (entity.getStack().isOf(ModItems.ELEMENTAL_GEM)) {
            if (source.isIn(DamageTypeTags.IS_FIRE)){
                ElementalArsenal.LOGGER.info("hey hey, its pyro time!");
                entity.getEntityWorld().setBlockState(entity.getBlockPos(), Blocks.AIR.getDefaultState());
            }
        }
    }
}
