package com.starwars.entity.client;

import com.starwars.entity.custom.StormtrooperEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StormtrooperRenderer extends GeoEntityRenderer<StormtrooperEntity> {
    public StormtrooperRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new StormtrooperModel());
    }
}
