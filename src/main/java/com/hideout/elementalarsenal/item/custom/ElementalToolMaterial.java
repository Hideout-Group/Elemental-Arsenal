package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.item.ModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ElementalToolMaterial implements ToolMaterial {
    private ElementalToolMaterial() {};
    public static final ElementalToolMaterial INSTANCE = new ElementalToolMaterial();
    @Override
    public int getDurability() {
        return 1200;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 8.0f;
    }

    @Override
    public float getAttackDamage() {
        return 3.0f;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.ELEMENTAL_GEM);
    }
}
