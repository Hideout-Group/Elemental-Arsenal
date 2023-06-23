package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.custom.util.IMultiElementItem;
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
    private static final String TYPE = "type";
    private static final String AVAILABLE_TYPES = "available_types";
    public ElementalSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putIntArray(AVAILABLE_TYPES, new int[] {0});
        return stack;
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        NbtCompound nbt = stack.getOrCreateNbt();
        addType(stack, nbt.getInt(TYPE));
        System.out.println("Crafted");
        super.onCraft(stack, world, player);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient || hand.equals(Hand.OFF_HAND)) {
            return super.use(world, user, hand);
        }

        ItemStack stack = user.getMainHandStack();

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(TYPE, ElementalType.getId(getAvailableTypes(stack)[(getIndexOfType(stack, nbt.getInt(TYPE)) + 1) % getAvailableTypes(stack).length]));
        System.out.println(Arrays.toString(getAvailableTypes(stack)));

        return super.use(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal(getAppendedName(stack))
                .setStyle(ElementalType.getStyle(getType(stack)));
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

    private void addType(ItemStack stack, int type) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.getIntArray(AVAILABLE_TYPES).length == 0) {
            nbt.putIntArray(AVAILABLE_TYPES, new ArrayList<>(List.of(0)));
        }

        ArrayList<Integer> available = new ArrayList<>(Arrays.asList(Arrays.stream(nbt.getIntArray(AVAILABLE_TYPES))
                .boxed().toArray(Integer[]::new))); // Java array moment
        if (available.contains(type)) return;
        available.add(type);
        Collections.sort(available); // Sort for OCD reasons
        nbt.putIntArray(AVAILABLE_TYPES, available);
    }

    private void addType(ItemStack stack, ElementalType type) {
        addType(stack, ElementalType.getId(type));
    }

    private int getIndexOfType(ItemStack stack, int type) {
        System.out.println(type);
        var available = Arrays.stream(getAvailableTypes(stack)).map(ElementalType::getId).toList();
        if (!available.contains(type)) {
            throw new IllegalArgumentException("There is no type of that index");
        } else {
            return available.indexOf(type);
        }
    }

    private int getIndexOfType(ItemStack stack, ElementalType type) {
        return getIndexOfType(stack, ElementalType.getId(type));
    }
}
