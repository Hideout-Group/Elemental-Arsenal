package com.hideout.elementalarsenal.util;

import net.minecraft.text.Style;
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
        TYPES.put(BLANK, new Wrapper(0, "Blank", Style.EMPTY.withColor(TextColor.parse("#DADADA"))));
        TYPES.put(AIR, new Wrapper(1, "Air", Style.EMPTY.withColor(TextColor.parse("#50BF8A"))));
        TYPES.put(EARTH, new Wrapper(2, "Earth", Style.EMPTY.withColor(TextColor.parse("#C29938"))));
        TYPES.put(WATER, new Wrapper(3, "Water", Style.EMPTY.withColor(TextColor.parse("#1CC0C9"))));
        TYPES.put(FIRE, new Wrapper(4, "Fire", Style.EMPTY.withColor(TextColor.parse("#D75D24"))));
        TYPES.put(LIGHTNING, new Wrapper(5, "Lightning", Style.EMPTY.withColor(TextColor.parse("#6E3986"))));
        TYPES.put(NATURE, new Wrapper(6, "Nature", Style.EMPTY.withColor(TextColor.parse("#1EA521"))));
        TYPES.put(ICE, new Wrapper(7, "Ice", Style.EMPTY.withColor(TextColor.parse("#A0E9E5"))));
    }

    public static int getId(ElementalType type) {
        return TYPES.get(type).ID;
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
            if (entry.getValue().NAME.equalsIgnoreCase(str)) return entry.getKey();
        }
        return null;
    }

    public static String toCasedString(ElementalType type) {
        return TYPES.get(type).NAME;
    }
    public static Style getStyle(ElementalType type) {
        return TYPES.get(type).STYLE;
    }

    private static class Wrapper {
        public final int ID;
        public final String NAME;
        public final Style STYLE;
        public Wrapper(int id, String name, Style style) {
            this.ID = id;
            this.NAME = name;
            this.STYLE = style;
        }
    }
}
