package software.bernie.geckolib.renderer;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.texture.AnimatableTexture;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayersContainer;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.util.RenderUtil;

import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3489;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_5602;
import net.minecraft.class_572;
import net.minecraft.class_630;
import net.minecraft.class_918;
import net.minecraft.class_9282;

/**
 * Base {@link GeoRenderer} for rendering in-world armor specifically
 * <p>
 * All custom armor added to be rendered in-world by GeckoLib should use an instance of this class
 *
 * @see GeoItem
 */
public class GeoArmorRenderer<T extends class_1792 & GeoItem> extends class_572 implements GeoRenderer<T> {
	protected final GeoRenderLayersContainer<T> renderLayers = new GeoRenderLayersContainer<>(this);
	protected final GeoModel<T> model;

	protected T animatable;
	protected class_572<?> baseModel;
	protected float scaleWidth = 1;
	protected float scaleHeight = 1;

	protected Matrix4f entityRenderTranslations = new Matrix4f();
	protected Matrix4f modelRenderTranslations = new Matrix4f();

	protected BakedGeoModel lastModel = null;
	protected GeoBone head = null;
	protected GeoBone body = null;
	protected GeoBone rightArm = null;
	protected GeoBone leftArm = null;
	protected GeoBone rightLeg = null;
	protected GeoBone leftLeg = null;
	protected GeoBone rightBoot = null;
	protected GeoBone leftBoot = null;

	protected class_1297 currentEntity = null;
	protected class_1799 currentStack = null;
	protected class_1304 currentSlot = null;

	public GeoArmorRenderer(GeoModel<T> model) {
		super(class_310.method_1551().method_31974().method_32072(class_5602.field_27579));

		this.model = model;
		this.field_3448 = false;
	}

	/**
	 * Gets the model instance for this renderer
	 */
	@Override
	public GeoModel<T> getGeoModel() {
		return this.model;
	}

	/**
	 * Gets the {@link GeoItem} instance currently being rendered
	 */
	public T getAnimatable() {
		return this.animatable;
	}

	/**
	 * Returns the entity currently being rendered with armour equipped
	 */
	public class_1297 getCurrentEntity() {
		return this.currentEntity;
	}

	/**
	 * Returns the ItemStack pertaining to the current piece of armor being rendered
	 */
	public class_1799 getCurrentStack() {
		return this.currentStack;
	}

	/**
	 * Returns the equipped slot of the armor piece being rendered
	 */
	public class_1304 getCurrentSlot() {
		return this.currentSlot;
	}

	/**
	 * Gets the id that represents the current animatable's instance for animation purposes
	 * <p>
	 * This is mostly useful for things like items, which have a single registered instance for all objects
	 */
	@Override
	public long getInstanceId(T animatable) {
		return GeoItem.getId(this.currentStack) + this.currentEntity.method_5628();
	}

	/**
	 * Gets the {@link class_1921} to render the given animatable with
	 * <p>
	 * Uses the {@link class_1921#method_25448} {@code RenderType} by default
	 * <p>
	 * Override this to change the way a model will render (such as translucent models, etc)
	 */
	@Override
	public class_1921 getRenderType(T animatable, class_2960 texture, @Nullable class_4597 bufferSource, float partialTick) {
		return class_1921.method_25448(texture);
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
	public GeoArmorRenderer<T> addRenderLayer(GeoRenderLayer<T> renderLayer) {
		this.renderLayers.addLayer(renderLayer);

		return this;
	}

	/**
	 * Sets a scale override for this renderer, telling GeckoLib to pre-scale the model
	 */
	public GeoArmorRenderer<T> withScale(float scale) {
		return withScale(scale, scale);
	}

	/**
	 * Sets a scale override for this renderer, telling GeckoLib to pre-scale the model
	 */
	public GeoArmorRenderer<T> withScale(float scaleWidth, float scaleHeight) {
		this.scaleWidth = scaleWidth;
		this.scaleHeight = scaleHeight;

		return this;
	}

	/**
	 * Returns the 'head' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the head model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getHeadBone(GeoModel<T> model) {
		return model.getBone("armorHead").orElse(null);
	}

	/**
	 * Returns the 'body' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the body model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getBodyBone(GeoModel<T> model) {
		return model.getBone("armorBody").orElse(null);
	}

	/**
	 * Returns the 'right arm' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the right arm model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getRightArmBone(GeoModel<T> model) {
		return model.getBone("armorRightArm").orElse(null);
	}

	/**
	 * Returns the 'left arm' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the left arm model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getLeftArmBone(GeoModel<T> model) {
		return model.getBone("armorLeftArm").orElse(null);
	}

	/**
	 * Returns the 'right leg' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the right leg model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getRightLegBone(GeoModel<T> model) {
		return model.getBone("armorRightLeg").orElse(null);
	}

	/**
	 * Returns the 'left leg' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the left leg model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getLeftLegBone(GeoModel<T> model) {
		return model.getBone("armorLeftLeg").orElse(null);
	}

	/**
	 * Returns the 'right boot' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the right boot model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getRightBootBone(GeoModel<T> model) {
		return model.getBone("armorRightBoot").orElse(null);
	}

	/**
	 * Returns the 'left boot' GeoBone from this model
	 * <p>
	 * Override if your geo model has different bone names for these bones
	 *
	 * @return The bone for the left boot model piece, or null if not using it
	 */
	@Nullable
	public GeoBone getLeftBootBone(GeoModel<T> model) {
		return model.getBone("armorLeftBoot").orElse(null);
	}

	/**
	 * Gets a tint-applying color to render the given animatable with
	 * <p>
	 * Returns {@link Color#WHITE} by default
	 */
	@Override
	public Color getRenderColor(T animatable, float partialTick, int packedLight) {
		return this.currentStack.method_31573(class_3489.field_48803) ? Color.ofOpaque(class_9282.method_57470(this.currentStack, -6265536)) : Color.WHITE;
	}

	/**
	 * Called before rendering the model to buffer. Allows for render modifications and preparatory work such as scaling and translating
	 * <p>
	 * {@link class_4587} translations made here are kept until the end of the render process
	 */
	@Override
	public void preRender(class_4587 poseStack, T animatable, BakedGeoModel model, @Nullable class_4597 bufferSource,
						  @Nullable class_4588 buffer, boolean isReRender, float partialTick, int packedLight,
						  int packedOverlay, int colour) {
		this.entityRenderTranslations = new Matrix4f(poseStack.method_23760().method_23761());

		applyBaseModel(this.baseModel);
		grabRelevantBones(model);
		applyBaseTransformations(this.baseModel);
		scaleModelForBaby(poseStack, animatable, partialTick, isReRender);
		scaleModelForRender(this.scaleWidth, this.scaleHeight, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);

		if (!(this.currentEntity instanceof GeoAnimatable))
			applyBoneVisibilityBySlot(this.currentSlot);
	}

	@Override
	@ApiStatus.Internal
	public void method_2828(class_4587 poseStack, @Nullable class_4588 buffer, int packedLight,
							   int packedOverlay, int colour) {
		class_310 mc = class_310.method_1551();
		class_4597 bufferSource =  mc.field_1769.field_20951.method_23000();

		if (mc.field_1769.method_3270() && mc.method_27022(this.currentEntity))
			bufferSource =  mc.field_1769.field_20951.method_23003();

		float partialTick = mc.method_60646().method_60637(true);
		class_1921 renderType = getRenderType(this.animatable, getTextureLocation(this.animatable), bufferSource, partialTick);
		buffer = class_918.method_27952(bufferSource, renderType, this.currentStack.method_7958());

		defaultRender(poseStack, this.animatable, bufferSource, null, buffer,
				0, partialTick, packedLight);

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
		poseStack.method_46416(0, 24 / 16f, 0);
		poseStack.method_22905(-1, -1, 1);

		if (!isReRender) {
			AnimationState<T> animationState = new AnimationState<>(animatable, 0, 0, partialTick, false);
			long instanceId = getInstanceId(animatable);
			GeoModel<T> currentModel = getGeoModel();

			animationState.setData(DataTickets.TICK, animatable.getTick(this.currentEntity));
			animationState.setData(DataTickets.ITEMSTACK, this.currentStack);
			animationState.setData(DataTickets.ENTITY, this.currentEntity);
			animationState.setData(DataTickets.EQUIPMENT_SLOT, this.currentSlot);
			currentModel.addAdditionalStateData(animatable, instanceId, animationState::setData);
			currentModel.handleAnimations(animatable, instanceId, animationState, partialTick);
		}

		this.modelRenderTranslations = new Matrix4f(poseStack.method_23760().method_23761());

		if (buffer != null)
			GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
					packedLight, packedOverlay, colour);

		poseStack.method_22909();
	}

	/**
	 * Renders the provided {@link GeoBone} and its associated child bones
	 */
	@Override
	public void renderRecursively(class_4587 poseStack, T animatable, GeoBone bone, class_1921 renderType, class_4597 bufferSource, class_4588 buffer, boolean isReRender, float partialTick, int packedLight,
								  int packedOverlay, int colour) {
		if (bone.isTrackingMatrices()) {
			Matrix4f poseState = new Matrix4f(poseStack.method_23760().method_23761());

			bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.modelRenderTranslations));
			bone.setLocalSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.entityRenderTranslations));
		}

		GeoRenderer.super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
	}

	/**
	 * Gets and caches the relevant armor model bones for this baked model if it hasn't been done already
	 */
	protected void grabRelevantBones(BakedGeoModel bakedModel) {
		if (this.lastModel == bakedModel)
			return;

		GeoModel<T> model = getGeoModel();
		this.lastModel = bakedModel;
		this.head = getHeadBone(model);
		this.body = getBodyBone(model);
		this.rightArm = getRightArmBone(model);
		this.leftArm = getLeftArmBone(model);
		this.rightLeg = getRightLegBone(model);
		this.leftLeg = getLeftLegBone(model);
		this.rightBoot = getRightBootBone(model);
		this.leftBoot = getLeftBootBone(model);
	}

	/**
	 * Prepare the renderer for the current render cycle
	 * <p>
	 * Must be called prior to render as the default HumanoidModel doesn't give render context
	 * <p>
	 * Params have been left nullable so that the renderer can be called for model/texture purposes safely.
	 * If you do grab the renderer using null parameters, you should not use it for actual rendering
	 *
	 * @param entity The entity being rendered with the armor on
	 * @param stack The ItemStack being rendered
	 * @param slot The slot being rendered
	 * @param baseModel The default (vanilla) model that would have been rendered if this model hadn't replaced it
	 */
	public void prepForRender(@Nullable class_1297 entity, class_1799 stack, @Nullable class_1304 slot, @Nullable class_572<?> baseModel) {
		if (entity == null || slot == null || baseModel == null)
			return;

		this.baseModel = baseModel;
		this.currentEntity = entity;
		this.currentStack = stack;
		this.animatable = (T)stack.method_7909();
		this.currentSlot = slot;
	}

	/**
	 * Applies settings and transformations pre-render based on the default model
	 */
	protected void applyBaseModel(class_572<?> baseModel) {
		this.field_3448 = baseModel.field_3448;
		this.field_3400 = baseModel.field_3400;
		this.field_3449 = baseModel.field_3449;
		this.field_3395 = baseModel.field_3395;
		this.field_3399 = baseModel.field_3399;
	}

	/**
	 * Resets the bone visibility for the model based on the currently rendering slot,
	 * and then sets bones relevant to the current slot as visible for rendering
	 * <p>
	 * This is only called by default for non-geo entities (I.E. players or vanilla mobs)
	 */
	protected void applyBoneVisibilityBySlot(class_1304 currentSlot) {
		method_2805(false);

		switch (currentSlot) {
			case field_6169 -> setBoneVisible(this.head, true);
			case field_6174 -> {
				setBoneVisible(this.body, true);
				setBoneVisible(this.rightArm, true);
				setBoneVisible(this.leftArm, true);
			}
			case field_6172 -> {
				setBoneVisible(this.rightLeg, true);
				setBoneVisible(this.leftLeg, true);
			}
			case field_6166 -> {
				setBoneVisible(this.rightBoot, true);
				setBoneVisible(this.leftBoot, true);
			}
			default -> {}
		}
	}

	/**
	 * Resets the bone visibility for the model based on the current {@link class_630} and {@link class_1304},
	 * and then sets the bones relevant to the current part as visible for rendering
	 * <p>
	 * If you are rendering a geo entity with armor, you should probably be calling this prior to rendering
	 */
	public void applyBoneVisibilityByPart(class_1304 currentSlot, class_630 currentPart, class_572<?> model) {
		method_2805(false);

		currentPart.field_3665 = true;
		GeoBone bone = null;

		if (currentPart == model.field_3394 || currentPart == model.field_3398) {
			bone = this.head;
		}
		else if (currentPart == model.field_3391) {
			bone = this.body;
		}
		else if (currentPart == model.field_27433) {
			bone = this.leftArm;
		}
		else if (currentPart == model.field_3401) {
			bone = this.rightArm;
		}
		else if (currentPart == model.field_3397) {
			bone = currentSlot == class_1304.field_6166 ? this.leftBoot : this.leftLeg;
		}
		else if (currentPart == model.field_3392) {
			bone = currentSlot == class_1304.field_6166 ? this.rightBoot : this.rightLeg;
		}

		if (bone != null)
			bone.setHidden(false);
	}

	/**
	 * Transform the currently rendering {@link GeoModel} to match the positions and rotations of the base model
	 */
	protected void applyBaseTransformations(class_572<?> baseModel) {
		if (this.head != null) {
			class_630 headPart = baseModel.field_3398;

			RenderUtil.matchModelPartRot(headPart, this.head);
			this.head.updatePosition(headPart.field_3657, -headPart.field_3656, headPart.field_3655);
		}

		if (this.body != null) {
			class_630 bodyPart = baseModel.field_3391;

			RenderUtil.matchModelPartRot(bodyPart, this.body);
			this.body.updatePosition(bodyPart.field_3657, -bodyPart.field_3656, bodyPart.field_3655);
		}

		if (this.rightArm != null) {
			class_630 rightArmPart = baseModel.field_3401;

			RenderUtil.matchModelPartRot(rightArmPart, this.rightArm);
			this.rightArm.updatePosition(rightArmPart.field_3657 + 5, 2 - rightArmPart.field_3656, rightArmPart.field_3655);
		}

		if (this.leftArm != null) {
			class_630 leftArmPart = baseModel.field_27433;

			RenderUtil.matchModelPartRot(leftArmPart, this.leftArm);
			this.leftArm.updatePosition(leftArmPart.field_3657 - 5f, 2f - leftArmPart.field_3656, leftArmPart.field_3655);
		}

		if (this.rightLeg != null) {
			class_630 rightLegPart = baseModel.field_3392;

			RenderUtil.matchModelPartRot(rightLegPart, this.rightLeg);
			this.rightLeg.updatePosition(rightLegPart.field_3657 + 2, 12 - rightLegPart.field_3656, rightLegPart.field_3655);

			if (this.rightBoot != null) {
				RenderUtil.matchModelPartRot(rightLegPart, this.rightBoot);
				this.rightBoot.updatePosition(rightLegPart.field_3657 + 2, 12 - rightLegPart.field_3656, rightLegPart.field_3655);
			}
		}

		if (this.leftLeg != null) {
			class_630 leftLegPart = baseModel.field_3397;

			RenderUtil.matchModelPartRot(leftLegPart, this.leftLeg);
			this.leftLeg.updatePosition(leftLegPart.field_3657 - 2, 12 - leftLegPart.field_3656, leftLegPart.field_3655);

			if (this.leftBoot != null) {
				RenderUtil.matchModelPartRot(leftLegPart, this.leftBoot);
				this.leftBoot.updatePosition(leftLegPart.field_3657 - 2, 12 - leftLegPart.field_3656, leftLegPart.field_3655);
			}
		}
	}

	@Override
	public void method_2805(boolean pVisible) {
		super.method_2805(pVisible);

		setBoneVisible(this.head, pVisible);
		setBoneVisible(this.body, pVisible);
		setBoneVisible(this.rightArm, pVisible);
		setBoneVisible(this.leftArm, pVisible);
		setBoneVisible(this.rightLeg, pVisible);
		setBoneVisible(this.leftLeg, pVisible);
		setBoneVisible(this.rightBoot, pVisible);
		setBoneVisible(this.leftBoot, pVisible);
	}

	/**
	 * Apply custom scaling to account for {@link net.minecraft.class_4592 AgeableListModel} baby models
	 */
	public void scaleModelForBaby(class_4587 poseStack, T animatable, float partialTick, boolean isReRender) {
		if (!this.field_3448 || isReRender)
			return;

		if (this.currentSlot == class_1304.field_6169) {
			if (this.baseModel.field_20915) {
				float headScale = 1.5f / this.baseModel.field_20918;

				poseStack.method_22905(headScale, headScale, headScale);
			}

			poseStack.method_46416(0, this.baseModel.field_20916 / 16f, this.baseModel.field_20917 / 16f);
		}
		else {
			float bodyScale = 1 / this.baseModel.field_20919;

			poseStack.method_22905(bodyScale, bodyScale, bodyScale);
			poseStack.method_46416(0, this.baseModel.field_20920 / 16f, 0);
		}
	}

	/**
	 * Sets a bone as visible or hidden, with nullability
	 */
	protected void setBoneVisible(@Nullable GeoBone bone, boolean visible) {
		if (bone == null)
			return;

		bone.setHidden(!visible);
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
		if (this.currentEntity != null)
			AnimatableTexture.setAndUpdate(getTextureLocation(animatable));
	}

	/**
	 * Create and fire the relevant {@code CompileLayers} event hook for this renderer
	 */
	@Override
	public void fireCompileRenderLayersEvent() {
		GeckoLibServices.Client.EVENTS.fireCompileArmorRenderLayers(this);
	}

	/**
	 * Create and fire the relevant {@code Pre-Render} event hook for this renderer
	 *
	 * @return Whether the renderer should proceed based on the cancellation state of the event
	 */
	@Override
	public boolean firePreRenderEvent(class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
		return GeckoLibServices.Client.EVENTS.fireArmorPreRender(this, poseStack, model, bufferSource, partialTick, packedLight);
	}

	/**
	 * Create and fire the relevant {@code Post-Render} event hook for this renderer
	 */
	@Override
	public void firePostRenderEvent(class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
		GeckoLibServices.Client.EVENTS.fireArmorPostRender(this, poseStack, model, bufferSource, partialTick, packedLight);
	}
}