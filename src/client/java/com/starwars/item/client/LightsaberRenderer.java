package com.starwars.item.client;

import com.starwars.StarWarsMod;
import com.starwars.item.custom.LightsaberItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LightsaberRenderer extends GeoItemRenderer<LightsaberItem> {
    public LightsaberRenderer() {
        super(new LightsaberModel());
    }

    @Override
    public Identifier getTextureLocation(LightsaberItem animatable) {
        String color = "blue";
        ItemStack stack = this.getCurrentItemStack();
        if (stack != null && stack.getItem() instanceof LightsaberItem lightsaber) {
            color = lightsaber.getColor(stack);
        }
        return Identifier.of(StarWarsMod.MOD_ID, "textures/item/lightsaber_" + color + ".png");
    }
}
