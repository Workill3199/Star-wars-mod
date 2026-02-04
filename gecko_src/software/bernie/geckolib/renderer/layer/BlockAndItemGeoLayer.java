package software.bernie.geckolib.renderer.layer;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.util.RenderUtil;

import java.util.function.BiFunction;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_811;

/**
 * {@link GeoRenderLayer} for rendering {@link class_2680 BlockStates}
 * or {@link class_1799 ItemStacks} on a given {@link GeoAnimatable}
 */
public class BlockAndItemGeoLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    protected final BiFunction<GeoBone, T, class_1799> stackForBone;
    protected final BiFunction<GeoBone, T, class_2680> blockForBone;

    public BlockAndItemGeoLayer(GeoRenderer<T> renderer) {
        this(renderer, (bone, animatable) -> null, (bone, animatable) -> null);
    }

    public BlockAndItemGeoLayer(GeoRenderer<T> renderer, BiFunction<GeoBone, T, class_1799> stackForBone, BiFunction<GeoBone, T, class_2680> blockForBone) {
        super(renderer);

        this.stackForBone = stackForBone;
        this.blockForBone = blockForBone;
    }

    /**
     * Return an ItemStack relevant to this bone for rendering, or null if no ItemStack to render
     */
    @Nullable
    protected class_1799 getStackForBone(GeoBone bone, T animatable) {
        return this.stackForBone.apply(bone, animatable);
    }

    /**
     * Return a BlockState relevant to this bone for rendering, or null if no BlockState to render
     */
    @Nullable
    protected class_2680 getBlockForBone(GeoBone bone, T animatable) {
        return this.blockForBone.apply(bone, animatable);
    }

    /**
     * Return a specific TransFormType for this {@link class_1799} render for this bone.
     */
    protected class_811 getTransformTypeForStack(GeoBone bone, class_1799 stack, T animatable) {
        return class_811.field_4315;
    }

    /**
     * This method is called by the {@link GeoRenderer} for each bone being rendered
     * <p>
     * This is a more expensive call, particularly if being used to render something on a different buffer.
     * It does however have the benefit of having the matrix translations and other transformations already applied from render-time
     * It's recommended to avoid using this unless necessary
     * <p>
     * The {@link GeoBone} in question has already been rendered by this stage
     * <p>
     * If you <i>do</i> use it, and you render something that changes the {@link class_4588 buffer}, you need to reset it back to the previous buffer
     * using {@link class_4597#getBuffer} before ending the method
     */
    @Override
    public void renderForBone(class_4587 poseStack, T animatable, GeoBone bone, class_1921 renderType, class_4597 bufferSource,
                              class_4588 buffer, float partialTick, int packedLight, int packedOverlay) {
        class_1799 stack = getStackForBone(bone, animatable);
        class_2680 blockState = getBlockForBone(bone, animatable);

        if (stack == null && blockState == null)
            return;

        poseStack.method_22903();
        RenderUtil.translateAndRotateMatrixForBone(poseStack, bone);

        if (stack != null)
            renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);

        if (blockState != null)
            renderBlockForBone(poseStack, bone, blockState, animatable, bufferSource, partialTick, packedLight, packedOverlay);

        poseStack.method_22909();
    }

    /**
     * Render the given {@link class_1799} for the provided {@link GeoBone}.
     */
    protected void renderStackForBone(class_4587 poseStack, GeoBone bone, class_1799 stack, T animatable, class_4597 bufferSource,
                                      float partialTick, int packedLight, int packedOverlay) {
        if (animatable instanceof class_1309 livingEntity) {
            class_310.method_1551().method_1480().method_23177(livingEntity, stack,
                    getTransformTypeForStack(bone, stack, animatable), false, poseStack, bufferSource, livingEntity.method_37908(),
                    packedLight, packedOverlay, livingEntity.method_5628());
        } else {
            class_310.method_1551().method_1480().method_23178(stack, getTransformTypeForStack(bone, stack, animatable),
                    packedLight, packedOverlay, poseStack, bufferSource, class_310.method_1551 ().field_1687, (int) this.renderer.getInstanceId(animatable));
        }
    }

    /**
     * Render the given {@link class_2680} for the provided {@link GeoBone}.
     */
    protected void renderBlockForBone(class_4587 poseStack, GeoBone bone, class_2680 state, T animatable, class_4597 bufferSource,
                                      float partialTick, int packedLight, int packedOverlay) {
        poseStack.method_22903();
        poseStack.method_46416(-0.25f, -0.25f, -0.25f);
        poseStack.method_22905(0.5f, 0.5f, 0.5f);
        class_310.method_1551().method_1541().method_3353(state, poseStack, bufferSource, packedLight, class_4608.field_21444);
        poseStack.method_22909();
    }
}
