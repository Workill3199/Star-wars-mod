package com.starwars.item.client;

import com.starwars.StarWarsMod;
import com.starwars.item.custom.LightsaberItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class LightsaberModel extends GeoModel<LightsaberItem> {
    @Override
    public Identifier getModelResource(LightsaberItem animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/lightsaber.geo.json");
    }

    @Override
    public Identifier getTextureResource(LightsaberItem animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/item/lightsaber.png");
    }

    @Override
    public Identifier getAnimationResource(LightsaberItem animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/lightsaber.animation.json");
    }
}
