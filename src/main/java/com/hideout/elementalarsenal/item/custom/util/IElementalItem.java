package com.hideout.elementalarsenal.item.custom.util;

import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.item.ItemStack;

public interface IElementalItem {
    ElementalType getType(ItemStack stack);
    String getAppendedName(ItemStack stack);
}
