package com.starwars.entity.client;

import com.starwars.entity.custom.EwokEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EwokRenderer extends GeoEntityRenderer<EwokEntity> {
    public EwokRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EwokModel());
    }
}
