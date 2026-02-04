package software.bernie.geckolib.event;

import net.minecraft.class_4587;
import net.minecraft.class_4597;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.service.GeckoLibEvents;

/**
 * Fabric service implementation for GeckoLib's various events
 */
public class GeckoLibEventsFabric implements GeckoLibEvents {
    /**
     * Fire the {@link GeoRenderEvent.Block.CompileRenderLayers} event
     */
    @Override
    public void fireCompileBlockRenderLayers(GeoBlockRenderer<?> renderer) {
        GeoRenderEvent.Block.CompileRenderLayers.EVENT.invoker().handle(new GeoRenderEvent.Block.CompileRenderLayers(renderer));
    }

    /**
     * Fire the {@link GeoRenderEvent.Block.Pre} event
     */
    @Override
    public boolean fireBlockPreRender(GeoBlockRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        return GeoRenderEvent.Block.Pre.EVENT.invoker().handle(new GeoRenderEvent.Block.Pre(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Block.Post} event
     */
    @Override
    public void fireBlockPostRender(GeoBlockRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        GeoRenderEvent.Block.Post.EVENT.invoker().handle(new GeoRenderEvent.Block.Post(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Armor.CompileRenderLayers} event
     */
    @Override
    public void fireCompileArmorRenderLayers(GeoArmorRenderer<?> renderer) {
        GeoRenderEvent.Armor.CompileRenderLayers.EVENT.invoker().handle(new GeoRenderEvent.Armor.CompileRenderLayers(renderer));
    }

    /**
     * Fire the {@link GeoRenderEvent.Armor.Pre} event
     */
    @Override
    public boolean fireArmorPreRender(GeoArmorRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        return GeoRenderEvent.Armor.Pre.EVENT.invoker().handle(new GeoRenderEvent.Armor.Pre(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Armor.Post} event
     */
    @Override
    public void fireArmorPostRender(GeoArmorRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        GeoRenderEvent.Armor.Post.EVENT.invoker().handle(new GeoRenderEvent.Armor.Post(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Entity.CompileRenderLayers} event
     */
    @Override
    public void fireCompileEntityRenderLayers(GeoEntityRenderer<?> renderer) {
        GeoRenderEvent.Entity.CompileRenderLayers.EVENT.invoker().handle(new GeoRenderEvent.Entity.CompileRenderLayers(renderer));
    }

    /**
     * Fire the {@link GeoRenderEvent.Entity.Pre} event
     */
    @Override
    public boolean fireEntityPreRender(GeoEntityRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        return GeoRenderEvent.Entity.Pre.EVENT.invoker().handle(new GeoRenderEvent.Entity.Pre(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Entity.Post} event
     */
    @Override
    public void fireEntityPostRender(GeoEntityRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        GeoRenderEvent.Entity.Post.EVENT.invoker().handle(new GeoRenderEvent.Entity.Post(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.ReplacedEntity.CompileRenderLayers} event
     */
    @Override
    public void fireCompileReplacedEntityRenderLayers(GeoReplacedEntityRenderer<?, ?> renderer) {
        GeoRenderEvent.ReplacedEntity.CompileRenderLayers.EVENT.invoker().handle(new GeoRenderEvent.ReplacedEntity.CompileRenderLayers(renderer));
    }

    /**
     * Fire the {@link GeoRenderEvent.ReplacedEntity.Pre} event
     */
    @Override
    public boolean fireReplacedEntityPreRender(GeoReplacedEntityRenderer<?, ?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        return GeoRenderEvent.ReplacedEntity.Pre.EVENT.invoker().handle(new GeoRenderEvent.ReplacedEntity.Pre(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.ReplacedEntity.Post} event
     */
    @Override
    public void fireReplacedEntityPostRender(GeoReplacedEntityRenderer<?, ?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        GeoRenderEvent.ReplacedEntity.Post.EVENT.invoker().handle(new GeoRenderEvent.ReplacedEntity.Post(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Item.CompileRenderLayers} event
     */
    @Override
    public void fireCompileItemRenderLayers(GeoItemRenderer<?> renderer) {
        GeoRenderEvent.Item.CompileRenderLayers.EVENT.invoker().handle(new GeoRenderEvent.Item.CompileRenderLayers(renderer));
    }

    /**
     * Fire the {@link GeoRenderEvent.Item.Pre} event
     */
    @Override
    public boolean fireItemPreRender(GeoItemRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        return GeoRenderEvent.Item.Pre.EVENT.invoker().handle(new GeoRenderEvent.Item.Pre(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Item.Post} event
     */
    @Override
    public void fireItemPostRender(GeoItemRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        GeoRenderEvent.Item.Post.EVENT.invoker().handle(new GeoRenderEvent.Item.Post(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Object.CompileRenderLayers} event
     */
    @Override
    public void fireCompileObjectRenderLayers(GeoObjectRenderer<?> renderer) {
        GeoRenderEvent.Object.CompileRenderLayers.EVENT.invoker().handle(new GeoRenderEvent.Object.CompileRenderLayers(renderer));
    }

    /**
     * Fire the {@link GeoRenderEvent.Object.Pre} event
     */
    @Override
    public boolean fireObjectPreRender(GeoObjectRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
        return GeoRenderEvent.Object.Pre.EVENT.invoker().handle(new GeoRenderEvent.Object.Pre(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }

    /**
     * Fire the {@link GeoRenderEvent.Object.Post} event
     */
    @Override
    public void fireObjectPostRender(GeoObjectRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
       GeoRenderEvent.Object.Post.EVENT.invoker().handle(new GeoRenderEvent.Object.Post(renderer, poseStack, model, bufferSource, partialTick, packedLight));
    }
}
