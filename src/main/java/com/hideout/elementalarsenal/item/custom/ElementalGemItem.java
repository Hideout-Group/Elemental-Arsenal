package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.item.custom.util.IElementalItem;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ElementalGemItem extends Item implements IElementalItem {
    public ElementalGemItem(Settings settings) {
        super(settings);
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
