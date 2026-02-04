package com.starwars.item.client;

import com.starwars.item.custom.BlasterItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BlasterRenderer extends GeoItemRenderer<BlasterItem> {
    public BlasterRenderer() {
        super(new BlasterModel());
    }
}
