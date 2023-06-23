package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.custom.interfaces.IElementalItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ElementalGemItem extends Item implements IElementalItem {
    public ElementalGemItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (hand.equals(Hand.OFF_HAND)) return super.useOnEntity(stack, user, entity, hand);
        ItemStack handItem = user.getMainHandStack(); // I don't know why I had to do this
        NbtCompound nbt = handItem.getOrCreateNbt();

        if (nbt.getInt(TYPE) != ElementalType.getId(ElementalType.BLANK)) return super.useOnEntity(stack, user, entity, hand);
        if (entity instanceof AnimalEntity)  {
            nbt.putInt(TYPE, ElementalType.getId(ElementalType.NATURE));
            ElementalArsenal.LOGGER.info(ElementalType.toCasedString(ElementalType.fromId(nbt.getInt(TYPE))));
            if (!entity.getWorld().isClient) {
                entity.getWorld().playSound(null,
                        entity.getBlockPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS);
            }
        }

        return super.useOnEntity(stack, user, entity, hand);
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
}
