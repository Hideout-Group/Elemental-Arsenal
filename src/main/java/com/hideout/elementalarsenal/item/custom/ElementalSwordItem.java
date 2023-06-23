package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.item.custom.interfaces.IMultiElementItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElementalSwordItem extends SwordItem implements IMultiElementItem {
    public ElementalSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        addType(stack, ElementalType.BLANK);
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient || hand.equals(Hand.OFF_HAND)) {
            return super.use(world, user, hand);
        }

        ItemStack stack = user.getMainHandStack();
        ElementalType type = getType(stack);

        return super.use(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal(getAppendedName(stack))
                .setStyle(ElementalType.getStyle(getType(stack)));
    }

    @Override
    public void setType(ItemStack stack, ElementalType type) {
        stack.getOrCreateNbt().putInt(TYPE, ElementalType.getId(type));
    }

    @Override
    public ElementalType getType(ItemStack stack) {
        return ElementalType.fromId(stack.getOrCreateNbt().getInt(TYPE));
    }

    @Override
    public String getAppendedName(ItemStack stack) {
        return ElementalType.toCasedString(getType(stack)) + " " + super.getName(stack).getString();
    }

    @Override
    public ElementalType[] getAvailableTypes(ItemStack stack) {
        return Arrays.stream(stack.getOrCreateNbt().getIntArray(AVAILABLE_TYPES)).mapToObj(ElementalType::fromId).toArray(ElementalType[]::new);
    }

    @Override
    public void updateTypes(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!Arrays.stream(getAvailableTypes(stack)).toList().contains(ElementalType.fromId(nbt.getInt(TYPE)))) {
            addType(stack, nbt.getInt(TYPE));
        }
    }

    @Override
    public boolean incrementType(ItemStack stack) {
        int length = getAvailableTypes(stack).length;
        if (length == 0) return false;

        NbtCompound nbt = stack.getOrCreateNbt();
        int index = getIndexOfType(stack, nbt.getInt(TYPE));
        int newIndex = (index + 1) % length;
        setType(stack, getAvailableTypes(stack)[newIndex]);
        return true;
    }

    @Override
    public boolean decrementType(ItemStack stack) {
        int length = getAvailableTypes(stack).length;
        if (length == 0) return false;

        NbtCompound nbt = stack.getOrCreateNbt();
        int index = getIndexOfType(stack, nbt.getInt(TYPE));
        int newIndex = (index + length - 1) % length;
        setType(stack, getAvailableTypes(stack)[newIndex]);

        return true;
    }

    @Override
    public void addType(ItemStack stack, ElementalType type) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (getAvailableTypes(stack).length == 0) {
            nbt.putIntArray(AVAILABLE_TYPES, new ArrayList<>(List.of(0)));
        }

        ArrayList<Integer> available = new ArrayList<>(Arrays.stream(getAvailableTypes(stack))
                .map(ElementalType::getId).toList()); // Java array moment

        if (available.contains(ElementalType.getId(type))) return;

        available.add(ElementalType.getId(type));
        Collections.sort(available); // Sort for consistency
        nbt.putIntArray(AVAILABLE_TYPES, available);
    }
}
