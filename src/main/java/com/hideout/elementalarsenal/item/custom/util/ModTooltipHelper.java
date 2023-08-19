package com.hideout.elementalarsenal.item.custom.util;

import com.google.common.collect.Maps;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;

public class ModTooltipHelper {

    private static abstract class TooltipHelper {
        private final HashMap<ElementalType, List<MutableText>> TOOLTIPS = Maps.newHashMap();

        protected TooltipHelper() {
            TOOLTIPS.put(ElementalType.BLANK, blank());
            TOOLTIPS.put(ElementalType.AIR, air());
            TOOLTIPS.put(ElementalType.EARTH, earth());
            TOOLTIPS.put(ElementalType.WATER, water());
            TOOLTIPS.put(ElementalType.FIRE, fire());
            TOOLTIPS.put(ElementalType.LIGHTNING, lightning());
            TOOLTIPS.put(ElementalType.NATURE, nature());
            TOOLTIPS.put(ElementalType.ICE, ice());
        }

        public List<MutableText> getTooltipForType(ElementalType type) {
            return TOOLTIPS.get(type);
        }

        protected abstract List<MutableText> blank();
        protected abstract List<MutableText> air();
        protected abstract List<MutableText> earth();
        protected abstract List<MutableText> water();
        protected abstract List<MutableText> fire();
        protected abstract List<MutableText> lightning();
        protected abstract List<MutableText> nature();
        protected abstract List<MutableText> ice();
    }

    public static class Trident extends TooltipHelper {
        private Trident() {
            super();
        }

        @Override
        protected List<MutableText> blank() {
            return List.of(
                    Text.literal("This item has yet to awaken its power")
            );
        }

        @Override
        protected List<MutableText> air() {
            return List.of();
        }

        @Override
        protected List<MutableText> earth() {
            return List.of();
        }

        @Override
        protected List<MutableText> water() {
            return List.of();
        }

        @Override
        protected List<MutableText> fire() {
            return List.of();
        }

        @Override
        protected List<MutableText> lightning() {
            return List.of();
        }

        @Override
        protected List<MutableText> nature() {
            return List.of();
        }

        @Override
        protected List<MutableText> ice() {
            return List.of();
        }

        public static Trident INSTANCE = new Trident();

    }

    public static class Sword extends TooltipHelper {
        private Sword() {
            super();
        }

        public static final Sword INSTANCE = new Sword();

        @Override
        protected List<MutableText> blank() {
            return List.of(
                    Text.literal("This item has yet to awaken its power")
            );
        }

        @Override
        protected List<MutableText> air() {
            return List.of(
                    Text.literal("Use: Flings you backwards at speeds unknown. Different behaviour when used on ground"),
                    Text.literal("Sneak + Use: Blows opponent backwards"),
                    Text.literal("Use while gliding: Propels you forward with the might of the wind"),
                    Text.literal("Passive: Immunity to all fall damage. Crouch to pull items towards you"),
                    Text.literal("On Hit: TBD")
            );
        }

        @Override
        protected List<MutableText> earth() {
            return List.of(
                    Text.literal("Use: TBD"),
                    Text.literal("Sneak + Use: TBD"),
                    Text.literal("Passive: You are imbued with the might of the Earth. You take less damage"),
                    Text.literal("On Hit: TBD")

            );
        }

        @Override
        protected List<MutableText> water() {
            return List.of(
                    Text.literal("Use: TBD"),
                    Text.literal("Sneak + Use: TBD"),
                    Text.literal("Passive: You are one with the water"),
                    Text.literal("On Hit: Water brings you life")
            );
        }

        @Override
        protected List<MutableText> fire() {
            return List.of(
                    Text.literal("Use: TBD"),
                    Text.literal("Sneak + Use: TBD"),
                    Text.literal("Passive: Fire no longer burns. It welcomes you"),
                    Text.literal("On Hit: TBD")
            );
        }

        @Override
        protected List<MutableText> lightning() {
            return List.of(
                    Text.literal("Use: Calls down thunder from above"),
                    Text.literal("Sneak + Use: TBD"),
                    Text.literal("Passive: You move like lightning itself"),
                    Text.literal("On Hit: Your enemies are electrified")
            );
        }

        @Override
        protected List<MutableText> nature() {
            return List.of(
                    Text.literal("Use: TBD"),
                    Text.literal("Sneak + Use: TBD"),
                    Text.literal("Passive: Sneak to bring life to crops and animals around you"),
                    Text.literal("On Hit: Calls nearby wildlife to assist you")
            );
        }

        @Override
        protected List<MutableText> ice() {
            return List.of(
                    Text.literal("Use: TBD"),
                    Text.literal("Sneak + Use: TBD"),
                    Text.literal("Passive: Water freezes at your touch"),
                    Text.literal("On Hit: Enemies are slowed")
            );
        }
    }
}
