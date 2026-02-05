package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.JediMasterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class JediMasterModel extends GeoModel<JediMasterEntity> {
    @Override
    public Identifier getModelResource(JediMasterEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/stormtrooper.geo.json");
    }

    @Override
    public Identifier getTextureResource(JediMasterEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/jedi_master.png");
    }

    @Override
    public Identifier getAnimationResource(JediMasterEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/stormtrooper.animation.json");
    }
}
