package com.hideout.elementalarsenal;

import com.hideout.elementalarsenal.effect.ModStatusEffects;
import com.hideout.elementalarsenal.event.ModEvents;
import com.hideout.elementalarsenal.item.ModItemGroups;
import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.network.ModMessages;
import com.hideout.elementalarsenal.particle.ModParticles;
import com.hideout.elementalarsenal.recipe.ModRecipes;
import com.hideout.elementalarsenal.registry.ModRegistries;
import com.hideout.elementalarsenal.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementalArsenal implements ModInitializer {
	public static final String MOD_ID = "elementalarsenal";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModEvents.registerEvents();

		ModItems.registerItems();
		ModItemGroups.registerItemGroups(); // RUN AFTER ITEMS

		ModParticles.registerParticles();

		ModRegistries.registerRegistries();
		ModRecipes.registerRecipes();

		ModStatusEffects.registerStatusEffects();
		ModSounds.registerSoundEvents();

		ModMessages.registerC2SPackets();
		LOGGER.info("Hello Fabric world!");
	}
}