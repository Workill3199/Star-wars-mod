package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.EwokEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EwokModel extends GeoModel<EwokEntity> {
    @Override
    public Identifier getModelResource(EwokEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/ewok.geo.json");
    }

    @Override
    public Identifier getTextureResource(EwokEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/ewok.png");
    }

    @Override
    public Identifier getAnimationResource(EwokEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/ewok.animation.json");
    }
}
