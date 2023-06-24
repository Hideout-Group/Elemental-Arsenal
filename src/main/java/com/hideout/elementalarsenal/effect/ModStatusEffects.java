package com.hideout.elementalarsenal.effect;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static StatusEffect ELECTRIFIED = registerEffect("electrified", new ElectrifiedStatusEffect());
    public static void registerStatusEffects() {

    }

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ElementalArsenal.MOD_ID, name), effect);
    }
}
