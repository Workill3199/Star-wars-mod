package software.bernie.geckolib.renderer.layer;

import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

/**
 * Render layer base class for rendering additional layers of effects or textures over an existing model at runtime
 * <p>
 * Contains the base boilerplate and helper code for various render layer features
 */
public abstract class GeoRenderLayer<T extends GeoAnimatable> {
	protected final GeoRenderer<T> renderer;

	public GeoRenderLayer(GeoRenderer<T> entityRendererIn) {
		this.renderer = entityRendererIn;
	}

	/**
	 * Get the {@link GeoModel} currently being rendered
	 */
	public GeoModel<T> getGeoModel() {
		return this.renderer.getGeoModel();
	}

	/**
	 * Gets the {@link BakedGeoModel} instance that is currently being used.
	 * This can be directly used for re-rendering
	 */
	public BakedGeoModel getDefaultBakedModel(T animatable) {
		return getGeoModel().getBakedModel(getGeoModel().getModelResource(animatable));
	}

	/**
	 * Get the renderer responsible for the current render operation
	 */
	public GeoRenderer<T> getRenderer() {
		return this.renderer;
	}

	/**
	 * Get the texture resource path for the given {@link GeoAnimatable}.<br>
	 * By default, falls back to {@link GeoModel#getTextureResource(GeoAnimatable)}
	 */
	protected class_2960 getTextureResource(T animatable) {
		return this.renderer.getTextureLocation(animatable);
	}

	/**
	 * This method is called by the {@link GeoRenderer} before rendering, immediately after {@link GeoRenderer#preRender} has been called
	 * <p>
	 * This allows for RenderLayers to perform pre-render manipulations such as hiding or showing bones
	 */
	public void preRender(class_4587 poseStack, T animatable, BakedGeoModel bakedModel, @Nullable class_1921 renderType,
						  class_4597 bufferSource, @Nullable class_4588 buffer, float partialTick,
						  int packedLight, int packedOverlay) {}

	/**
	 * This is the method that is actually called by the render for your render layer to function
	 * <p>
	 * This is called <i>after</i> the animatable has been rendered, but before supplementary rendering like nametags
	 * <p>
	 * <b><u>NOTE:</u></b> If the passed {@link class_4588 buffer} is null, then the animatable was not actually rendered (invisible, etc)
	 * and you may need to factor this in to your design
	 */
	public void render(class_4587 poseStack, T animatable, BakedGeoModel bakedModel, @Nullable class_1921 renderType,
								class_4597 bufferSource, @Nullable class_4588 buffer, float partialTick,
								int packedLight, int packedOverlay) {}

	/**
	 * This method is called by the {@link GeoRenderer} for each bone being rendered
	 * <p>
	 * This is a more expensive call, particularly if being used to render something on a different buffer
	 * It does however have the benefit of having the matrix translations and other transformations already applied from render-time
	 * It's recommended to avoid using this unless necessary
	 * <p>
	 * The {@link GeoBone} in question has already been rendered by this stage
	 * <p>
	 * If you <i>do</i> use it, and you render something that changes the {@link class_4588 buffer}, you need to reset it back to the previous buffer
	 * using {@link class_4597#getBuffer} before ending the method
	 */
	public void renderForBone(class_4587 poseStack, T animatable, GeoBone bone, class_1921 renderType,
							  class_4597 bufferSource, class_4588 buffer, float partialTick, int packedLight, int packedOverlay) {}
}