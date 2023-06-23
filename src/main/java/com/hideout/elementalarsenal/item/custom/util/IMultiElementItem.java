package com.hideout.elementalarsenal.item.custom.util;

import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.item.ItemStack;

public interface IMultiElementItem extends IElementalItem {
    ElementalType[] getAvailableTypes(ItemStack stack);
    void updateTypes(ItemStack stack);
}
