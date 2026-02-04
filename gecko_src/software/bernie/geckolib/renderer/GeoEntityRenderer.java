package software.bernie.geckolib.renderer;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.texture.AnimatableTexture;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayersContainer;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.util.RenderUtil;

import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1921;
import net.minecraft.class_1944;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_270;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4050;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5617;
import net.minecraft.class_765;
import net.minecraft.class_7833;
import net.minecraft.class_897;
import net.minecraft.class_922;

/**
 * Base {@link GeoRenderer} class for rendering {@link class_1297 Entities} specifically
 * <p>
 * All entities added to be rendered by GeckoLib should use an instance of this class
 * <p>
 * This also includes {@link net.minecraft.class_1676 Projectiles}
 */
public class GeoEntityRenderer<T extends class_1297 & GeoAnimatable> extends class_897<T> implements GeoRenderer<T> {
	protected final GeoRenderLayersContainer<T> renderLayers = new GeoRenderLayersContainer<>(this);
	protected final GeoModel<T> model;

	protected T animatable;
	protected float scaleWidth = 1;
	protected float scaleHeight = 1;

	protected Matrix4f entityRenderTranslations = new Matrix4f();
	protected Matrix4f modelRenderTranslations = new Matrix4f();

	public GeoEntityRenderer(class_5617.class_5618 renderManager, GeoModel<T> model) {
		super(renderManager);

		this.model = model;
	}

	/**
	 * Gets the model instance for this renderer
	 */
	@Override
	public GeoModel<T> getGeoModel() {
		return this.model;
	}

	/**
	 * Gets the {@link GeoAnimatable} instance currently being rendered
	 */
	@Override
	public T getAnimatable() {
		return this.animatable;
	}

	/**
	 * Gets the id that represents the current animatable's instance for animation purposes
	 * <p>
	 * This is mostly useful for things like items, which have a single registered instance for all objects
	 */
	@Override
	public long getInstanceId(T animatable) {
		return animatable.method_5628();
	}

	/**
	 * Shadowing override of {@link class_897#method_3931}
	 * <p>
	 * This redirects the call to {@link GeoRenderer#getTextureLocation}
	 */
	@Override
	public class_2960 method_3931(T animatable) {
		return GeoRenderer.super.getTextureLocation(animatable);
	}

	/**
	 * Returns the list of registered {@link GeoRenderLayer GeoRenderLayers} for this renderer
	 */
	@Override
	public List<GeoRenderLayer<T>> getRenderLayers() {
		return this.renderLayers.getRenderLayers();
	}

	/**
	 * Adds a {@link GeoRenderLayer} to this renderer, to be called after the main model is rendered each frame
	 */
	public GeoEntityRenderer<T> addRenderLayer(GeoRenderLayer<T> renderLayer) {
		this.renderLayers.addLayer(renderLayer);

		return this;
	}

	/**
	 * Sets a scale override for this renderer, telling GeckoLib to pre-scale the model
	 */
	public GeoEntityRenderer<T> withScale(float scale) {
		return withScale(scale, scale);
	}

	/**
	 * Sets a scale override for this renderer, telling GeckoLib to pre-scale the model
	 */
	public GeoEntityRenderer<T> withScale(float scaleWidth, float scaleHeight) {
		this.scaleWidth = scaleWidth;
		this.scaleHeight = scaleHeight;

		return this;
	}

	/**
	 * Gets a tint-applying color to render the given animatable with
	 * <p>
	 * Returns {@link Color#WHITE} by default, modified for invisibility in spectator
	 */
	@Override
	public Color getRenderColor(T animatable, float partialTick, int packedLight) {
		Color color = GeoRenderer.super.getRenderColor(animatable, partialTick, packedLight);

		if (animatable.method_5767() && !animatable.method_5756(ClientUtil.getClientPlayer()))
			color = Color.ofARGB(class_3532.method_15386(color.getAlpha() * 38 / 255f), color.getRed(), color.getGreen(), color.getBlue());

		return color;
	}

	/**
	 * Gets the {@link class_1921} to render the given animatable with
	 * <p>
	 * Uses the {@link class_1921#method_23578} {@code RenderType} by default
	 * <p>
	 * Override this to change the way a model will render (such as translucent models, etc).
	 *
	 * @return Return the RenderType to use, or null to prevent the model rendering. Returning null will not prevent animation functions taking place
	 */
	@Nullable
	@Override
	public class_1921 getRenderType(T animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
		final boolean invisible = animatable.method_5767();

		if (invisible && !animatable.method_5756(ClientUtil.getClientPlayer()))
			return class_1921.method_29379(texture);

		if (!invisible)
			return GeoRenderer.super.getRenderType(animatable, texture, bufferSource, partialTick);

		return class_310.method_1551().method_27022(animatable) ? class_1921.method_23287(texture) : null;
	}

	/**
	 * Called before rendering the model to buffer. Allows for render modifications and preparatory work such as scaling and translating
	 * <p>
	 * {@link class_4587} translations made here are kept until the end of the render process
	 */
	@Override
	public void preRender(class_4587 poseStack, T animatable, BakedGeoModel model, @Nullable class_4597 bufferSource, @Nullable class_4588 buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		this.entityRenderTranslations = new Matrix4f(poseStack.method_23760().method_23761());

		scaleModelForRender(this.scaleWidth, this.scaleHeight, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
	}

	@Override
	@ApiStatus.Internal
	public void method_3936(T entity, float entityYaw, float partialTick, class_4587 poseStack, class_4597 bufferSource, int packedLight) {
		this.animatable = entity;

		defaultRender(poseStack, entity, bufferSource, null, null, entityYaw, partialTick, packedLight);

		this.animatable = null;
	}

	/**
	 * The actual render method that subtype renderers should override to handle their specific rendering tasks
	 * <p>
	 * {@link GeoRenderer#preRender} has already been called by this stage, and {@link GeoRenderer#postRender} will be called directly after
	 */
	@Override
	public void actuallyRender(class_4587 poseStack, T animatable, BakedGeoModel model, @Nullable class_1921 renderType,
							   class_4597 bufferSource, @Nullable class_4588 buffer, boolean isReRender, float partialTick,
							   int packedLight, int packedOverlay, int colour) {
		poseStack.method_22903();

		class_1309 livingEntity = animatable instanceof class_1309 entity ? entity : null;
		boolean shouldSit = animatable.method_5765() && (animatable.method_5854() != null);
		float lerpBodyRot = livingEntity == null ? 0 : class_3532.method_17821(partialTick, livingEntity.field_6220, livingEntity.field_6283);
		float lerpHeadRot = livingEntity == null ? 0 : class_3532.method_17821(partialTick, livingEntity.field_6259, livingEntity.field_6241);
		float netHeadYaw = lerpHeadRot - lerpBodyRot;

		if (shouldSit && animatable.method_5854() instanceof class_1309 livingentity) {
			lerpBodyRot = class_3532.method_17821(partialTick, livingentity.field_6220, livingentity.field_6283);
			netHeadYaw = lerpHeadRot - lerpBodyRot;
			float clampedHeadYaw = class_3532.method_15363(class_3532.method_15393(netHeadYaw), -85, 85);
			lerpBodyRot = lerpHeadRot - clampedHeadYaw;

			if (clampedHeadYaw * clampedHeadYaw > 2500f)
				lerpBodyRot += clampedHeadYaw * 0.2f;

			netHeadYaw = lerpHeadRot - lerpBodyRot;
		}

		if (animatable.method_18376() == class_4050.field_18078 && livingEntity != null) {
			class_2350 bedDirection = livingEntity.method_18401();

			if (bedDirection != null) {
				float eyePosOffset = livingEntity.method_18381(class_4050.field_18076) - 0.1F;

				poseStack.method_46416(-bedDirection.method_10148() * eyePosOffset, 0, -bedDirection.method_10165() * eyePosOffset);
			}
		}

		float nativeScale = livingEntity != null ? livingEntity.method_55693() : 1;
		float ageInTicks = animatable.field_6012 + partialTick;
		float limbSwingAmount = 0;
		float limbSwing = 0;

		poseStack.method_22905(nativeScale, nativeScale, nativeScale);
		applyRotations(animatable, poseStack, ageInTicks, lerpBodyRot, partialTick, nativeScale);

		if (!shouldSit && animatable.method_5805() && livingEntity != null) {
			limbSwingAmount = livingEntity.field_42108.method_48570(partialTick);
			limbSwing = livingEntity.field_42108.method_48572(partialTick);

			if (livingEntity.method_6109())
				limbSwing *= 3f;

			if (limbSwingAmount > 1f)
				limbSwingAmount = 1f;
		}

		if (!isReRender) {
			float headPitch = class_3532.method_16439(partialTick, animatable.field_6004, animatable.method_36455());
			float motionThreshold = getMotionAnimThreshold(animatable);
			class_243 velocity = animatable.method_18798();
			float avgVelocity = (float)(Math.abs(velocity.field_1352) + Math.abs(velocity.field_1350) / 2f);
			AnimationState<T> animationState = new AnimationState<T>(animatable, limbSwing, limbSwingAmount, partialTick, avgVelocity >= motionThreshold && limbSwingAmount != 0);
			long instanceId = getInstanceId(animatable);
			GeoModel<T> currentModel = getGeoModel();

			animationState.setData(DataTickets.TICK, animatable.getTick(animatable));
			animationState.setData(DataTickets.ENTITY, animatable);
			animationState.setData(DataTickets.ENTITY_MODEL_DATA, new EntityModelData(shouldSit, livingEntity != null && livingEntity.method_6109(), -netHeadYaw, -headPitch));
			currentModel.addAdditionalStateData(animatable, instanceId, animationState::setData);
			currentModel.handleAnimations(animatable, instanceId, animationState, partialTick);
		}

		poseStack.method_46416(0, 0.01f, 0);

		this.modelRenderTranslations = new Matrix4f(poseStack.method_23760().method_23761());

		if (buffer != null)
			GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
					packedLight, packedOverlay, colour);

		poseStack.method_22909();
	}

	/**
	 * Render the various {@link GeoRenderLayer RenderLayers} that have been registered to this renderer
	 */
	@Override
	public void applyRenderLayers(class_4587 poseStack, T animatable, BakedGeoModel model, @Nullable class_1921 renderType,
								  class_4597 bufferSource, @Nullable class_4588 buffer, float partialTick,
								  int packedLight, int packedOverlay) {
		if (!animatable.method_7325())
			GeoRenderer.super.applyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
	}

	/**
	 * Call after all other rendering work has taken place, including reverting the {@link class_4587}'s state
	 * <p>
	 * This method is <u>not</u> called in {@link GeoRenderer#reRender re-render}
	 */
	@Override
	public void renderFinal(class_4587 poseStack, T animatable, BakedGeoModel model, class_4597 bufferSource, @Nullable class_4588 buffer, float partialTick, int packedLight, int packedOverlay, int colour) {
		super.method_3936(animatable, 0, partialTick, poseStack, bufferSource, packedLight);

		if (animatable instanceof class_1308 mob) {
			class_1297 leashHolder = mob.method_60952();

			if (leashHolder != null)
				renderLeash(mob, partialTick, poseStack, bufferSource, leashHolder);
		}
	}

	/**
	 * Renders the provided {@link GeoBone} and its associated child bones
	 */
	@Override
	public void renderRecursively(class_4587 poseStack, T animatable, GeoBone bone, class_1921 renderType, class_4597 bufferSource, class_4588 buffer, boolean isReRender, float partialTick, int packedLight,
								  int packedOverlay, int colour) {
		poseStack.method_22903();
		RenderUtil.translateMatrixToBone(poseStack, bone);
		RenderUtil.translateToPivotPoint(poseStack, bone);
		RenderUtil.rotateMatrixAroundBone(poseStack, bone);
		RenderUtil.scaleMatrixForBone(poseStack, bone);

		if (bone.isTrackingMatrices()) {
			Matrix4f poseState = new Matrix4f(poseStack.method_23760().method_23761());
			Matrix4f localMatrix = RenderUtil.invertAndMultiplyMatrices(poseState, this.entityRenderTranslations);

			bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.modelRenderTranslations));
			bone.setLocalSpaceMatrix(RenderUtil.translateMatrix(localMatrix, method_23169(this.animatable, 1).method_46409()));
			bone.setWorldSpaceMatrix(RenderUtil.translateMatrix(new Matrix4f(localMatrix), this.animatable.method_19538().method_46409()));
		}

		RenderUtil.translateAwayFromPivotPoint(poseStack, bone);

		buffer = checkAndRefreshBuffer(isReRender, buffer, bufferSource, renderType);

		renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, colour);

		if (!isReRender)
			applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);

		renderChildBones(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);

		poseStack.method_22909();
	}

	/**
	 * Applies rotation transformations to the renderer prior to render time to account for various entity states
	 * @deprecated Use {@link #applyRotations(class_1297, class_4587, float, float, float, float)}
	 */
	@Deprecated(forRemoval = true)
	protected void applyRotations(T animatable, class_4587 poseStack, float ageInTicks, float rotationYaw,
			float partialTick) {
		applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, 1);
	}

	/**
	 * Applies rotation transformations to the renderer prior to render time to account for various entity states
	 */
	protected void applyRotations(T animatable, class_4587 poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
		if (isShaking(animatable))
			rotationYaw += (float)(Math.cos(animatable.field_6012 * 3.25d) * Math.PI * 0.4d);

		if (!animatable.method_41328(class_4050.field_18078))
			poseStack.method_22907(class_7833.field_40716.rotationDegrees(180f - rotationYaw));

		if (animatable instanceof class_1309 livingEntity) {
			if (livingEntity.field_6213 > 0) {
				float deathRotation = (livingEntity.field_6213 + partialTick - 1f) / 20f * 1.6f;

				poseStack.method_22907(class_7833.field_40718.rotationDegrees(Math.min(class_3532.method_15355(deathRotation), 1) * getDeathMaxRotation(animatable)));
			}
			else if (livingEntity.method_6123()) {
				poseStack.method_22907(class_7833.field_40714.rotationDegrees(-90f - livingEntity.method_36455()));
				poseStack.method_22907(class_7833.field_40716.rotationDegrees((livingEntity.field_6012 + partialTick) * -75f));
			}
			else if (animatable.method_41328(class_4050.field_18078)) {
				class_2350 bedOrientation = livingEntity.method_18401();

				poseStack.method_22907(class_7833.field_40716.rotationDegrees(bedOrientation != null ? RenderUtil.getDirectionAngle(bedOrientation) : rotationYaw));
				poseStack.method_22907(class_7833.field_40718.rotationDegrees(getDeathMaxRotation(animatable)));
				poseStack.method_22907(class_7833.field_40716.rotationDegrees(270f));
			}
			else if (class_922.method_38563(livingEntity)) {
				poseStack.method_46416(0, (animatable.method_17682() + 0.1f) / nativeScale, 0);
				poseStack.method_22907(class_7833.field_40718.rotationDegrees(180f));
			}
		}
	}

	/**
	 * Gets the max rotation value for dying entities
	 * <p>
	 * You might want to modify this for different aesthetics, such as a {@link net.minecraft.class_1628} flipping upside down on death
	 * <p>
	 * Functionally equivalent to {@link net.minecraft.class_922#method_4039}
	 */
	protected float getDeathMaxRotation(T animatable) {
		return 90f;
	}

	/**
	 * Get the maximum distance (in blocks) that an entity's nameplate should be visible.
	 * <p>This is only a short-circuit predicate, and other conditions after this check must be also passed in order for the name to render</p>
	 */
	public double getNameRenderCutoffDistance(T animatable) {
		return animatable.method_21751() ? 32d : 64d;
	}

	/**
	 * Whether the entity's nametag should be rendered or not
	 * <p>
	 * Pretty much exclusively used in {@link class_897#method_3926}
	 */
	@Override
	public boolean method_3921(T animatable) {
		if (!(animatable instanceof class_1309))
			return super.method_3921(animatable);

		double nameRenderCutoff = getNameRenderCutoffDistance(animatable);

		if (this.field_4676.method_23168(animatable) >= nameRenderCutoff * nameRenderCutoff)
			return false;

		if (animatable instanceof class_1308 && (!animatable.method_5733() && (!animatable.method_16914() || animatable != this.field_4676.field_4678)))
			return false;

		final class_310 minecraft = class_310.method_1551();
		boolean visibleToClient = !animatable.method_5756(minecraft.field_1724);
		class_270 entityTeam = animatable.method_5781();

		if (entityTeam == null)
			return class_310.method_1498() && animatable != minecraft.method_1560() && visibleToClient && !animatable.method_5782();

		class_270 playerTeam = minecraft.field_1724.method_5781();

		return switch (entityTeam.method_1201()) {
			case field_1442 -> visibleToClient;
			case field_1443 -> false;
			case field_1444 -> playerTeam == null ? visibleToClient : entityTeam.method_1206(playerTeam) && (entityTeam.method_1199() || visibleToClient);
			case field_1446 -> playerTeam == null ? visibleToClient : !entityTeam.method_1206(playerTeam) && visibleToClient;
		};
	}

	/**
	 * Gets a packed overlay coordinate pair for rendering
	 * <p>
	 * Mostly just used for the red tint when an entity is hurt,
	 * but can be used for other things like the {@link net.minecraft.class_1548}
	 * white tint when exploding.
	 */
	@Override
	public int getPackedOverlay(T animatable, float u, float partialTick) {
		if (!(animatable instanceof class_1309 entity))
			return class_4608.field_21444;

		return class_4608.method_23625(class_4608.method_23210(u),
				class_4608.method_23212(entity.field_6235 > 0 || entity.field_6213 > 0));
	}

	/**
	 * Whether the entity is currently shaking. This is usually used for freezing, but also for things like piglin conversion or striders suffocating
	 * <p>
	 * This is used for a shaking effect while rendering
	 * @see net.minecraft.class_922#method_25450(class_1309)
	 */
	public boolean isShaking(T animatable) {
		return animatable.method_32314();
	}

	/**
	 * Static rendering code for rendering a leash segment
	 * <p>
	 * It's a like-for-like from {@link net.minecraft.class_927#method_61049} that had to be duplicated here for flexible usage
	 */
	public <E extends class_1297, M extends class_1308> void renderLeash(M mob, float partialTick, class_4587 poseStack,
			class_4597 bufferSource, E leashHolder) {
		double lerpBodyAngle = (class_3532.method_16439(partialTick, mob.field_6220, mob.field_6283) * class_3532.field_29847) + class_3532.field_29845;
		class_243 leashOffset = mob.method_45321(partialTick);
		double xAngleOffset = Math.cos(lerpBodyAngle) * leashOffset.field_1350 + Math.sin(lerpBodyAngle) * leashOffset.field_1352;
		double zAngleOffset = Math.sin(lerpBodyAngle) * leashOffset.field_1350 - Math.cos(lerpBodyAngle) * leashOffset.field_1352;
		double lerpOriginX = class_3532.method_16436(partialTick, mob.field_6014, mob.method_23317()) + xAngleOffset;
		double lerpOriginY = class_3532.method_16436(partialTick, mob.field_6036, mob.method_23318()) + leashOffset.field_1351;
		double lerpOriginZ = class_3532.method_16436(partialTick, mob.field_5969, mob.method_23321()) + zAngleOffset;
		class_243 ropeGripPosition = leashHolder.method_30951(partialTick);
		float xDif = (float)(ropeGripPosition.field_1352 - lerpOriginX);
		float yDif = (float)(ropeGripPosition.field_1351 - lerpOriginY);
		float zDif = (float)(ropeGripPosition.field_1350 - lerpOriginZ);
		float offsetMod = class_3532.method_48119(xDif * xDif + zDif * zDif) * 0.025f / 2f;
		float xOffset = zDif * offsetMod;
		float zOffset = xDif * offsetMod;
		class_4588 vertexConsumer = bufferSource.getBuffer(class_1921.method_23587());
		class_2338 entityEyePos = class_2338.method_49638(mob.method_5836(partialTick));
		class_2338 holderEyePos = class_2338.method_49638(leashHolder.method_5836(partialTick));
		int entityBlockLight = method_24087((T)mob, entityEyePos);
		int holderBlockLight = leashHolder.method_5809() ? 15 : leashHolder.method_37908().method_8314(class_1944.field_9282, holderEyePos);
		int entitySkyLight = mob.method_37908().method_8314(class_1944.field_9284, entityEyePos);
		int holderSkyLight = mob.method_37908().method_8314(class_1944.field_9284, holderEyePos);

		poseStack.method_22903();
		poseStack.method_22904(xAngleOffset, leashOffset.field_1351, zAngleOffset);

		Matrix4f posMatrix = new Matrix4f(poseStack.method_23760().method_23761());

		for (int segment = 0; segment <= 24; ++segment) {
			GeoEntityRenderer.renderLeashPiece(vertexConsumer, posMatrix, xDif, yDif, zDif, entityBlockLight, holderBlockLight,
					entitySkyLight, holderSkyLight, 0.025f, 0.025f, xOffset, zOffset, segment, false);
		}

		for (int segment = 24; segment >= 0; --segment) {
			GeoEntityRenderer.renderLeashPiece(vertexConsumer, posMatrix, xDif, yDif, zDif, entityBlockLight, holderBlockLight,
					entitySkyLight, holderSkyLight, 0.025f, 0.0f, xOffset, zOffset, segment, true);
		}

		poseStack.method_22909();
	}

	/**
	 * Static rendering code for rendering a leash segment
	 * <p>
	 * It's a like-for-like from {@link class_897#method_61050} that had to be duplicated here for flexible usage
	 */
	private static void renderLeashPiece(class_4588 buffer, Matrix4f positionMatrix, float xDif, float yDif,
										 float zDif, int entityBlockLight, int holderBlockLight, int entitySkyLight,
										 int holderSkyLight, float width, float yOffset, float xOffset, float zOffset, int segment, boolean isLeashKnot) {
		float piecePosPercent = segment / 24f;
		int lerpBlockLight = (int)class_3532.method_16439(piecePosPercent, entityBlockLight, holderBlockLight);
		int lerpSkyLight = (int)class_3532.method_16439(piecePosPercent, entitySkyLight, holderSkyLight);
		int packedLight = class_765.method_23687(lerpBlockLight, lerpSkyLight);
		float knotColourMod = segment % 2 == (isLeashKnot ? 1 : 0) ? 0.7f : 1f;
		float red = 0.5f * knotColourMod;
		float green = 0.4f * knotColourMod;
		float blue = 0.3f * knotColourMod;
		float x = xDif * piecePosPercent;
		float y = yDif > 0.0f ? yDif * piecePosPercent * piecePosPercent : yDif - yDif * (1.0f - piecePosPercent) * (1.0f - piecePosPercent);
		float z = zDif * piecePosPercent;

		buffer.method_22918(positionMatrix, x - xOffset, y + yOffset, z + zOffset).method_22915(red, green, blue, 1).method_60803(packedLight);
		buffer.method_22918(positionMatrix, x + xOffset, y + width - yOffset, z - zOffset).method_22915(red, green, blue, 1).method_60803(packedLight);
	}

	/**
	 * Update the current frame of a {@link AnimatableTexture potentially animated} texture used by this GeoRenderer
	 * <p>
	 * This should only be called immediately prior to rendering
	 *
	 * @see AnimatableTexture#setAndUpdate
	 */
	@Override
	public void updateAnimatedTextureFrame(T animatable) {
		AnimatableTexture.setAndUpdate(method_3931(animatable));
	}

	/**
	 * Create and fire the relevant {@code CompileLayers} event hook for this renderer
	 */
	@Override
	public void fireCompileRenderLayersEvent() {
		GeckoLibServices.Client.EVENTS.fireCompileEntityRenderLayers(this);
	}

	/**
	 * Create and fire the relevant {@code Pre-Render} event hook for this renderer
	 *
	 * @return Whether the renderer should proceed based on the cancellation state of the event
	 */
	@Override
	public boolean firePreRenderEvent(class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
		return GeckoLibServices.Client.EVENTS.fireEntityPreRender(this, poseStack, model, bufferSource, partialTick, packedLight);
	}

	/**
	 * Create and fire the relevant {@code Post-Render} event hook for this renderer
	 */
	@Override
	public void firePostRenderEvent(class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
		GeckoLibServices.Client.EVENTS.fireEntityPostRender(this, poseStack, model, bufferSource, partialTick, packedLight);
	}
}
