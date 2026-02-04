package software.bernie.geckolib.cache.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibServices;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import net.minecraft.class_1011;
import net.minecraft.class_1044;
import net.minecraft.class_1047;
import net.minecraft.class_1060;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import net.minecraft.class_4573;

/**
 * Abstract texture wrapper for GeckoLib textures
 * <p>
 * Mostly just handles boilerplate
 */
public abstract class GeoAbstractTexture extends class_1044 {
	/**
	 * Generates the texture instance for the given path with the given appendix if it hasn't already been generated
	 */
	protected static void generateTexture(class_2960 texturePath, Consumer<class_1060> textureManagerConsumer) {
		if (!RenderSystem.isOnRenderThreadOrInit())
			throw new IllegalThreadStateException("Texture loading called outside of the render thread! This should DEFINITELY not be happening.");

		class_1060 textureManager = class_310.method_1551().method_1531();

		if (!(textureManager.method_34590(texturePath, class_1047.method_4540()) instanceof GeoAbstractTexture))
			textureManagerConsumer.accept(textureManager);
	}

	@Override
	public final void method_4625(class_3300 resourceManager) throws IOException {
		class_4573 renderCall = loadTexture(resourceManager, class_310.method_1551());

		if (renderCall == null)
			return;

		if (!RenderSystem.isOnRenderThreadOrInit()) {
			RenderSystem.recordRenderCall(renderCall);
		}
		else {
			renderCall.execute();
		}
	}

	/**
	 * Debugging function to write out the generated glowmap image to disk
	 */
	protected void printDebugImageToDisk(class_2960 id, class_1011 newImage) {
		try {
			File file = new File(GeckoLibServices.PLATFORM.getGameDir().toFile(), "GeoTexture Debug Printouts");

			if (!file.exists()) {
				file.mkdirs();
			}
			else if (!file.isDirectory()) {
				file.delete();
				file.mkdirs();
			}

			file = new File(file, id.method_12832().replace('/', '.'));

			if (!file.exists())
				file.createNewFile();

			newImage.method_4325(file);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Called at {@link class_1044#method_4625} time to load this texture for the first time into the render cache
	 * <p>
	 * Generate and apply the necessary functions here, then return the RenderCall to submit to the render pipeline
	 *
	 * @return The RenderCall to submit to the render pipeline, or null if no further action required
	 */
	@Nullable
	protected abstract class_4573 loadTexture(class_3300 resourceManager, class_310 mc) throws IOException;

	/**
	 * No-frills helper method for uploading {@link class_1011 images} into memory for use
	 */
	public static void uploadSimple(int texture, class_1011 image, boolean blur, boolean clamp) {
		TextureUtil.prepareImage(texture, 0, image.method_4307(), image.method_4323());
		image.method_22619(0, 0, 0, 0, 0, image.method_4307(), image.method_4323(), blur, clamp, false, true);
	}

	public static class_2960 appendToPath(class_2960 location, String suffix) {
		String path = location.method_12832();
		int i = path.lastIndexOf('.');

		return class_2960.method_60655(location.method_12836(), path.substring(0, i) + suffix + path.substring(i));
	}
}
