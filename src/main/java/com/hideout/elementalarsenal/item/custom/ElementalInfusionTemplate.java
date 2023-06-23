package com.hideout.elementalarsenal.item.custom;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.util.List;

public class ElementalInfusionTemplate extends SmithingTemplateItem {
    private static final String RED = "#E55B2C";
    private static final String BLUE = "#B3FCF6";
    public ElementalInfusionTemplate() {
        super(
                Text.literal("Elemental ").setStyle(Style.EMPTY.withColor(TextColor.parse(BLUE)))
                        .append(Text.literal("Items").setStyle(Style.EMPTY.withColor(TextColor.parse(RED)))),
                Text.literal("Elemental ").setStyle(Style.EMPTY.withColor(TextColor.parse(BLUE)))
                        .append(Text.literal("Gems").setStyle(Style.EMPTY.withColor(TextColor.parse(RED)))),
                Text.literal("Elemental ").setStyle(Style.EMPTY.withColor(TextColor.parse(BLUE)))
                        .append(Text.literal("Infusion").setStyle(Style.EMPTY.withColor(TextColor.parse(RED)))),
                Text.literal("Base"),
                Text.literal("Catalyst"),
                List.of(new Identifier("item/empty_slot_sword")),
                List.of(new Identifier(ElementalArsenal.MOD_ID, "item/empty_slot_elemental_gem")));
    }
}
