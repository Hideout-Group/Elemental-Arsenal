package com.hideout.elementalarsenal.item.custom.interfaces;

import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public interface IMultiElementItem extends IElementalItem {
    String AVAILABLE_TYPES = "elementalarsenal:available_types";
    ElementalType[] getAvailableTypes(ItemStack stack);
    void addType(ItemStack stack, ElementalType type);
    default void addType(ItemStack stack, int type) {addType(stack, ElementalType.fromId(type));}

    default int getIndexOfType(ItemStack stack, int type) {
        System.out.println(type);
        var available = Arrays.stream(getAvailableTypes(stack)).map(ElementalType::getId).toList();
        if (!available.contains(type)) {
            throw new IllegalArgumentException("There is no type of that index");
        } else {
            return available.indexOf(type);
        }
    }

    default int getIndexOfType(ItemStack stack, ElementalType type) {
        return getIndexOfType(stack, ElementalType.getId(type));
    }

    void updateTypes(ItemStack stack);
    boolean incrementType(ItemStack stack);
    boolean decrementType(ItemStack stack);
}
