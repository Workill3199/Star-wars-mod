package software.bernie.geckolib.renderer.layer;

import net.minecraft.class_1058;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1738;
import net.minecraft.class_1741;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2190;
import net.minecraft.class_2484;
import net.minecraft.class_310;
import net.minecraft.class_3489;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4722;
import net.minecraft.class_5598;
import net.minecraft.class_5602;
import net.minecraft.class_572;
import net.minecraft.class_630;
import net.minecraft.class_630.class_628;
import net.minecraft.class_6880;
import net.minecraft.class_8053;
import net.minecraft.class_836;
import net.minecraft.class_9282;
import net.minecraft.class_9334;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.util.RenderUtil;

/**
 * Builtin class for handling dynamic armor rendering on GeckoLib entities
 * <p>
 * Supports both {@link GeoItem GeckoLib} and {@link class_1738 Vanilla} armor models
 * <p>
 * Unlike a traditional armor renderer, this renderer renders per-bone, giving much more flexible armor rendering
 */
public class ItemArmorGeoLayer<T extends class_1309 & GeoAnimatable> extends GeoRenderLayer<T> {
	protected static final class_572<class_1309> INNER_ARMOR_MODEL = new class_572<>(class_310.method_1551().method_31974().method_32072(class_5602.field_27579));
	protected static final class_572<class_1309> OUTER_ARMOR_MODEL = new class_572<>(class_310.method_1551().method_31974().method_32072(class_5602.field_27580));

	@Nullable
	protected class_1799 mainHandStack;
	@Nullable protected class_1799 offhandStack;
	@Nullable protected class_1799 helmetStack;
	@Nullable protected class_1799 chestplateStack;
	@Nullable protected class_1799 leggingsStack;
	@Nullable protected class_1799 bootsStack;

	public ItemArmorGeoLayer(GeoRenderer<T> geoRenderer) {
		super(geoRenderer);
	}

	/**
	 * Return an EquipmentSlot for a given {@link class_1799} and animatable instance
	 * <p>
	 * This is what determines the base model to use for rendering a particular stack
	 */
	@NotNull
	protected class_1304 getEquipmentSlotForBone(GeoBone bone, class_1799 stack, T animatable) {
		for (class_1304 slot : class_1304.values()) {
			if (slot.method_5925() == class_1304.class_1305.field_6178) {
				if (stack == animatable.method_6118(slot))
					return slot;
			}
		}

		return class_1304.field_6174;
	}

	/**
	 * Return a ModelPart for a given {@link GeoBone}
	 * <p>
	 * This is then transformed into position for the final render
	 */
	@NotNull
	protected class_630 getModelPartForBone(GeoBone bone, class_1304 slot, class_1799 stack, T animatable, class_572<?> baseModel) {
		return baseModel.field_3391;
	}

	/**
	 * Get the {@link class_1799} relevant to the bone being rendered
	 *
	 * @return The ItemStack for the bone being rendered, or null if this bone should be ignored
	 */
	@Nullable
	protected class_1799 getArmorItemForBone(GeoBone bone, T animatable) {
		return null;
	}

	/**
	 * This method is called by the {@link GeoRenderer} before rendering, immediately after {@link GeoRenderer#preRender} has been called
	 * <p>
	 * This allows for RenderLayers to perform pre-render manipulations such as hiding or showing bones
	 */
	@Override
	public void preRender(class_4587 poseStack, T animatable, BakedGeoModel bakedModel, @Nullable class_1921 renderType, class_4597 bufferSource,
						  @Nullable class_4588 buffer, float partialTick, int packedLight, int packedOverlay) {
		this.mainHandStack = animatable.method_6118(class_1304.field_6173);
		this.offhandStack = animatable.method_6118(class_1304.field_6171);
		this.helmetStack = animatable.method_6118(class_1304.field_6169);
		this.chestplateStack = animatable.method_6118(class_1304.field_6174);
		this.leggingsStack = animatable.method_6118(class_1304.field_6172);
		this.bootsStack = animatable.method_6118(class_1304.field_6166);
	}

	/**
	 * This method is called by the {@link GeoRenderer} for each bone being rendered
	 * <p>
	 * This is a more expensive call, particularly if being used to render something on a different buffer
	 * It does however have the benefit of having the matrix translations and other transformations already applied from render-time
	 * It's recommended to avoid using this unless necessary
	 * <p>
	 * The {@link GeoBone} in question has already been rendered by this stage
	 * <p>
	 * If you <i>do</i> use it, and you render something that changes the {@link class_4588 buffer}, you need to reset it back to the previous buffer
	 * using {@link class_4597#getBuffer} before ending the method
	 */
	@Override
	public void renderForBone(class_4587 poseStack, T animatable, GeoBone bone, class_1921 renderType, class_4597 bufferSource,
							  class_4588 buffer, float partialTick, int packedLight, int packedOverlay) {
		class_1799 armorStack = getArmorItemForBone(bone, animatable);

		if (armorStack == null)
			return;

		if (armorStack.method_7909() instanceof class_1747 blockItem && blockItem.method_7711() instanceof class_2190 skullBlock) {
			renderSkullAsArmor(poseStack, bone, armorStack, skullBlock, bufferSource, packedLight);
		}
		else {
			class_1304 slot = getEquipmentSlotForBone(bone, armorStack, animatable);
			class_572<?> model = getModelForItem(bone, slot, armorStack, animatable);
			class_630 modelPart = getModelPartForBone(bone, slot, armorStack, animatable, model);

			if (!modelPart.field_3663.isEmpty()) {
				poseStack.method_22903();
				poseStack.method_22905(-1, -1, 1);

				if (model instanceof GeoArmorRenderer<?> geoArmorRenderer) {
					prepModelPartForRender(poseStack, bone, modelPart);
					geoArmorRenderer.prepForRender(animatable, armorStack, slot, model);
					geoArmorRenderer.applyBoneVisibilityByPart(slot, modelPart, model);
					geoArmorRenderer.method_2828(poseStack, null, packedLight, packedOverlay, Color.WHITE.argbInt());
				}
				else if (armorStack.method_7909() instanceof class_1738) {
					prepModelPartForRender(poseStack, bone, modelPart);
					renderVanillaArmorPiece(poseStack, animatable, bone, slot, armorStack, modelPart, bufferSource, partialTick, packedLight, packedOverlay);
				}

				poseStack.method_22909();
			}
		}
	}

	/**
	 * Renders an individual armor piece base on the given {@link GeoBone} and {@link class_1799}
	 */
	protected <I extends class_1792 & GeoItem> void renderVanillaArmorPiece(class_4587 poseStack, T animatable, GeoBone bone, class_1304 slot, class_1799 armorStack,
															   class_630 modelPart, class_4597 bufferSource, float partialTick, int packedLight, int packedOverlay) {
		class_6880<class_1741> material = ((class_1738)armorStack.method_7909()).method_7686();

		for (class_1741.class_9196 layer : material.comp_349().comp_2302()) {
			int color = armorStack.method_31573(class_3489.field_48803) ? class_9282.method_57470(armorStack, -6265536) : -1;
			class_4588 buffer = getVanillaArmorBuffer(bufferSource, animatable, armorStack, slot, bone, layer, packedLight, packedOverlay, false);

			modelPart.method_22699(poseStack, buffer, packedLight, packedOverlay, color);
		}

		class_8053 trim = armorStack.method_57824(class_9334.field_49607);

		if (trim != null) {
			class_1058 sprite = class_310.method_1551().method_1554().method_24153(class_4722.field_42071).method_4608(slot == class_1304.field_6172 ? trim.method_48434(material) : trim.method_48436(material));
			class_4588 buffer = sprite.method_24108(bufferSource.getBuffer(class_4722.method_48480(trim.method_48424().comp_349().comp_1905())));
			modelPart.method_22698(poseStack, buffer, packedLight, packedOverlay);
		}

		if (armorStack.method_7958())
			modelPart.method_22699(poseStack, getVanillaArmorBuffer(bufferSource, animatable, armorStack, slot, bone, null, packedLight, packedOverlay, true), packedLight, packedOverlay, Color.WHITE.argbInt());
	}

	/**
	 * Returns the standard VertexConsumer for armor rendering from the given buffer source
	 *
	 * @param bufferSource The BufferSource to draw the buffer from
	 * @param animatable The GeoAnimatable the armor piece is rendering on
	 * @param stack The ItemStack for the armor piece
	 * @param slot The EquipmentSlot the armor piece is in
	 * @param bone The GeoBone related to the armor piece
	 * @param layer The currently rendering material layer for the armor piece, or null if not rendering a specific layer
	 * @param packedLight The packed sky/block light value for the entity the armor piece is rendering on
	 * @param packedOverlay The packed red/white overlay value for the entity the armor piece is rendering on
	 * @return The buffer to draw to
	 */
	protected class_4588 getVanillaArmorBuffer(class_4597 bufferSource, T animatable, class_1799 stack, class_1304 slot, GeoBone bone, @Nullable class_1741.class_9196 layer, int packedLight, int packedOverlay, boolean forGlint) {
		if (forGlint)
			return bufferSource.getBuffer(class_1921.method_27949());

		return bufferSource.getBuffer(class_1921.method_25448(layer.method_56693(slot == class_1304.field_6172)));
	}

	/**
	 * Returns a cached instance of a base HumanoidModel that is used for rendering/modelling the provided {@link class_1799}
	 */
	@NotNull
	protected class_572<?> getModelForItem(GeoBone bone, class_1304 slot, class_1799 stack, T animatable) {
		class_572<class_1309> defaultModel = slot == class_1304.field_6172 ? INNER_ARMOR_MODEL : OUTER_ARMOR_MODEL;
		
		return GeckoLibServices.Client.ITEM_RENDERING.getArmorModelForItem(animatable, stack, slot, defaultModel);
	}

	/**
	 * Render a given {@link class_2190} as a worn armor piece in relation to a given {@link GeoBone}
	 */
	protected void renderSkullAsArmor(class_4587 poseStack, GeoBone bone, class_1799 stack, class_2190 skullBlock, class_4597 bufferSource, int packedLight) {
		class_2484.class_2485 type = skullBlock.method_9327();
		class_5598 model = class_836.method_32160(class_310.method_1551().method_31974()).get(type);
		class_1921 renderType = class_836.method_3578(type, stack.method_57824(class_9334.field_49617));

		poseStack.method_22903();
		RenderUtil.translateAndRotateMatrixForBone(poseStack, bone);
		poseStack.method_22905(1.1875f, 1.1875f, 1.1875f);
		poseStack.method_46416(-0.5f, 0, -0.5f);
		class_836.method_32161(null, 0, 0, poseStack, bufferSource, packedLight, model, renderType);
		poseStack.method_22909();
	}

	/**
	 * Prepares the given {@link class_630} for render by setting its translation, position, and rotation values based on the provided {@link GeoBone}
	 *
	 * @param poseStack The PoseStack being used for rendering
	 * @param bone The GeoBone to base the translations on
	 * @param sourcePart The ModelPart to translate
	 */
	protected void prepModelPartForRender(class_4587 poseStack, GeoBone bone, class_630 sourcePart) {
		final GeoCube firstCube = bone.getCubes().get(0);
		final class_628 armorCube = sourcePart.field_3663.get(0);
		final double armorBoneSizeX = firstCube.size().method_10216();
		final double armorBoneSizeY = firstCube.size().method_10214();
		final double armorBoneSizeZ = firstCube.size().method_10215();
		final double actualArmorSizeX = Math.abs(armorCube.field_3648 - armorCube.field_3645);
		final double actualArmorSizeY = Math.abs(armorCube.field_3647 - armorCube.field_3644);
		final double actualArmorSizeZ = Math.abs(armorCube.field_3646 - armorCube.field_3643);
		float scaleX = (float)(armorBoneSizeX / actualArmorSizeX);
		float scaleY = (float)(armorBoneSizeY / actualArmorSizeY);
		float scaleZ = (float)(armorBoneSizeZ / actualArmorSizeZ);

		sourcePart.method_2851(-(bone.getPivotX() - ((bone.getPivotX() * scaleX) - bone.getPivotX()) / scaleX),
				-(bone.getPivotY() - ((bone.getPivotY() * scaleY) - bone.getPivotY()) / scaleY),
				(bone.getPivotZ() - ((bone.getPivotZ() * scaleZ) - bone.getPivotZ()) / scaleZ));

		sourcePart.field_3654 = -bone.getRotX();
		sourcePart.field_3675 = -bone.getRotY();
		sourcePart.field_3674 = bone.getRotZ();

		poseStack.method_22905(scaleX, scaleY, scaleZ);
	}
}
