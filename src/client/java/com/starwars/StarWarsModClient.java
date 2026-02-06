package com.starwars;

import com.starwars.entity.ModEntities;
import com.starwars.entity.client.*;
import com.starwars.item.ModItems;
import com.starwars.item.client.BlasterRenderer;
import com.starwars.item.client.LightsaberRenderer;
import com.starwars.item.custom.LightsaberItem;
import com.starwars.screen.CircuitTableScreen;
import com.starwars.screen.HyperforgeScreen;
import com.starwars.screen.LightsaberForgeScreen;
import com.starwars.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class StarWarsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LIGHTSABER, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
			if (LightsaberItem.renderer == null) {
				LightsaberItem.renderer = new LightsaberRenderer();
			}
			((LightsaberRenderer)LightsaberItem.renderer).render(stack, mode, matrices, vertexConsumers, light, overlay);
		});

        HandledScreens.register(ModScreenHandlers.HYPERFORGE_SCREEN_HANDLER, HyperforgeScreen::new);
        HandledScreens.register(ModScreenHandlers.LIGHTSABER_FORGE_SCREEN_HANDLER, LightsaberForgeScreen::new);
        HandledScreens.register(ModScreenHandlers.CIRCUIT_TABLE_SCREEN_HANDLER, CircuitTableScreen::new);

        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.BLASTER, new BlasterRenderer()::render);

        EntityRendererRegistry.register(ModEntities.R2D2, R2D2Renderer::new);
        EntityRendererRegistry.register(ModEntities.BATTLE_DROID, BattleDroidRenderer::new);
        EntityRendererRegistry.register(ModEntities.STORMTROOPER, StormtrooperRenderer::new);
        EntityRendererRegistry.register(ModEntities.C3PO, C3PORenderer::new);
        EntityRendererRegistry.register(ModEntities.JAWA, JawaRenderer::new);
        EntityRendererRegistry.register(ModEntities.EWOK, EwokRenderer::new);
        EntityRendererRegistry.register(ModEntities.JEDI_MASTER, com.starwars.entity.client.JediMasterRenderer::new);
        EntityRendererRegistry.register(ModEntities.LASER_PROJECTILE, LaserProjectileRenderer::new);
    }
}