package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.item.custom.interfaces.IMultiElementItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.SmithingTransformRecipe;
import net.minecraft.recipe.SmithingTrimRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        ElementalType[] types = getAvailableTypes(stack);
        if (types.length > 0) {
            if (!Screen.hasShiftDown()) {
                tooltip.add(Text.literal("Press SHIFT to view elements").formatted(Formatting.YELLOW));
            } else {
                for (ElementalType type : types) {
                    tooltip.add(type.toFormattedText());
                }
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal(getAppendedName(stack))
                .setStyle(getType(stack).getStyle());
    }

    @Override
    public void setType(ItemStack stack, ElementalType type) {
        stack.getOrCreateNbt().putInt(TYPE, type.getId());
    }

    @Override
    public ElementalType getType(ItemStack stack) {
        return ElementalType.fromId(stack.getOrCreateNbt().getInt(TYPE));
    }

    @Override
    public String getAppendedName(ItemStack stack) {
        return getType(stack).toString() + " " + super.getName(stack).getString();
    }

    @Override
    public ElementalType[] getAvailableTypes(ItemStack stack) {
        return Arrays.stream(stack.getOrCreateNbt().getIntArray(AVAILABLE_TYPES)).mapToObj(ElementalType::fromId).toArray(ElementalType[]::new);
    }

    @Override
    public void addType(ItemStack stack, ElementalType type) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (getAvailableTypes(stack).length == 0) {
            nbt.putIntArray(AVAILABLE_TYPES, new ArrayList<>(List.of(0)));
        }

        ArrayList<Integer> available = new ArrayList<>(Arrays.stream(getAvailableTypes(stack))
                .map(ElementalType::getId).toList()); // Java array moment

        if (available.contains(type.getId())) return;

        available.add(type.getId());
        Collections.sort(available); // Sort for consistency
        nbt.putIntArray(AVAILABLE_TYPES, available);
    }
}
