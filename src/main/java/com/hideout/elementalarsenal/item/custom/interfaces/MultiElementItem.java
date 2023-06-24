package com.hideout.elementalarsenal.item.custom.interfaces;

import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public interface MultiElementItem extends ElementalItem {
    String AVAILABLE_TYPES = "elementalarsenal:available_types";
    ElementalType[] getAvailableTypes(ItemStack stack);
    void addType(ItemStack stack, ElementalType type);
    default void addType(ItemStack stack, int type) {addType(stack, ElementalType.fromId(type));}

    default int getIndexOfType(ItemStack stack, ElementalType type) {
        var available = List.of(getAvailableTypes(stack));
        if (!available.contains(type)) {
            throw new IllegalArgumentException("There is no type of that index");
        } else {
            return available.indexOf(type);
        }
    }

    default int getIndexOfType(ItemStack stack, int type) {
        return getIndexOfType(stack, ElementalType.fromId(type));
    }

    default boolean incrementType(ItemStack stack) {
        int length = getAvailableTypes(stack).length;
        if (length == 0) return false;


        int index = getIndexOfType(stack, getType(stack));
        int newIndex = (index + 1) % length;
        setType(stack, getAvailableTypes(stack)[newIndex]);
        return true;
    }

    default boolean decrementType(ItemStack stack) {
        int length = getAvailableTypes(stack).length;
        if (length == 0) return false;


        int index = getIndexOfType(stack, getType(stack));
        int newIndex = (index + length - 1) % length;
        setType(stack, getAvailableTypes(stack)[newIndex]);

        return true;
    }

    default void updateTypes(ItemStack stack) {
        if (!Arrays.stream(getAvailableTypes(stack)).toList().contains(getType(stack))) {
            addType(stack, getType(stack));
        }
    }
}
