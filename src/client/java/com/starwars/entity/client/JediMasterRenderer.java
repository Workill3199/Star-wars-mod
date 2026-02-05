package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.JediMasterEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JediMasterRenderer extends GeoEntityRenderer<JediMasterEntity> {
    public JediMasterRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new JediMasterModel());
    }

    @Override
    public Identifier getTextureLocation(JediMasterEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/jedi_master.png");
    }
}
