package com.example.pikachu_ziey;


import com.example.pikachu_ziey.block.ModBlocks;
import com.example.pikachu_ziey.item.ModItemGroups;
import com.example.pikachu_ziey.item.ModItems;
import com.example.pikachu_ziey.teleportbook.networking.TeleportBookNetworking;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pikachu_ziey implements ModInitializer {
	public static final String MOD_ID = "pikachu_ziey";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();
		ModItemGroups.registerGroups();
		ModBlocks.registerModBlocks();
		TeleportBookNetworking.register();
		new com.example.pikachu_ziey.Player.PiggyBackMod().onInitialize();
		LOGGER.info("Hello Fabric world!");
	}
}