package software.bernie.geckolib.loading;

import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import net.minecraft.class_2960;
import net.minecraft.class_3300;
import net.minecraft.class_3518;

/**
 * Extracts raw information from given files, and other similar functions
 */
public final class FileLoader {
	/**
	 * Load up and deserialize an animation json file to its respective {@link Animation} components
	 *
	 * @param location The resource path of the animations file
	 * @param manager The Minecraft {@code ResourceManager} responsible for maintaining in-memory resource access
	 */
	public static BakedAnimations loadAnimationsFile(class_2960 location, class_3300 manager) {
		if (location.method_12832().endsWith(".geo.json"))
			throw new IllegalArgumentException("Geo model file found in animations folder!");

		if (!location.method_12832().endsWith(".animation.json"))
			GeckoLibConstants.LOGGER.warn("Found animation file with improper file name format; animation files should end in .animation.json: '" + location + "'");

		return KeyFramesAdapter.GEO_GSON.fromJson(class_3518.method_15296(loadFile(location, manager), "animations"), BakedAnimations.class);
	}

	/**
	 * Load up and deserialize a geo model json file to its respective {@link BakedGeoModel} format
	 *
	 * @param location The resource path of the model file
	 * @param manager The Minecraft {@code ResourceManager} responsible for maintaining in-memory resource access
	 */
	public static Model loadModelFile(class_2960 location, class_3300 manager) {
		if (location.method_12832().endsWith(".animation.json"))
			throw new IllegalArgumentException("Animation file found in geo models folder!");

		if (!location.method_12832().endsWith(".geo.json"))
			GeckoLibConstants.LOGGER.warn("Found geo model file with improper file name format; geo model files should end in .geo.json: '" + location + "'");

		return KeyFramesAdapter.GEO_GSON.fromJson(loadFile(location, manager), Model.class);
	}

	/**
	 * Load a given json file into memory
	 *
	 * @param location The resource path of the json file
	 * @param manager The Minecraft {@code ResourceManager} responsible for maintaining in-memory resource access
	 */
	public static JsonObject loadFile(class_2960 location, class_3300 manager) {
		return class_3518.method_15284(KeyFramesAdapter.GEO_GSON, getFileContents(location, manager), JsonObject.class);
	}

	/**
	 * Read a text-based file into memory in the form of a single string
	 *
	 * @param location The resource path of the file
	 * @param manager The Minecraft {@code ResourceManager} responsible for maintaining in-memory resource access
	 */
	public static String getFileContents(class_2960 location, class_3300 manager) {
		try (InputStream inputStream = manager.getResourceOrThrow(location).method_14482()) {
			return IOUtils.toString(inputStream, Charset.defaultCharset());
		}
		catch (Exception e) {
			GeckoLibConstants.LOGGER.error("Couldn't load " + location, e);

			throw new RuntimeException(new FileNotFoundException(location.toString()));
		}
	}
}
