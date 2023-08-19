package com.hideout.elementalarsenal.entity;

import com.hideout.elementalarsenal.mixin.TridentEntityAccessor;
import com.hideout.elementalarsenal.util.ElementalType;
import com.hideout.elementalarsenal.util.ElementalUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ElementalTridentEntity extends TridentEntity {
    public static final TrackedData<Integer> TYPE = DataTracker.registerData(ElementalTridentEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public ElementalTridentEntity(EntityType<? extends TridentEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setAttributes(ItemStack stack) {
        this.setTridentStack(stack.copy());
        this.dataTracker.set(TridentEntityAccessor.getLoyalty(), (byte) EnchantmentHelper.getLoyalty(stack));
        this.dataTracker.set(TridentEntityAccessor.getEnchanted(), stack.hasGlint());
        TridentEntityAccessor accessor = (TridentEntityAccessor) this;
        this.dataTracker.set(TYPE, ElementalUtils.getType(accessor.getTridentStack()).getId());
    }

    public ElementalType getElementalType() {
        return this.dataTracker.containsKey(TYPE) ? ElementalType.fromId(this.dataTracker.get(TYPE)) : ElementalType.BLANK;
    }

    public void setTridentStack(ItemStack copy) {
        ((TridentEntityAccessor) this).setTridentStack(copy);
    }

    protected void setDealtDamage() {
        ((TridentEntityAccessor) this).setDealtDamage(true);
    }

    protected boolean hasDealtDamage() {
        return ((TridentEntityAccessor) this).hasDealtDamage();
    }
}
