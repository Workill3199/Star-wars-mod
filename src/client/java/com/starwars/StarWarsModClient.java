package com.starwars;

import com.starwars.entity.ModEntities;
import com.starwars.entity.client.BattleDroidRenderer;
import com.starwars.entity.client.LaserProjectileRenderer;
import com.starwars.entity.client.R2D2Renderer;
import com.starwars.item.ModItems;
import com.starwars.item.client.BlasterRenderer;
import com.starwars.item.client.LightsaberRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class StarWarsModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LIGHTSABER, new LightsaberRenderer()::render);
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.BLASTER, new BlasterRenderer()::render);
		EntityRendererRegistry.register(ModEntities.R2D2, R2D2Renderer::new);
		EntityRendererRegistry.register(ModEntities.BATTLE_DROID, BattleDroidRenderer::new);
		EntityRendererRegistry.register(ModEntities.LASER_PROJECTILE, LaserProjectileRenderer::new);
	}
}