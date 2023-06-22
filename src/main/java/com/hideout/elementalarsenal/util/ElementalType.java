package com.hideout.elementalarsenal.util;

import net.minecraft.util.Formatting;

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
        TYPES.put(BLANK, new Wrapper(0, "Blank", new Formatting[] {Formatting.WHITE}));
        TYPES.put(AIR, new Wrapper(1, "Air", new Formatting[] {Formatting.GREEN}));
        TYPES.put(EARTH, new Wrapper(2, "Earth", new Formatting[] {Formatting.GOLD}));
        TYPES.put(WATER, new Wrapper(3, "Water", new Formatting[] {Formatting.DARK_BLUE}));
        TYPES.put(FIRE, new Wrapper(4, "Fire", new Formatting[] {Formatting.RED}));
        TYPES.put(LIGHTNING, new Wrapper(5, "Lightning", new Formatting[] {Formatting.LIGHT_PURPLE}));
        TYPES.put(NATURE, new Wrapper(6, "Nature", new Formatting[] {Formatting.DARK_GREEN}));
        TYPES.put(ICE, new Wrapper(7, "Ice", new Formatting[] {Formatting.BLUE}));
    }

    public static int getId(ElementalType type) {
        return TYPES.get(type).ID;
    }

    public static ElementalType fromId(int id) {
        ElementalType type = BLANK;
        for (var entry:
             TYPES.entrySet()) {
            if (entry.getValue().ID == id) type = entry.getKey();
        }
        return type;
    }

    public static String toCasedString(ElementalType type) {
        return TYPES.get(type).NAME;
    }
    public static Formatting[] getFormattings(ElementalType type) {
        return TYPES.get(type).FORMATTINGS;
    }

    private static class Wrapper {
        public final int ID;
        public final String NAME;
        public final Formatting[] FORMATTINGS;
        public Wrapper(int id, String name, Formatting[] formattings) {
            this.ID = id;
            this.NAME = name;
            this.FORMATTINGS = formattings;
        }
    }
}
