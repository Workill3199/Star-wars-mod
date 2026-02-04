package software.bernie.geckolib.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.texture.AnimatableTexture;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayersContainer;
import software.bernie.geckolib.util.RenderUtil;

import java.util.List;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_308;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_5599;
import net.minecraft.class_756;
import net.minecraft.class_811;
import net.minecraft.class_824;
import net.minecraft.class_897;
import net.minecraft.class_918;

/**
 * Base {@link GeoRenderer} class for rendering {@link class_1792 Items} specifically
 * <p>
 * All items added to be rendered by GeckoLib should use an instance of this class.
 */
public class GeoItemRenderer<T extends class_1792 & GeoAnimatable> extends class_756 implements GeoRenderer<T> {
	protected final GeoRenderLayersContainer<T> renderLayers = new GeoRenderLayersContainer<>(this);
	protected final GeoModel<T> model;

	protected class_1799 currentItemStack;
	protected class_811 renderPerspective;
	protected T animatable;
	protected float scaleWidth = 1;
	protected float scaleHeight = 1;
	protected boolean useEntityGuiLighting = false;

	protected Matrix4f itemRenderTranslations = new Matrix4f();
	protected Matrix4f modelRenderTranslations = new Matrix4f();

	public GeoItemRenderer(GeoModel<T> model) {
		this(class_310.method_1551().method_31975(), class_310.method_1551().method_31974(),
				model);
	}

	public GeoItemRenderer(class_824 dispatcher, class_5599 modelSet, GeoModel<T> model) {
		super(dispatcher, modelSet);

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
	 * Returns the current ItemStack being rendered
	 */
	public class_1799 getCurrentItemStack() {
		return this.currentItemStack;
	}

	/**
	 * Mark this renderer so that it uses an alternate lighting scheme when rendering the item in GUI
	 * <p>
	 * This can help with improperly lit 3d models
	 */
	public GeoItemRenderer<T> useAlternateGuiLighting() {
		this.useEntityGuiLighting = true;

		return this;
	}

	/**
	 * Gets the id that represents the current animatable's instance for animation purposes
	 * <p>
	 * This is mostly useful for things like items, which have a single registered instance for all objects
	 */
	@Override
	public long getInstanceId(T animatable) {
		return GeoItem.getId(this.currentItemStack);
	}

	/**
	 * Shadowing override of {@link class_897#method_3931}
	 * <p>
	 * This redirects the call to {@link GeoRenderer#getTextureLocation}
	 */
	@Override
	public class_2960 getTextureLocation(T animatable) {
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
	public GeoItemRenderer<T> addRenderLayer(GeoRenderLayer<T> renderLayer) {
		this.renderLayers.addLayer(renderLayer);

		return this;
	}

	/**
	 * Sets a scale override for this renderer, telling GeckoLib to pre-scale the model
	 */
	public GeoItemRenderer<T> withScale(float scale) {
		return withScale(scale, scale);
	}

	/**
	 * Sets a scale override for this renderer, telling GeckoLib to pre-scale the model
	 */
	public GeoItemRenderer<T> withScale(float scaleWidth, float scaleHeight) {
		this.scaleWidth = scaleWidth;
		this.scaleHeight = scaleHeight;

		return this;
	}

	/**
	 * Called before rendering the model to buffer. Allows for render modifications and preparatory work such as scaling and translating
	 * <p>
	 * {@link class_4587} translations made here are kept until the end of the render process
	 */
	@Override
	public void preRender(class_4587 poseStack, T animatable, BakedGeoModel model, @Nullable class_4597 bufferSource, @Nullable class_4588 buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		this.itemRenderTranslations = new Matrix4f(poseStack.method_23760().method_23761());

		scaleModelForRender(this.scaleWidth, this.scaleHeight, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);

		if (!isReRender)
			poseStack.method_46416(0.5f, 0.51f, 0.5f);
	}

	@Override
	@ApiStatus.Internal
	public void method_3166(class_1799 stack, class_811 transformType, class_4587 poseStack,
			class_4597 bufferSource, int packedLight, int packedOverlay) {
		this.animatable = (T)stack.method_7909();
		this.currentItemStack = stack;
		this.renderPerspective = transformType;
		float partialTick = class_310.method_1551().method_60646().method_60637(true);

		if (transformType == class_811.field_4317) {
			renderInGui(transformType, poseStack, bufferSource, packedLight, packedOverlay, partialTick);
		}
		else {
			class_1921 renderType = getRenderType(this.animatable, getTextureLocation(this.animatable), bufferSource, partialTick);
			class_4588 buffer = class_918.method_29711(bufferSource, renderType, false, this.currentItemStack != null && this.currentItemStack.method_7958());

			defaultRender(poseStack, this.animatable, bufferSource, renderType, buffer,
					0, partialTick, packedLight);
		}

		this.animatable = null;
	}

	/**
	 * Wrapper method to handle rendering the item in a GUI context (defined by {@link class_811#field_4317} normally)
	 * <p>
	 * Just includes some additional required transformations and settings
	 */
	protected void renderInGui(class_811 transformType, class_4587 poseStack,
							   class_4597 bufferSource, int packedLight, int packedOverlay, float partialTick) {
		setupLightingForGuiRender();

		class_4597.class_4598 defaultBufferSource = bufferSource instanceof class_4597.class_4598 bufferSource2 ? bufferSource2 : class_310.method_1551().field_1769.field_20951.method_23000();
		class_1921 renderType = getRenderType(this.animatable, getTextureLocation(this.animatable), defaultBufferSource, partialTick);
		class_4588 buffer = class_918.method_29711(bufferSource, renderType, true, this.currentItemStack != null && this.currentItemStack.method_7958());

		poseStack.method_22903();
		defaultRender(poseStack, this.animatable, defaultBufferSource, renderType, buffer, 0, partialTick, packedLight);
		defaultBufferSource.method_22993();
		RenderSystem.enableDepthTest();
		class_308.method_24211();
		poseStack.method_22909();
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

		if (!isReRender) {
			AnimationState<T> animationState = new AnimationState<>(animatable, 0, 0, partialTick, false);
			long instanceId = getInstanceId(animatable);
			GeoModel<T> currentModel = getGeoModel();

			animationState.setData(DataTickets.TICK, animatable.getTick(this.currentItemStack));
			animationState.setData(DataTickets.ITEM_RENDER_PERSPECTIVE, this.renderPerspective);
			animationState.setData(DataTickets.ITEMSTACK, this.currentItemStack);
			animatable.getAnimatableInstanceCache().getManagerForId(instanceId).setData(DataTickets.ITEM_RENDER_PERSPECTIVE, this.renderPerspective);
			currentModel.addAdditionalStateData(animatable, instanceId, animationState::setData);
			currentModel.handleAnimations(animatable, instanceId, animationState, partialTick);
		}

		this.modelRenderTranslations = new Matrix4f(poseStack.method_23760().method_23761());

		if (buffer != null)
			GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
					packedLight, packedOverlay, colour);
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
			bone.setLocalSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.itemRenderTranslations));
		}

		GeoRenderer.super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay,
				colour);
	}

	/**
	 * Set the current lighting normals for the current render pass
	 * <p>
	 * Only used for {@link class_811#field_4317} rendering
	 */
	public void setupLightingForGuiRender() {
		if (this.useEntityGuiLighting) {
			class_308.method_34742();
		}
		else {
			class_308.method_24210();
		}
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
		AnimatableTexture.setAndUpdate(getTextureLocation(animatable));
	}

	/**
	 * Create and fire the relevant {@code CompileLayers} event hook for this renderer
	 */
	@Override
	public void fireCompileRenderLayersEvent() {
		GeckoLibServices.Client.EVENTS.fireCompileItemRenderLayers(this);
	}

	/**
	 * Create and fire the relevant {@code Pre-Render} event hook for this renderer
	 *
	 * @return Whether the renderer should proceed based on the cancellation state of the event
	 */
	@Override
	public boolean firePreRenderEvent(class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
		return GeckoLibServices.Client.EVENTS.fireItemPreRender(this, poseStack, model, bufferSource, partialTick, packedLight);
	}

	/**
	 * Create and fire the relevant {@code Post-Render} event hook for this renderer
	 */
	@Override
	public void firePostRenderEvent(class_4587 poseStack, BakedGeoModel model, class_4597 bufferSource, float partialTick, int packedLight) {
		GeckoLibServices.Client.EVENTS.fireItemPostRender(this, poseStack, model, bufferSource, partialTick, packedLight);
	}
}
