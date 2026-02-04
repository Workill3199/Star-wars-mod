package software.bernie.geckolib.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.class_1011;
import net.minecraft.class_3270;
import net.minecraft.class_3518;
import net.minecraft.class_5253;

/**
 * Metadata class that stores the data for GeckoLib's {@link software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer emissive texture feature} for a given texture
 */
public class GeoGlowingTextureMeta {
	public static final class_3270<GeoGlowingTextureMeta> DESERIALIZER = new class_3270<>() {
		@Override
		public String method_14420() {
			return "glowsections";
		}

		@Override
		public GeoGlowingTextureMeta method_14421(JsonObject json) {
			List<Pixel> pixels = fromSections(class_3518.method_15292(json, "sections", null));

			if (pixels.isEmpty())
				throw new JsonParseException("Empty glowlayer sections file. Must have at least one glow section!");

			return new GeoGlowingTextureMeta(pixels);
		}

		/**
		 * Generate a {@link Pixel} collection from the "sections" array of the mcmeta file
		 */
		private List<Pixel> fromSections(@Nullable JsonArray sectionsArray) {
			if (sectionsArray == null)
				return List.of();

			List<Pixel> pixels = new ObjectArrayList<>();

			for (JsonElement element : sectionsArray) {
				if (!(element instanceof JsonObject obj))
					throw new JsonParseException("Invalid glowsections json format, expected a JsonObject, found: " + element.getClass());

				int x1 = class_3518.method_15282(obj, "x1", class_3518.method_15282(obj, "x", 0));
				int y1 = class_3518.method_15282(obj, "y1", class_3518.method_15282(obj, "y", 0));
				int x2 = class_3518.method_15282(obj, "x2", class_3518.method_15282(obj, "w", 0) + x1);
				int y2 = class_3518.method_15282(obj, "y2", class_3518.method_15282(obj, "h", 0) + y1);
				int alpha = class_3518.method_15282(obj, "alpha", class_3518.method_15282(obj, "a", 0));

				if (x1 + y1 + x2 + y2 == 0)
					throw new IllegalArgumentException("Invalid glowsections section object, section must be at least one pixel in size");

				for (int x = x1; x <= x2; x++) {
					for (int y = y1; y <= y2; y++) {
						pixels.add(new Pixel(x, y, alpha));
					}
				}
			}

			return pixels;
		}
	};

	private final List<Pixel> pixels;

	public GeoGlowingTextureMeta(List<Pixel> pixels) {
		this.pixels = pixels;
	}

	/**
	 * Generate the GlowLayer pixels list from an existing image resource, instead of using the .png.mcmeta file
	 */
	public static GeoGlowingTextureMeta fromExistingImage(class_1011 glowLayer) {
		List<Pixel> pixels = new ObjectArrayList<>();

		for (int x = 0; x < glowLayer.method_4307(); x++) {
			for (int y = 0; y < glowLayer.method_4323(); y++) {
				int color = glowLayer.method_4315(x, y); // Actually ABGR. Blame Mojang.

				if (color != 0)
					pixels.add(new Pixel(x, y, class_5253.class_8045.method_48342(color)));
			}
		}

		if (pixels.isEmpty())
			throw new IllegalStateException("Invalid glow layer texture provided, must have at least one pixel!");

		return new GeoGlowingTextureMeta(pixels);
	}

	/**
	 * Create a new mask image based on the pre-determined pixel data
	 */
	public void createImageMask(class_1011 originalImage, class_1011 newImage) {
		for (Pixel pixel : this.pixels) {
			int color = originalImage.method_4315(pixel.x, pixel.y); // Actually ABGR. Blame Mojang.

			if (pixel.alpha > 0)
				color = class_5253.class_8045.method_48344(pixel.alpha, class_5253.class_8045.method_48347(color), class_5253.class_8045.method_48346(color), class_5253.class_8045.method_48345(color));

			newImage.method_4305(pixel.x, pixel.y, color);
			originalImage.method_4305(pixel.x, pixel.y, 0);
		}
	}

	/**
	 * A pixel marker for a glowlayer mask
	 *
	 * @param x The X coordinate of the pixel
	 * @param y The Y coordinate of the pixel
	 * @param alpha The alpha value of the mask
	 */
	private record Pixel(int x, int y, int alpha) {}
}
