package com.starwars.screen;

import com.starwars.StarWarsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<HyperforgeScreenHandler> HYPERFORGE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(StarWarsMod.MOD_ID, "hyperforge"),
                    new ScreenHandlerType<>(HyperforgeScreenHandler::new, FeatureSet.empty()));

    public static final ScreenHandlerType<LightsaberForgeScreenHandler> LIGHTSABER_FORGE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(StarWarsMod.MOD_ID, "lightsaber_forge_screen_handler"),
                    new ScreenHandlerType<>(LightsaberForgeScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<CircuitTableScreenHandler> CIRCUIT_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(StarWarsMod.MOD_ID, "circuit_table_screen_handler"),
                    new ScreenHandlerType<>(CircuitTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreenHandlers() {
        StarWarsMod.LOGGER.info("Registering Screen Handlers for " + StarWarsMod.MOD_ID);
    }
}
