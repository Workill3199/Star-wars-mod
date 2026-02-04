package com.starwars.item.client;

import com.starwars.StarWarsMod;
import com.starwars.item.custom.BlasterItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BlasterModel extends GeoModel<BlasterItem> {
    @Override
    public Identifier getModelResource(BlasterItem animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "geo/blaster.geo.json");
    }

    @Override
    public Identifier getTextureResource(BlasterItem animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "textures/item/blaster.png");
    }

    @Override
    public Identifier getAnimationResource(BlasterItem animatable) {
        return Identifier.of(StarWarsMod.MOD_ID, "animations/blaster.animation.json");
    }
}
