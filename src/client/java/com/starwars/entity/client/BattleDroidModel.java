package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.BattleDroidEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BattleDroidModel extends GeoModel<BattleDroidEntity> {
    @Override
    public Identifier getModelResource(BattleDroidEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/battle_droid.geo.json");
    }

    @Override
    public Identifier getTextureResource(BattleDroidEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/entity/battle_droid.png");
    }

    @Override
    public Identifier getAnimationResource(BattleDroidEntity animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/battle_droid.animation.json");
    }
}
