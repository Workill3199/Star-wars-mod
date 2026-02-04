package software.bernie.geckolib.cache;

import net.minecraft.class_18;
import net.minecraft.class_2487;
import net.minecraft.class_3218;
import net.minecraft.class_7225;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;

/**
 * Storage class that keeps track of the last animatable id used, and provides new ones on request
 * <p>
 * Generally only used for {@link net.minecraft.class_1792 Items}, but any {@link SingletonAnimatableInstanceCache singleton} will likely use this.
 */
public final class AnimatableIdCache extends class_18 {
	private static final class_8645<AnimatableIdCache> FACTORY = new class_8645<>(AnimatableIdCache::new, AnimatableIdCache::new, null);
	private static final String DATA_KEY = "geckolib_id_cache";
	private long lastId;

	private AnimatableIdCache() {}

	private AnimatableIdCache(class_2487 tag, class_7225.class_7874 registryLookup) {
		this.lastId = tag.method_10537("last_id");
	}

	/**
	 * Get the next free id from the id cache
	 *
	 * @param level An arbitrary ServerLevel. It doesn't matter which one
	 * @return The next free ID, which is immediately reserved for use after calling this method
	 */
	public static long getFreeId(class_3218 level) {
		return getCache(level).getNextId();
	}

	private long getNextId() {
		method_80();

		return ++this.lastId;
	}

	@Override
	public class_2487 method_75(class_2487 tag, class_7225.class_7874 registryLookup) {
		tag.method_10544("last_id", this.lastId);

		return tag;
	}

	private static AnimatableIdCache getCache(class_3218 level) {
		return level.method_8503().method_30002().method_17983().method_17924(FACTORY, DATA_KEY);
	}
}
