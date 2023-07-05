package com.hideout.elementalarsenal.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.util.HashMap;
import java.util.Objects;

public enum ElementalType {
    BLANK(0, "element.elementalarsenal.blank",
            Style.EMPTY.withColor(0xDADADA)),
    AIR(1, "element.elementalarsenal.air",
            Style.EMPTY.withColor(0x50BF8A)),
    EARTH(2, "element.elementalarsenal.earth",
            Style.EMPTY.withColor(0xC29938)),
    WATER(3, "element.elementalarsenal.water",
            Style.EMPTY.withColor(0x1CC0C9)),
    FIRE(4, "element.elementalarsenal.fire",
            Style.EMPTY.withColor(0xD75D24)),
    LIGHTNING(5, "element.elementalarsenal.lightning",
            Style.EMPTY.withColor(0x6E3986)),
    NATURE(6, "element.elementalarsenal.nature",
            Style.EMPTY.withColor(0x1EA521)),
    ICE(7, "element.elementalarsenal.ice",
            Style.EMPTY.withColor(0xA0E9E5));

    private final int ID;
    private final String TRANSLATION_KEY;
    private final Style STYLE;
    ElementalType(int id, String translationKey, Style style) {
        ID = id;
        TRANSLATION_KEY = translationKey;
        STYLE = style;
    }

    public static ElementalType fromId(int id) {
        for (ElementalType value : values()) {
            if (value.getId() == id) return value;
        }
        return null;
    }

    public static ElementalType fromString(String str) {
        for (ElementalType value : values()) {
            if (Objects.equals(value.toString(), str)) return value;
        }
        return null;
    }

    public int getId() {
        return ID;
    }

    public String getTranslationKey() {
        return TRANSLATION_KEY;
    }
    public Style getStyle() {
        return STYLE;
    }

    public MutableText toFormattedText() {
        return Text.translatable(getTranslationKey()).setStyle(getStyle());
    }
    public String toString() {
        return toFormattedText().getString();
    }
}
