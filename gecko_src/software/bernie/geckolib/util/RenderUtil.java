package software.bernie.geckolib.util;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2586;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3673;
import net.minecraft.class_4587;
import net.minecraft.class_630;
import net.minecraft.class_7833;
import net.minecraft.class_827;
import net.minecraft.class_897;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

/**
 * Helper class for various methods and functions useful while rendering
 */
public final class RenderUtil {
	public static void translateMatrixToBone(class_4587 poseStack, GeoBone bone) {
		poseStack.method_46416(-bone.getPosX() / 16f, bone.getPosY() / 16f, bone.getPosZ() / 16f);
	}

	public static void rotateMatrixAroundBone(class_4587 poseStack, GeoBone bone) {
		if (bone.getRotZ() != 0)
			poseStack.method_22907(class_7833.field_40718.rotation(bone.getRotZ()));

		if (bone.getRotY() != 0)
			poseStack.method_22907(class_7833.field_40716.rotation(bone.getRotY()));

		if (bone.getRotX() != 0)
			poseStack.method_22907(class_7833.field_40714.rotation(bone.getRotX()));
	}

	public static void rotateMatrixAroundCube(class_4587 poseStack, GeoCube cube) {
		class_243 rotation = cube.rotation();

		poseStack.method_22907(new Quaternionf().rotationXYZ(0, 0, (float)rotation.method_10215()));
		poseStack.method_22907(new Quaternionf().rotationXYZ(0, (float)rotation.method_10214(), 0));
		poseStack.method_22907(new Quaternionf().rotationXYZ((float)rotation.method_10216(), 0, 0));
	}

	public static void scaleMatrixForBone(class_4587 poseStack, GeoBone bone) {
		poseStack.method_22905(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
	}

	public static void translateToPivotPoint(class_4587 poseStack, GeoCube cube) {
		class_243 pivot = cube.pivot();
		poseStack.method_22904(pivot.method_10216() / 16f, pivot.method_10214() / 16f, pivot.method_10215() / 16f);
	}

	public static void translateToPivotPoint(class_4587 poseStack, GeoBone bone) {
		poseStack.method_46416(bone.getPivotX() / 16f, bone.getPivotY() / 16f, bone.getPivotZ() / 16f);
	}

	public static void translateAwayFromPivotPoint(class_4587 poseStack, GeoCube cube) {
		class_243 pivot = cube.pivot();

		poseStack.method_22904(-pivot.method_10216() / 16f, -pivot.method_10214() / 16f, -pivot.method_10215() / 16f);
	}

	public static void translateAwayFromPivotPoint(class_4587 poseStack, GeoBone bone) {
		poseStack.method_46416(-bone.getPivotX() / 16f, -bone.getPivotY() / 16f, -bone.getPivotZ() / 16f);
	}

	public static void translateAndRotateMatrixForBone(class_4587 poseStack, GeoBone bone) {
		translateToPivotPoint(poseStack, bone);
		rotateMatrixAroundBone(poseStack, bone);
	}

	public static void prepMatrixForBone(class_4587 poseStack, GeoBone bone) {
		translateMatrixToBone(poseStack, bone);
		translateToPivotPoint(poseStack, bone);
		rotateMatrixAroundBone(poseStack, bone);
		scaleMatrixForBone(poseStack, bone);
		translateAwayFromPivotPoint(poseStack, bone);
	}
	
	public static Matrix4f invertAndMultiplyMatrices(Matrix4f baseMatrix, Matrix4f inputMatrix) {
		inputMatrix = new Matrix4f(inputMatrix);
		
		inputMatrix.invert();
		inputMatrix.mul(baseMatrix);

		return inputMatrix;
	}
	
	/**
     * Translates the provided {@link class_4587} to face towards the given {@link class_1297}'s rotation
	 * <p>
     * Usually used for rotating projectiles towards their trajectory, in an {@link GeoRenderer#preRender} override
	 */
	public static void faceRotation(class_4587 poseStack, class_1297 animatable, float partialTick) {
		poseStack.method_22907(class_7833.field_40716.rotationDegrees(class_3532.method_16439(partialTick, animatable.field_5982, animatable.method_36454()) - 90));
		poseStack.method_22907(class_7833.field_40718.rotationDegrees(class_3532.method_16439(partialTick, animatable.field_6004, animatable.method_36455())));
	}

	/**
	 * Add a positional vector to a matrix
	 * <p>
	 * This is specifically implemented to act as a translation of an x/y/z coordinate triplet to a render matrix
	 */
	public static Matrix4f translateMatrix(Matrix4f matrix, Vector3f vector) {
		return matrix.add(new Matrix4f().m30(vector.x).m31(vector.y).m32(vector.z));
	}
	
	/**
	 * Gets the actual dimensions of a texture resource from a given path
	 * <p>
	 * Not performance-efficient, and should not be relied upon
	 *
	 * @param texture The path of the texture resource to check
	 * @return The dimensions (width x height) of the texture, or null if unable to find or read the file
	 */
	@Nullable
	public static IntIntPair getTextureDimensions(class_2960 texture) {
		if (texture == null)
			return null;

		class_1044 originalTexture = null;
		class_310 mc = class_310.method_1551();

		try {
			originalTexture = mc.method_5385(() -> mc.method_1531().method_4619(texture)).get();
		}
		catch (Exception e) {
			GeckoLibConstants.LOGGER.warn("Failed to load image for id {}", texture);
			e.printStackTrace();
		}

		if (originalTexture == null)
			return null;

		class_1011 image = null;

		try {
			image = originalTexture instanceof class_1043 dynamicTexture ? dynamicTexture.method_4525()
					: class_1011.method_4309(mc.method_1478().method_14486(texture).get().method_14482());
		}
		catch (Exception e) {
			GeckoLibConstants.LOGGER.error("Failed to read image for id {}", texture);
			e.printStackTrace();
		}

		return image == null ? null : IntIntImmutablePair.of(image.method_4307(), image.method_4323());
	}

	public static double getCurrentSystemTick() {
		return System.nanoTime() / 1E6 / 50d;
	}

	/**
	 * Returns the current time (in ticks) that the {@link org.lwjgl.glfw.GLFW GLFW} instance has been running
	 * <p>
	 * This is effectively a permanent timer that counts up since the game was launched.
	 */
	public static double getCurrentTick() {
		return class_3673.method_15974() * 20d;
	}

	/**
	 * Returns a float equivalent of a boolean
	 * <p>
	 * Output table:
	 * <ul>
	 *     <li>true -> 1</li>
	 *     <li>false -> 0</li>
	 * </ul>
	 */
	public static float booleanToFloat(boolean input) {
		return input ? 1f : 0f;
	}

	/**
	 * Converts a given double array to its {@link class_243} equivalent
	 */
	public static class_243 arrayToVec(double[] array) {
		return new class_243(array[0], array[1], array[2]);
	}

	/**
	 * Rotates a {@link GeoBone} to match a provided {@link class_630}'s rotations
	 * <p>
	 * Usually used for items or armor rendering to match the rotations of other non-geo model parts
	 */
	public static void matchModelPartRot(class_630 from, GeoBone to) {
		to.updateRotation(-from.field_3654, -from.field_3675, from.field_3674);
	}

	/**
	 * If a {@link GeoCube} is a 2d plane the {@link software.bernie.geckolib.cache.object.GeoQuad Quad's}
	 * normal is inverted in an intersecting plane,it can cause issues with shaders and other lighting tasks
	 * <p>
	 * This performs a pseudo-ABS function to help resolve some of those issues
	 */
	public static void fixInvertedFlatCube(GeoCube cube, Vector3f normal) {
		if (normal.x() < 0 && (cube.size().method_10214() == 0 || cube.size().method_10215() == 0))
			normal.mul(-1, 1, 1);

		if (normal.y() < 0 && (cube.size().method_10216() == 0 || cube.size().method_10215() == 0))
			normal.mul(1, -1, 1);

		if (normal.z() < 0 && (cube.size().method_10216() == 0 || cube.size().method_10214() == 0))
			normal.mul(1, 1, -1);
	}

	/**
	 * Converts a {@link class_2350} to a rotational float for rotation purposes
	 */
	public static float getDirectionAngle(class_2350 direction) {
		return switch(direction) {
			case field_11035 -> 90f;
			case field_11043 -> 270f;
			case field_11034 -> 180f;
			default -> 0f;
		};
	}

	/**
	 * Special helper function for lerping yaw.
	 * <p>
	 * This exists because yaw in Minecraft handles its yaw a bit strangely, and can cause incorrect results if lerped without accounting for special-cases
	 */
	public static double lerpYaw(double delta, double start, double end) {
		start = class_3532.method_15338(start);
		end = class_3532.method_15338(end);
		double diff = start - end;
		end = diff > 180 || diff < -180 ? start + Math.copySign(360 - Math.abs(diff), diff) : end;

		return class_3532.method_16436(delta, start, end);
	}

	/**
	 * Gets a {@link GeoModel} instance from a given {@link class_1299}
	 * <p>
	 * This only works if you're calling this method for an EntityType known to be using a {@link GeoRenderer GeckoLib Renderer}
	 * <p>
	 * Generally speaking you probably shouldn't be calling this method at all.
	 *
	 * @param entityType The {@code EntityType} to retrieve the GeoModel for
	 * @return The GeoModel, or null if one isn't found
	 */
	@Nullable
	public static GeoModel<?> getGeoModelForEntityType(class_1299<?> entityType) {
		class_897<?> renderer = class_310.method_1551().method_1561().field_4696.get(entityType);

		return renderer instanceof GeoRenderer<?> geoRenderer ? geoRenderer.getGeoModel() : null;
	}

	/**
	 * Gets a GeoAnimatable instance that has been registered as the replacement renderer for a given {@link class_1299}
	 *
	 * @param entityType The {@code EntityType} to retrieve the replaced {@link GeoAnimatable} for
	 * @return The {@code GeoAnimatable} instance, or null if one isn't found
	 */
	@Nullable
	public static GeoAnimatable getReplacedAnimatable(class_1299<?> entityType) {
		class_897<?> renderer = class_310.method_1551().method_1561().field_4696.get(entityType);

		return renderer instanceof GeoReplacedEntityRenderer<?, ?> replacedEntityRenderer ? replacedEntityRenderer.getAnimatable() : null;
	}

	/**
	 * Gets a {@link GeoModel} instance from a given {@link class_1297}
	 * <p>
	 * This only works if you're calling this method for an Entity known to be using a {@link GeoRenderer GeckoLib Renderer}
	 * <p>
	 * Generally speaking you probably shouldn't be calling this method at all.
	 *
	 * @param entity The {@code Entity} to retrieve the GeoModel for
	 * @return The GeoModel, or null if one isn't found
	 */
	@Nullable
	public static GeoModel<?> getGeoModelForEntity(class_1297 entity) {
		class_897<?> renderer = class_310.method_1551().method_1561().method_3953(entity);

		return renderer instanceof GeoRenderer<?> geoRenderer ? geoRenderer.getGeoModel() : null;
	}

	/**
	 * Gets a {@link GeoModel} instance from a given {@link class_1792}
	 * <p>
	 * This only works if you're calling this method for an Item known to be using a {@link GeoRenderer GeckoLib Renderer}
	 * <p>
	 * Generally speaking you probably shouldn't be calling this method at all.
	 *
	 * @param item The {@code ItemStack} to retrieve the GeoModel for
	 * @return The GeoModel, or null if one isn't found
	 */
	@Nullable
	public static GeoModel<?> getGeoModelForItem(class_1799 item) {
		return GeckoLibServices.Client.ITEM_RENDERING.getGeoModelForItem(item);
	}

	/**
	 * Gets a {@link GeoModel} instance from a given {@link class_2586}
	 * <p>
	 * This only works if you're calling this method for a BlockEntity known to be using a {@link GeoRenderer GeckoLib Renderer}
	 * <p>
	 * Generally speaking you probably shouldn't be calling this method at all
	 *
	 * @param blockEntity The {@code BlockEntity} to retrieve the GeoModel for
	 * @return The GeoModel, or null if one isn't found
	 */
	@Nullable
	public static GeoModel<?> getGeoModelForBlock(class_2586 blockEntity) {
		class_827<?> renderer = class_310.method_1551().method_31975().method_3550(blockEntity);

		return renderer instanceof GeoRenderer<?> geoRenderer ? geoRenderer.getGeoModel() : null;
	}

	/**
	 * Gets a {@link GeoModel} instance from a given {@link class_1792}
	 * <p>
	 * This only works if you're calling this method for an Item known to be using a {@link software.bernie.geckolib.renderer.GeoArmorRenderer GeoArmorRenderer}
	 * <p>
	 * Generally speaking you probably shouldn't be calling this method at all
	 *
	 * @param stack The ItemStack to retrieve the GeoModel for
	 * @return The GeoModel, or null if one isn't found
	 */
	@Nullable
	public static GeoModel<?> getGeoModelForArmor(class_1799 stack) {
		return GeckoLibServices.Client.ITEM_RENDERING.getGeoModelForArmor(stack);
	}
}
