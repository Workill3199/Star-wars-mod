package software.bernie.geckolib.model;

import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * {@link DefaultedGeoModel} specific to {@link net.minecraft.class_1297 Entities}
 * <p>
 * Using this class pre-sorts provided asset paths into the "entity" subdirectory
 * <p>
 * Additionally it can automatically handle head-turning if the entity has a "head" bone
 */
public class DefaultedEntityGeoModel<T extends GeoAnimatable> extends DefaultedGeoModel<T> {
	protected final boolean turnsHead;

	/**
	 * Create a new instance of this model class
	 * <p>
	 * The asset path should be the truncated relative path from the base folder
	 * <p>
	 * E.G.
	 * <pre>{@code
	 * 	new ResourceLocation("myMod", "animals/red_fish")
	 * }</pre>
	 */
	public DefaultedEntityGeoModel(class_2960 assetSubpath) {
		this(assetSubpath, false);
	}

	public DefaultedEntityGeoModel(class_2960 assetSubpath, boolean turnsHead) {
		super(assetSubpath);

		this.turnsHead = turnsHead;
	}

	/**
	 * Returns the subtype string for this type of model
	 * <p>
	 * This allows for sorting of asset files into neat subdirectories for clean management.
	 */
	@Override
	protected String subtype() {
		return "entity";
	}

	@Override
	public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
		if (!this.turnsHead)
			return;

		GeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * class_3532.field_29847);
			head.setRotY(entityData.netHeadYaw() * class_3532.field_29847);
		}
	}

	/**
	 * Changes the constructor-defined model path for this model to an alternate
	 * <p>
	 * This is useful if your animatable shares a model path with another animatable that differs in path to the texture and animations for this model
	 */
	@Override
	public DefaultedEntityGeoModel<T> withAltModel(class_2960 altPath) {
		return (DefaultedEntityGeoModel<T>)super.withAltModel(altPath);
	}

	/**
	 * Changes the constructor-defined animations path for this model to an alternate
	 * <p>
	 * This is useful if your animatable shares an animations path with another animatable that differs in path to the model and texture for this model
	 */
	@Override
	public DefaultedEntityGeoModel<T> withAltAnimations(class_2960 altPath) {
		return (DefaultedEntityGeoModel<T>)super.withAltAnimations(altPath);
	}

	/**
	 * Changes the constructor-defined texture path for this model to an alternate
	 * <p>
	 * This is useful if your animatable shares a texture path with another animatable that differs in path to the model and animations for this model
	 */
	@Override
	public DefaultedEntityGeoModel<T> withAltTexture(class_2960 altPath) {
		return (DefaultedEntityGeoModel<T>)super.withAltTexture(altPath);
	}
}
