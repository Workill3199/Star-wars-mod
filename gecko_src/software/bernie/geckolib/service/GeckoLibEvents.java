package software.bernie.geckolib.service;

import net.minecraft.class_4587;
import net.minecraft.class_4597;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.*;

/**
 * Loader-agnostic service interface for GeckoLib's various events
 */
public interface GeckoLibEvents {
    /**
     * Fire the Block.CompileRenderLayers event
     */
    void fireCompileBlockRenderLayers(GeoBlockRenderer<?> renderer);
    /**
     * Fire the Block.Pre event
     */
    boolean fireBlockPreRender(GeoBlockRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);
    /**
     * Fire the Block.Post event
     */
    void fireBlockPostRender(GeoBlockRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);

    /**
     * Fire the Armor.CompileRenderLayers event
     */
    void fireCompileArmorRenderLayers(GeoArmorRenderer<?> renderer);
    /**
     * Fire the Armor.Pre event
     */
    boolean fireArmorPreRender(GeoArmorRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);
    /**
     * Fire the Armor.Post event
     */
    void fireArmorPostRender(GeoArmorRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);

    /**
     * Fire the Entity.CompileRenderLayers event
     */
    void fireCompileEntityRenderLayers(GeoEntityRenderer<?> renderer);
    /**
     * Fire the Entity.Pre event
     */
    boolean fireEntityPreRender(GeoEntityRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);
    /**
     * Fire the Entity.Post event
     */
    void fireEntityPostRender(GeoEntityRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);

    /**
     * Fire the ReplacedEntity.CompileRenderLayers event
     */
    void fireCompileReplacedEntityRenderLayers(GeoReplacedEntityRenderer<?, ?> renderer);
    /**
     * Fire the ReplacedEntity.Pre event
     */
    boolean fireReplacedEntityPreRender(GeoReplacedEntityRenderer<?, ?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);
    /**
     * Fire the ReplacedEntity.Post event
     */
    void fireReplacedEntityPostRender(GeoReplacedEntityRenderer<?, ?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);

    /**
     * Fire the Item.CompileRenderLayers event
     */
    void fireCompileItemRenderLayers(GeoItemRenderer<?> renderer);
    /**
     * Fire the Item.Pre event
     */
    boolean fireItemPreRender(GeoItemRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);
    /**
     * Fire the Item.Post event
     */
    void fireItemPostRender(GeoItemRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);

    /**
     * Fire the Object.CompileRenderLayers event
     */
    void fireCompileObjectRenderLayers(GeoObjectRenderer<?> renderer);
    /**
     * Fire the Object.Pre event
     */
    boolean fireObjectPreRender(GeoObjectRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);
    /**
     * Fire the Object.Post event
     */
    void fireObjectPostRender(GeoObjectRenderer<?> renderer, class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight);
}
