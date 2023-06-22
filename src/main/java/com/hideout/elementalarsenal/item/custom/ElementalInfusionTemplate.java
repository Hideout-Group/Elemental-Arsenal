package com.hideout.elementalarsenal.item.custom;

import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.util.List;

public class ElementalInfusionTemplate extends SmithingTemplateItem {
    private static final String COLOR = "#9BF593";
    public ElementalInfusionTemplate() {
        super(
                Text.literal("Elemental Items").setStyle(Style.EMPTY.withColor(TextColor.parse(COLOR))),
                Text.literal("Elemental Gems").setStyle(Style.EMPTY.withColor(TextColor.parse(COLOR))),
                Text.literal("Elemental Infusion").setStyle(Style.EMPTY.withColor(TextColor.parse(COLOR))),
                Text.literal("The base for infusion"),
                Text.literal("The catalyst for infusion"),
                List.of(new Identifier("item/empty_slot_sword")),
                List.of(new Identifier("item/empty_slot_diamond")));
    }
}
