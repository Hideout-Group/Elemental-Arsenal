package com.hideout.elementalarsenal.effect;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModStatusEffects {
    private static final ArrayList<StatusEffect> EFFECTS = new ArrayList<>();
    public static StatusEffect ELECTRIFIED = registerEffect("electrified", new ElectrifiedStatusEffect());
    public static StatusEffect RENDER_LARGE = registerEffect("render_large", new RenderLargeStatusEffect());

    public static void registerStatusEffects() {

    }

    public static boolean hasModEffect(LivingEntity entity) {
        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            if (EFFECTS.contains(effect.getEffectType()))
                return true;
        }
        return false;
    }

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        EFFECTS.add(effect);
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ElementalArsenal.MOD_ID, name), effect);
    }
}
