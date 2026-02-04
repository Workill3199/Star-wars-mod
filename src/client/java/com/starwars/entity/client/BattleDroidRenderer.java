package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.BattleDroidEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BattleDroidRenderer extends GeoEntityRenderer<BattleDroidEntity> {
    public BattleDroidRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BattleDroidModel());
    }

    @Override
    public Identifier getTextureLocation(BattleDroidEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/battle_droid.png");
    }
}
