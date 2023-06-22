package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.item.custom.util.IElementalItem;
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

public class ElementalSwordItem extends SwordItem implements IElementalItem {
    public ElementalSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("type", 0);
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient || hand.equals(Hand.OFF_HAND)) {
            return super.use(world, user, hand);
        }

        ItemStack stack = user.getMainHandStack();

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("type", (nbt.getInt("type") + 1) % 7);

        return super.use(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal(getAppendedName(stack))
                .formatted(ElementalType.getFormattings(getType(stack)));
    }

    @Override
    public ElementalType getType(ItemStack stack) {
        return ElementalType.fromId(stack.getOrCreateNbt().getInt("type"));
    }

    @Override
    public String getAppendedName(ItemStack stack) {
        return ElementalType.toCasedString(getType(stack)) + " " + super.getName(stack).getString();
    }
}
