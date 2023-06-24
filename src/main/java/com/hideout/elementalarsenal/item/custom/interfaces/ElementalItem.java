package com.hideout.elementalarsenal.item.custom.interfaces;

import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.item.ItemStack;

public interface ElementalItem {
    String TYPE = "elementalarsenal:type";
    void setType(ItemStack stack, ElementalType type);
    default void setType(ItemStack stack, int type) {
        setType(stack, ElementalType.fromId(type));
    }
    ElementalType getType(ItemStack stack);
    String getAppendedName(ItemStack stack);
}
