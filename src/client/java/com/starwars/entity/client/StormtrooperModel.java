package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.StormtrooperEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.model.GeoModel;

public class StormtrooperModel extends GeoModel<StormtrooperEntity> {
    @Override
    public Identifier getModelResource(StormtrooperEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/stormtrooper.geo.json");
    }

    @Override
    public Identifier getTextureResource(StormtrooperEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/stormtrooper.png");
    }

    @Override
    public Identifier getAnimationResource(StormtrooperEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/stormtrooper.animation.json");
    }
}
