package software.bernie.geckolib.renderer.layer;

import net.minecraft.class_1297;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.util.ClientUtil;

/**
 * {@link GeoRenderLayer} for rendering the auto-generated glowlayer functionality implemented by Geckolib using the <i>_glowing</i> appendixed texture files
 *
 * @see <a href="https://github.com/bernie-g/geckolib/wiki/Emissive-Textures-Glow-Layer">GeckoLib Wiki - Glow Layers</a>
 */
public class AutoGlowingGeoLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
	public AutoGlowingGeoLayer(GeoRenderer<T> renderer) {
		super(renderer);
	}

	/**
	 * Get the render type to use for this glowlayer renderer
	 * <p>
	 * Uses a custom RenderType similar to {@link class_1921#method_23026(class_2960)} by default, which may not be ideal in all circumstances
	 * @deprecated Use {@link #getRenderType(GeoAnimatable, class_4597)}
	 */
	@Deprecated(forRemoval = true)
	protected class_1921 getRenderType(T animatable) {
		return getRenderType(animatable, null);
	}

	/**
	 * Get the render type to use for this glowlayer renderer, or null if the layer should not render
	 * <p>
	 * Uses a custom RenderType similar to {@link class_1921#method_23026(class_2960)} by default, which may not be ideal in all circumstances.<br>
	 * Automatically accounts for entity states like invisibility and glowing
	 *
	 * @param bufferSource Nullable until {@link #getRenderType(GeoAnimatable)} is removed for backward compatibility
	 */
	@Nullable
	protected class_1921 getRenderType(T animatable, @Nullable class_4597 bufferSource) {
		if (!(animatable instanceof class_1297 entity))
			return AutoGlowingTexture.getRenderType(getTextureResource(animatable));

		boolean invisible = entity.method_5767();
		class_2960 texture = AutoGlowingTexture.getEmissiveResource(getTextureResource(animatable));

		if (invisible && !entity.method_5756(ClientUtil.getClientPlayer()))
			return class_1921.method_29379(texture);

		if (class_310.method_1551().method_27022(entity)) {
			if (invisible)
				return class_1921.method_23287(texture);

			return AutoGlowingTexture.getOutlineRenderType(getTextureResource(animatable));
		}

		return invisible ? null : AutoGlowingTexture.getRenderType(getTextureResource(animatable));
	}

	/**
	 * This is the method that is actually called by the render for your render layer to function
	 * <p>
	 * This is called <i>after</i> the animatable has been rendered, but before supplementary rendering like nametags
	 */
	@Override
	public void render(class_4587 poseStack, T animatable, BakedGeoModel bakedModel, @Nullable class_1921 renderType, class_4597 bufferSource, @Nullable class_4588 buffer, float partialTick, int packedLight, int packedOverlay) {
		renderType = getRenderType(animatable);

		if (renderType != null) {
			getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, renderType,
					bufferSource.getBuffer(renderType), partialTick, 15728640, packedOverlay,
					getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt());
		}
	}
}
