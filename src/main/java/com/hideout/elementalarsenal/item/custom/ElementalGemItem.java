package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.custom.util.IElementalItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ElementalGemItem extends Item implements IElementalItem {
    public ElementalGemItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient || hand.equals(Hand.OFF_HAND)) return super.useOnEntity(stack, user, entity, hand);
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.getInt("type") != ElementalType.getId(ElementalType.BLANK)) return super.useOnEntity(stack, user, entity, hand);
        if (entity instanceof AnimalEntity)  {
            nbt.putInt("type", ElementalType.getId(ElementalType.NATURE));
            ElementalArsenal.LOGGER.info(String.valueOf(nbt.getInt("type")));
        }

        return super.useOnEntity(stack, user, entity, hand);
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
