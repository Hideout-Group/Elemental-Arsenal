package com.hideout.elementalarsenal.sound;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent AIR_PUSH = registerSoundEvent("air_push");
    public static void registerSoundEvents() {

    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ElementalArsenal.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
