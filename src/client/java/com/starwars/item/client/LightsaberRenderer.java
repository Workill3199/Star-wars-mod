package com.starwars.item.client;

import com.starwars.StarWarsMod;
import com.starwars.item.custom.LightsaberItem;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
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

    @Override
    public void preRender(MatrixStack poseStack, LightsaberItem animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);

        if (this.model != null) {
            this.model.getBone("blade").ifPresent(blade -> {
                if (this.renderPerspective == ModelTransformationMode.GUI || 
                    this.renderPerspective == ModelTransformationMode.GROUND ||
                    this.renderPerspective == ModelTransformationMode.FIXED) {
                    
                    blade.setHidden(true);
                } else {
                    blade.setHidden(false);
                }
            });
        }
    }
}