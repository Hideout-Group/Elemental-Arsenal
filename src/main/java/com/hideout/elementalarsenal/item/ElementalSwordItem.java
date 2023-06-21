package com.hideout.elementalarsenal.item;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ElementalSwordItem extends SwordItem {
    private boolean toggled = false;
    public ElementalSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateNbt().putInt("type", 0);
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient || hand.equals(Hand.OFF_HAND)) {
            return super.use(world, user, hand);
        }

        toggled = !toggled;

        ItemStack stack = user.getMainHandStack();

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("type", toggled ? 0 : 1);

        ElementalArsenal.LOGGER.info(nbt.get("type").asString());

        return super.use(world, user, hand);
    }
}