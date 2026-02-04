package software.bernie.geckolib.renderer.specialty;

import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.util.RenderUtil;

import java.util.Map;
import net.minecraft.class_1921;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;

/**
 * Extended special-block renderer for more advanced or dynamic models
 * <p>
 * Because of the extra performance cost of this renderer, it is advised to avoid using it unnecessarily,
 * and consider whether the benefits are worth the cost for your needs.
 */
public abstract class DynamicGeoBlockRenderer<T extends class_2586 & GeoAnimatable> extends GeoBlockRenderer<T> {
	protected static Map<class_2960, IntIntPair> TEXTURE_DIMENSIONS_CACHE = new Object2ObjectOpenHashMap<>();

	protected class_2960 textureOverride = null;

	public DynamicGeoBlockRenderer(GeoModel<T> model) {
		super(model);
	}

	/**
	 * For each bone rendered, this method is called
	 * <p>
	 * If a ResourceLocation is returned, the renderer will render the bone using that texture instead of the default
	 * This can be useful for custom rendering  on a per-bone basis
	 * <p>
	 * There is a somewhat significant performance cost involved in this however, so only use as needed
	 *
	 * @return The specified ResourceLocation, or null if no override
	 */
	@Nullable
	protected class_2960 getTextureOverrideForBone(GeoBone bone, T animatable, float partialTick) {
		return null;
	}

	/**
	 * For each bone rendered, this method is called
	 * <p>
	 * If a RenderType is returned, the renderer will render the bone using that RenderType instead of the default
	 * This can be useful for custom rendering operations on a per-bone basis
	 * <p>
	 * There is a somewhat significant performance cost involved in this however, so only use as needed
	 *
	 * @return The specified RenderType, or null if no override
	 */
	@Nullable
	protected class_1921 getRenderTypeOverrideForBone(GeoBone bone, T animatable, class_2960 texturePath, class_4597 bufferSource, float partialTick) {
		return null;
	}

	/**
	 * Override this to handle a given {@link GeoBone GeoBone's} rendering in a particular way
	 *
	 * @return Whether the renderer should skip rendering the {@link GeoCube cubes} of the given GeoBone or not
	 */
	protected boolean boneRenderOverride(class_4587 poseStack, GeoBone bone, class_4597 bufferSource, class_4588 buffer,
										 float partialTick, int packedLight, int packedOverlay, int colour) {
		return false;
	}

	/**
	 * Renders the provided {@link GeoBone} and its associated child bones
	 */
	@Override
	public void renderRecursively(class_4587 poseStack, T animatable, GeoBone bone, class_1921 renderType, class_4597 bufferSource, class_4588 buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		poseStack.method_22903();
		RenderUtil.translateMatrixToBone(poseStack, bone);
		RenderUtil.translateToPivotPoint(poseStack, bone);
		RenderUtil.rotateMatrixAroundBone(poseStack, bone);
		RenderUtil.scaleMatrixForBone(poseStack, bone);

		if (bone.isTrackingMatrices()) {
			Matrix4f poseState = new Matrix4f(poseStack.method_23760().method_23761());
			Matrix4f localMatrix = RenderUtil.invertAndMultiplyMatrices(poseState, this.blockRenderTranslations);
			Matrix4f worldState = new Matrix4f(localMatrix);
			class_2338 pos = this.animatable.method_11016();

			bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.modelRenderTranslations));
			bone.setLocalSpaceMatrix(localMatrix);
			bone.setWorldSpaceMatrix(worldState.translate(new Vector3f(pos.method_10263(), pos.method_10264(), pos.method_10260())));
		}

		RenderUtil.translateAwayFromPivotPoint(poseStack, bone);

		this.textureOverride = getTextureOverrideForBone(bone, this.animatable, partialTick);
		class_2960 texture = this.textureOverride == null ? getTextureLocation(this.animatable) : this.textureOverride;
		class_1921 renderTypeOverride = getRenderTypeOverrideForBone(bone, this.animatable, texture, bufferSource, partialTick);

		if (texture != null && renderTypeOverride == null)
			renderTypeOverride = getRenderType(this.animatable, texture, bufferSource, partialTick);

		if (renderTypeOverride != null)
			buffer = bufferSource.getBuffer(renderTypeOverride);

		if (!boneRenderOverride(poseStack, bone, bufferSource, buffer, partialTick, packedLight, packedOverlay, colour))
			super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, colour);

		if (renderTypeOverride != null)
			buffer = bufferSource.getBuffer(renderType);

		if (!isReRender)
			applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);

		buffer = checkAndRefreshBuffer(isReRender, buffer, bufferSource, renderType);

		super.renderChildBones(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);

		poseStack.method_22909();
	}

	/**
	 * Called after rendering the model to buffer. Post-render modifications should be performed here
	 * <p>
	 * {@link class_4587} transformations will be unused and lost once this method ends
	 */
	@Override
	public void postRender(class_4587 poseStack, T animatable, BakedGeoModel model, class_4597 bufferSource, @Nullable class_4588 buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		this.textureOverride = null;

		super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
	}

	/**
	 * Applies the {@link GeoQuad Quad's} {@link GeoVertex vertices} to the given {@link class_4588 buffer} for rendering
	 * <p>
	 * Custom override to handle custom non-baked textures for DynamicGeoBlockRenderer
	 */
	@Override
	public void createVerticesOfQuad(GeoQuad quad, Matrix4f poseState, Vector3f normal, class_4588 buffer,
									 int packedLight, int packedOverlay, int colour) {
		if (this.textureOverride == null) {
			super.createVerticesOfQuad(quad, poseState, normal, buffer, packedLight, packedOverlay,
					colour);

			return;
		}

		IntIntPair boneTextureSize = computeTextureSize(this.textureOverride);
		IntIntPair blockTextureSize = computeTextureSize(getTextureLocation(this.animatable));

		if (boneTextureSize == null || blockTextureSize == null) {
			super.createVerticesOfQuad(quad, poseState, normal, buffer, packedLight, packedOverlay,
					colour);

			return;
		}

		for (GeoVertex vertex : quad.vertices()) {
			Vector4f vector4f = poseState.transform(new Vector4f(vertex.position().x(), vertex.position().y(), vertex.position().z(), 1.0f));
			float texU = (vertex.texU() * blockTextureSize.firstInt()) / boneTextureSize.firstInt();
			float texV = (vertex.texV() * blockTextureSize.secondInt()) / boneTextureSize.secondInt();

			buffer.method_23919(vector4f.x(), vector4f.y(), vector4f.z(), colour, texU, texV,
					packedOverlay, packedLight, normal.x(), normal.y(), normal.z());
		}
	}

	/**
	 * Retrieve or compute the height and width of a given texture from its {@link class_2960}
	 * <p>
	 * This is used for dynamically mapping vertices on a given quad
	 * <p>
	 * This is inefficient however, and should only be used where required
	 */
	protected IntIntPair computeTextureSize(class_2960 texture) {
		return TEXTURE_DIMENSIONS_CACHE.computeIfAbsent(texture, RenderUtil::getTextureDimensions);
	}
}