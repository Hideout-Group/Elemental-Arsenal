package com.hideout.elementalarsenal.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.*;

public abstract class ElementalUtils {
    public static final String TYPE = "elementalarsenal:type";
    public static final String AVAILABLE_TYPES = "elementalarsenal:available_types";

    public static void setType(ItemStack stack, ElementalType type) {
        stack.getOrCreateNbt().putInt(TYPE, type.getId());
    }

    public static void setType(ItemStack stack, int type) {
        setType(stack, Objects.requireNonNull(ElementalType.fromId(type)));
    }

    public static ElementalType getType(ItemStack stack) {
        return ElementalType.fromId(stack.getOrCreateNbt().getInt(TYPE));
    }

    public static String getAppendedName(ItemStack stack) {
        return getType(stack).toString() + " " + stack.getItem().getName().getString();
    }

    public static ElementalType[] getAvailableTypes(ItemStack stack) {
        return Arrays.stream(stack.getOrCreateNbt().getIntArray(AVAILABLE_TYPES)).mapToObj(ElementalType::fromId).toArray(ElementalType[]::new);
    }

    public static void setAvailableTypes(ItemStack stack, ElementalType... types) {
        stack.getOrCreateNbt().putIntArray(AVAILABLE_TYPES, Arrays.stream(types).map(ElementalType::getId).toList());
    }

    public static void setAvailableTypes(ItemStack stack, List<ElementalType> types) {
        stack.getOrCreateNbt().putIntArray(AVAILABLE_TYPES, types.stream().map(ElementalType::getId).toList());
    }

    public static void addType(ItemStack stack, ElementalType type) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (getAvailableTypes(stack).length == 0) {
            nbt.putIntArray(AVAILABLE_TYPES, new ArrayList<>(List.of(0)));
        }

        List<Integer> available = new ArrayList<>(Arrays.stream(getAvailableTypes(stack))
                .map(ElementalType::getId).toList()); // Java array moment

        if (available.contains(type.getId())) return;

        available.add(type.getId());
        Collections.sort(available); // Sort for consistency
        nbt.putIntArray(AVAILABLE_TYPES, available);
    }

    public static void addType(ItemStack stack, int type) {addType(stack, ElementalType.fromId(type));}

    public static int getIndexOfType(ItemStack stack, ElementalType type) {
        var available = List.of(getAvailableTypes(stack));
        if (!available.contains(type)) {
            throw new IllegalArgumentException("There is no type of that index");
        } else {
            return available.indexOf(type);
        }
    }

    public static int getIndexOfType(ItemStack stack, int type) {
        return getIndexOfType(stack, ElementalType.fromId(type));
    }

    public static boolean incrementType(ItemStack stack, int amount) {
        return slideType(stack, amount);
    }

    public static boolean decrementType(ItemStack stack, int amount) {
        return slideType(stack, -amount);
    }

    private static boolean slideType(ItemStack stack, int amount) {
        int length = getAvailableTypes(stack).length;
        if (length == 0) return false;

        int index = getIndexOfType(stack, getType(stack));
        int newIndex = (index + length + amount) % length;
        setType(stack, getAvailableTypes(stack)[newIndex]);
        return true;
    }

    // Force updates the available types with the current type
    public static void updateTypes(ItemStack stack) {
        if (!Arrays.stream(getAvailableTypes(stack)).toList().contains(getType(stack))) {
            addType(stack, getType(stack));
        }
    }
}
