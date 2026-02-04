package software.bernie.geckolib.cache.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.resource.GeoGlowingTextureMeta;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1084;
import net.minecraft.class_156;
import net.minecraft.class_1921;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_4573;
import net.minecraft.class_4668;
import net.minecraft.class_757;

/**
 * Texture object type responsible for GeckoLib's emissive render textures
 *
 * @see <a href="https://github.com/bernie-g/geckolib/wiki/Emissive-Textures-Glow-Layer">GeckoLib Wiki - Glow Layers</a>
 */
public class AutoGlowingTexture extends GeoAbstractTexture {
	private static final class_4668.class_5942 SHADER_STATE = new class_4668.class_5942(class_757::method_42595);
	private static final class_4668.class_4685 TRANSPARENCY_STATE = new class_4668.class_4685("translucent_transparency", () -> {
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.class_4535.SRC_ALPHA, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA, GlStateManager.class_4535.ONE, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
	}, () -> {
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	});
	private static final class_4668.class_4686 WRITE_MASK = new class_4668.class_4686(true, true);
	private static final BiFunction<class_2960, Boolean, class_1921> GLOWING_RENDER_TYPE = class_156.method_34865((texture, isGlowing) -> {
		class_4668.class_4683 textureState = new class_4668.class_4683(texture, false, false);

		return class_1921.method_24049("geo_glowing_layer", class_290.field_1580, class_293.class_5596.field_27382, 256, false, true,
				class_1921.class_4688.method_23598()
						.method_34578(SHADER_STATE)
						.method_34577(textureState)
						.method_23615(TRANSPARENCY_STATE)
						.method_23611(new class_4668.class_4679(true))
						.method_23616(WRITE_MASK).method_23617(isGlowing));
	});
	/**
	 * @deprecated Use {@link #GLOWING_RENDER_TYPE}
	 */
	@Deprecated(forRemoval = true)
	private static final Function<class_2960, class_1921> RENDER_TYPE_FUNCTION = class_156.method_34866(texture -> GLOWING_RENDER_TYPE.apply(texture, false));
	private static final String APPENDIX = "_glowmask";
	/**
	 * Set to true <u><b>IN DEV</b></u> to have GeckoLib print out the base texture and generated glowlayer textures to the base game directory (./run)
	 */
	public static boolean PRINT_DEBUG_IMAGES = false;

	protected final class_2960 textureBase;
	protected final class_2960 glowLayer;

	public AutoGlowingTexture(class_2960 originalLocation, class_2960 location) {
		this.textureBase = originalLocation;
		this.glowLayer = location;
	}

	/**
	 * Get the emissive resource equivalent of the input resource path
	 * <p>
	 * Additionally prepares the texture manager for the missing texture if the resource is not present
	 *
	 * @return The glowlayer resourcepath for the provided input path
	 */
	public static class_2960 getEmissiveResource(class_2960 baseResource) {
		class_2960 path = appendToPath(baseResource, APPENDIX);

		generateTexture(path, textureManager -> textureManager.method_4616(path, new AutoGlowingTexture(baseResource, path)));

		return path;
	}

	/**
	 * Generates the glow layer {@link class_1011} and appropriately modifies the base texture for use in glow render layers
	 */
	@Nullable
	@Override
	protected class_4573 loadTexture(class_3300 resourceManager, class_310 mc) throws IOException {
		class_1044 originalTexture;

		try {
			originalTexture = mc.method_5385(() -> mc.method_1531().method_4619(this.textureBase)).get();
		}
		catch (InterruptedException | ExecutionException e) {
			throw new IOException("Failed to load original texture: " + this.textureBase, e);
		}

		class_3298 textureBaseResource = resourceManager.method_14486(this.textureBase).get();
		class_1011 baseImage = originalTexture instanceof class_1043 dynamicTexture ?
				dynamicTexture.method_4525() : class_1011.method_4309(textureBaseResource.method_14482());
		class_1011 glowImage = null;
		Optional<class_1084> textureBaseMeta = textureBaseResource.method_14481().method_43041(class_1084.field_5344);
		boolean blur = textureBaseMeta.isPresent() && textureBaseMeta.get().method_4696();
		boolean clamp = textureBaseMeta.isPresent() && textureBaseMeta.get().method_4697();

		try {
			Optional<class_3298> glowLayerResource = resourceManager.method_14486(this.glowLayer);
			GeoGlowingTextureMeta glowLayerMeta = null;

			if (glowLayerResource.isPresent()) {
				glowImage = class_1011.method_4309(glowLayerResource.get().method_14482());
				glowLayerMeta = GeoGlowingTextureMeta.fromExistingImage(glowImage);
			}
			else {
				Optional<GeoGlowingTextureMeta> meta = textureBaseResource.method_14481().method_43041(GeoGlowingTextureMeta.DESERIALIZER);

				if (meta.isPresent()) {
					glowLayerMeta = meta.get();
					glowImage = new class_1011(baseImage.method_4307(), baseImage.method_4323(), true);
				}
			}

			if (glowLayerMeta != null) {
				glowLayerMeta.createImageMask(baseImage, glowImage);

				if (PRINT_DEBUG_IMAGES && GeckoLibServices.PLATFORM.isDevelopmentEnvironment()) {
					printDebugImageToDisk(this.textureBase, baseImage);
					printDebugImageToDisk(this.glowLayer, glowImage);
				}
			}
		}
		catch (IOException e) {
			GeckoLibConstants.LOGGER.warn("Resource failed to open for glowlayer meta: {}", this.glowLayer, e);
		}

		class_1011 mask = glowImage;

		if (mask == null)
			return null;

		return () -> {
			uploadSimple(method_4624(), mask, blur, clamp);

			if (originalTexture instanceof class_1043 dynamicTexture) {
				dynamicTexture.method_4524();
			}
			else {
				uploadSimple(originalTexture.method_4624(), baseImage, blur, clamp);
			}
		};
	}

	/**
	 * Return a cached instance of the RenderType for the given texture for AutoGlowingGeoLayer rendering
	 *
	 * @param texture The texture of the resource to apply a glow layer to
	 */
	public static class_1921 getRenderType(class_2960 texture) {
		return GLOWING_RENDER_TYPE.apply(getEmissiveResource(texture), false);
	}

	/**
	 * Return a cached instance of the RenderType for the given texture for AutoGlowingGeoLayer rendering, while the entity has an outline
	 *
	 * @param texture The texture of the resource to apply a glow layer to
	 */
	public static class_1921 getOutlineRenderType(class_2960 texture) {
		return GLOWING_RENDER_TYPE.apply(getEmissiveResource(texture), true);
	}
}
