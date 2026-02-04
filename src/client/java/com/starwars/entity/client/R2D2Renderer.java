package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.R2D2Entity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class R2D2Renderer extends GeoEntityRenderer<R2D2Entity> {
    public R2D2Renderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new R2D2Model());
    }

    @Override
    public void render(R2D2Entity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
