package software.bernie.geckolib.animatable;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.constant.dataticket.SerializableDataTicket;

import java.util.function.Consumer;
import net.minecraft.class_1297;
import net.minecraft.class_1299;

/**
 * The {@link GeoAnimatable} interface specific to {@link class_1297 Entities}
 * <p>
 * This interface is <u>specifically</u> for entities replacing the rendering of other, existing entities
 *
 * @see <a href="https://github.com/bernie-g/geckolib/wiki/Entity-Animations">GeckoLib Wiki - Entity Animations</a>
 */
public interface GeoReplacedEntity extends SingletonGeoAnimatable {
	/**
	 * Returns the {@link class_1299} this entity is intending to replace
	 * <p>
	 * This is used for rendering an animation purposes.
	 */
	class_1299<?> getReplacingEntityType();

	/**
	 * Get server-synced animation data via its relevant {@link SerializableDataTicket}
	 * <p>
	 * Should only be used on the <u>client-side</u>
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param entity The entity instance relevant to the data being set
	 * @param dataTicket The data ticket for the data to retrieve
	 * @return The synced data, or null if no data of that type has been synced
	 */
	@ApiStatus.NonExtendable
	@Nullable
	default <D> D getAnimData(class_1297 entity, SerializableDataTicket<D> dataTicket) {
		return getAnimatableInstanceCache().getManagerForId(entity.method_5628()).getData(dataTicket);
	}

	/**
	 * Saves an arbitrary syncable piece of data to this animatable's {@link AnimatableManager}
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param relatedEntity An entity related to the state of the data for syncing
	 * @param dataTicket The DataTicket to sync the data for
	 * @param data The data to sync
	 */
	@ApiStatus.NonExtendable
	default <D> void setAnimData(class_1297 relatedEntity, SerializableDataTicket<D> dataTicket, D data) {
		if (relatedEntity.method_37908().method_8608()) {
			getAnimatableInstanceCache().getManagerForId(relatedEntity.method_5628()).setData(dataTicket, data);
		}
		else {
			GeckoLibServices.NETWORK.syncEntityAnimData(relatedEntity, true, dataTicket, data);
		}
	}

	/**
	 * Trigger an animation for this Entity, based on the controller name and animation name
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param relatedEntity An entity related to the state of the data for syncing
	 * @param controllerName The name of the controller name the animation belongs to, or null to do an inefficient lazy search
	 * @param animName The name of animation to trigger. This needs to have been registered with the controller via {@link AnimationController#triggerableAnim AnimationController.triggerableAnim}
	 */
	@ApiStatus.NonExtendable
	default void triggerAnim(class_1297 relatedEntity, @Nullable String controllerName, String animName) {
		if (relatedEntity.method_37908().method_8608()) {
			getAnimatableInstanceCache().getManagerForId(relatedEntity.method_5628()).tryTriggerAnimation(controllerName, animName);
		}
		else {
			GeckoLibServices.NETWORK.triggerEntityAnim(relatedEntity, true, controllerName, animName);
		}
	}
	
	/**
	 * Returns the current age/tick of the animatable instance
	 * <p>
	 * By default this is just the animatable's age in ticks, but this method allows for non-ticking custom animatables to provide their own values
	 *
	 * @param entity The Entity representing this animatable
	 * @return The current tick/age of the animatable, for animation purposes
	 */
	@Override
	default double getTick(Object entity) {
		return ((class_1297)entity).field_6012;
	}

	// These methods aren't used for GeoReplacedEntity
	@ApiStatus.NonExtendable
	@Override
	default void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {}

	// These methods aren't used for GeoReplacedEntity
	@ApiStatus.NonExtendable
	@Override
	default Object getRenderProvider() {
		return null;
	}
}
