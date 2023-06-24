package com.hideout.elementalarsenal.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.util.HashMap;

public enum ElementalType {
    BLANK,
    AIR,
    EARTH,
    WATER,
    FIRE,
    LIGHTNING,
    NATURE,
    ICE;

    private final static HashMap<ElementalType, Wrapper> TYPES = new HashMap<>();

    static {
        TYPES.put(BLANK, new Wrapper(0, "element.elementalarsenal.blank",
                Style.EMPTY.withColor(TextColor.parse("#DADADA"))));

        TYPES.put(AIR, new Wrapper(1, "element.elementalarsenal.air",
                Style.EMPTY.withColor(TextColor.parse("#50BF8A"))));

        TYPES.put(EARTH, new Wrapper(2, "element.elementalarsenal.earth",
                Style.EMPTY.withColor(TextColor.parse("#C29938"))));

        TYPES.put(WATER, new Wrapper(3, "element.elementalarsenal.water",
                Style.EMPTY.withColor(TextColor.parse("#1CC0C9"))));

        TYPES.put(FIRE, new Wrapper(4, "element.elementalarsenal.fire",
                Style.EMPTY.withColor(TextColor.parse("#D75D24"))));

        TYPES.put(LIGHTNING, new Wrapper(5, "element.elementalarsenal.lightning",
                Style.EMPTY.withColor(TextColor.parse("#6E3986"))));

        TYPES.put(NATURE, new Wrapper(6, "element.elementalarsenal.nature",
                Style.EMPTY.withColor(TextColor.parse("#1EA521"))));

        TYPES.put(ICE, new Wrapper(7, "element.elementalarsenal.ice",
                Style.EMPTY.withColor(TextColor.parse("#A0E9E5"))));
    }

    public static ElementalType fromId(int id) {
        for (var entry:
                TYPES.entrySet()) {
            if (entry.getValue().ID == id) return entry.getKey();
        }
        return null;
    }

    public static ElementalType fromString(String str) {
        for (var entry:
                TYPES.entrySet()) {
            if (Text.translatable(entry.getValue().TRANSLATION_KEY).getString().equalsIgnoreCase(str)) return entry.getKey();
        }
        return null;
    }

    public int getId() {
        return TYPES.get(this).ID;
    }

    public String getTranslationKey() {
        return TYPES.get(this).TRANSLATION_KEY;
    }
    public Style getStyle() {
        return TYPES.get(this).STYLE;
    }

    public MutableText toFormattedText() {
        return Text.translatable(getTranslationKey()).setStyle(getStyle());
    }
    public String toString() {
        return toFormattedText().getString();
    }



    private static class Wrapper {
        public final int ID;
        public final String TRANSLATION_KEY;
        public final Style STYLE;
        public Wrapper(int id, String translation_key, Style style) {
            this.ID = id;
            this.TRANSLATION_KEY = translation_key;
            this.STYLE = style;
        }
    }
}
