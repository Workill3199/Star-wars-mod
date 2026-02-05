package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.C3POEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class C3POModel extends GeoModel<C3POEntity> {
    @Override
    public Identifier getModelResource(C3POEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/c3po.geo.json");
    }

    @Override
    public Identifier getTextureResource(C3POEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/c3po.png");
    }

    @Override
    public Identifier getAnimationResource(C3POEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/c3po.animation.json");
    }
}
