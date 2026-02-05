package com.starwars.entity.client;

import com.starwars.entity.custom.C3POEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class C3PORenderer extends GeoEntityRenderer<C3POEntity> {
    public C3PORenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new C3POModel());
    }
}
