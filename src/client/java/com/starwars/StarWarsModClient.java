package com.starwars;

import com.starwars.entity.ModEntities;
import com.starwars.entity.client.BattleDroidRenderer;
import com.starwars.entity.client.LaserProjectileRenderer;
import com.starwars.entity.client.C3PORenderer;
import com.starwars.entity.client.EwokRenderer;
import com.starwars.entity.client.JawaRenderer;
import com.starwars.entity.client.R2D2Renderer;
import com.starwars.entity.client.StormtrooperRenderer;
import com.starwars.item.ModItems;
import com.starwars.item.client.BlasterRenderer;
import com.starwars.item.client.LightsaberRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import com.starwars.screen.HyperforgeScreen;
import com.starwars.screen.LightsaberForgeScreen;
import com.starwars.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class StarWarsModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.HYPERFORGE_SCREEN_HANDLER, HyperforgeScreen::new);
		HandledScreens.register(ModScreenHandlers.LIGHTSABER_FORGE_SCREEN_HANDLER, LightsaberForgeScreen::new);

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LIGHTSABER, new LightsaberRenderer()::render);
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.BLASTER, new BlasterRenderer()::render);
		EntityRendererRegistry.register(ModEntities.R2D2, R2D2Renderer::new);
		EntityRendererRegistry.register(ModEntities.BATTLE_DROID, BattleDroidRenderer::new);
        EntityRendererRegistry.register(ModEntities.STORMTROOPER, StormtrooperRenderer::new);
        EntityRendererRegistry.register(ModEntities.C3PO, C3PORenderer::new);
        EntityRendererRegistry.register(ModEntities.JAWA, JawaRenderer::new);
        EntityRendererRegistry.register(ModEntities.EWOK, EwokRenderer::new);
		EntityRendererRegistry.register(ModEntities.LASER_PROJECTILE, LaserProjectileRenderer::new);
	}
}