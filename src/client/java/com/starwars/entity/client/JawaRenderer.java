package com.starwars.entity.client;

import com.starwars.entity.custom.JawaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JawaRenderer extends GeoEntityRenderer<JawaEntity> {
    public JawaRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new JawaModel());
    }
}
