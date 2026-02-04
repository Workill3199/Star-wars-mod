package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.R2D2Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class R2D2Model extends GeoModel<R2D2Entity> {
    @Override
    public Identifier getModelResource(R2D2Entity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/r2d2.geo.json");
    }

    @Override
    public Identifier getTextureResource(R2D2Entity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/r2d2.png");
    }

    @Override
    public Identifier getAnimationResource(R2D2Entity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/r2d2.animation.json");
    }
}
