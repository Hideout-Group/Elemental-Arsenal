package com.hideout.elementalarsenal.item.custom.util;

import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Helper class for getting the tooltips for each individual MultiElementItem. Not ideal.
 */
public class ModTooltipHelper {


    public static class Sword {
        public static List<MutableText> getTooltipForType(ElementalType type) {
            switch (type) {
                case BLANK -> { return BLANK; }
                case AIR -> { return AIR; }
                case EARTH -> { return EARTH; }
                case WATER -> { return WATER; }
                case FIRE -> { return FIRE; }
                case LIGHTNING -> { return LIGHTNING; }
                case NATURE -> { return NATURE; }
                case ICE -> { return ICE; }
            }

            return List.of();
        }

        private static final List<MutableText> BLANK = List.of(
                Text.literal("This item has yet to awaken its power")
        );
        private static final List<MutableText> AIR = List.of(
                Text.literal("Use: Flings you backwards at speeds unknown. Different behaviour when used on ground"),
                Text.literal("Sneak + Use: Blows opponent backwards"),
                Text.literal("Use while gliding: Propels you forward with the might of the wind"),
                Text.literal("Passive: Immunity to all fall damage. Crouch to pull items towards you"),
                Text.literal("On Hit: TBD")

        );
        private static final List<MutableText> EARTH = List.of(
                Text.literal("Use: TBD"),
                Text.literal("Sneak + Use: TBD"),
                Text.literal("Passive: You are imbued with the might of the Earth. You take less damage"),
                Text.literal("On Hit: TBD")

        );;
        private static final List<MutableText> WATER = List.of(
                Text.literal("Use: TBD"),
                Text.literal("Sneak + Use: TBD"),
                Text.literal("Passive: You are one with the water"),
                Text.literal("On Hit: Water brings you life")
        );;
        private static final List<MutableText> FIRE = List.of(
                Text.literal("Use: TBD"),
                Text.literal("Sneak + Use: TBD"),
                Text.literal("Passive: Fire no longer burns. It welcomes you"),
                Text.literal("On Hit: TBD")
        );;
        private static final List<MutableText> LIGHTNING = List.of(
                Text.literal("Use: Calls down thunder from above"),
                Text.literal("Sneak + Use: TBD"),
                Text.literal("Passive: You move like lightning itself"),
                Text.literal("On Hit: Your enemies are electrified")
        );;
        private static final List<MutableText> NATURE = List.of(
                Text.literal("Use: TBD"),
                Text.literal("Sneak + Use: TBD"),
                Text.literal("Passive: Sneak to bring life to crops and animals around you"),
                Text.literal("On Hit: Calls nearby wildlife to assist you")
        );;
        private static final List<MutableText> ICE = List.of(
                Text.literal("Use: TBD"),
                Text.literal("Sneak + Use: TBD"),
                Text.literal("Passive: Water freezes at your touch"),
                Text.literal("On Hit: Enemies are slowed")
        );;

    }
}
