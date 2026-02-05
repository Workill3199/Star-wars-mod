package com.starwars;

import com.starwars.block.ModBlocks;
import com.starwars.entity.ModEntities;
import com.starwars.entity.custom.BattleDroidEntity;
import com.starwars.entity.custom.C3POEntity;
import com.starwars.entity.custom.JawaEntity;
import com.starwars.entity.custom.R2D2Entity;
import com.starwars.entity.custom.StormtrooperEntity;
import com.starwars.entity.custom.EwokEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import com.starwars.item.ModItems;
import com.starwars.sound.ModSounds;
import com.starwars.entity.ModBlockEntities;
import com.starwars.screen.ModScreenHandlers;
import com.starwars.util.ModLootTableModifiers;
import com.starwars.util.ModRegistries;
import com.starwars.util.ModTrades;
import com.starwars.villager.ModVillagers;
import com.starwars.component.ModDataComponentTypes;
import com.starwars.world.gen.ModWorldGeneration;
import com.starwars.world.gen.feature.ModFeatures;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarWarsMod implements ModInitializer {
	public static final String MOD_ID = "star_wars_mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Star Wars world!");
        ModDataComponentTypes.registerDataComponentTypes();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModSounds.registerSounds();
		ModVillagers.registerVillagers();
        ModTrades.registerTrades();
        ModLootTableModifiers.modifyLootTables();
        ModRegistries.registerModStuff();
        
        FabricDefaultAttributeRegistry.register(ModEntities.R2D2, R2D2Entity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BATTLE_DROID, BattleDroidEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.STORMTROOPER, StormtrooperEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.C3PO, C3POEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.JAWA, JawaEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.EWOK, EwokEntity.setAttributes());
        
        ModEntities.registerSpawnRestrictions();
        ModFeatures.registerFeatures();
		
		ModWorldGeneration.generateModWorldGen();
	}
}