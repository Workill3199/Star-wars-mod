package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.JawaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class JawaModel extends GeoModel<JawaEntity> {
    @Override
    public Identifier getModelResource(JawaEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/jawa.geo.json");
    }

    @Override
    public Identifier getTextureResource(JawaEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/jawa.png");
    }

    @Override
    public Identifier getAnimationResource(JawaEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/jawa.animation.json");
    }
}
