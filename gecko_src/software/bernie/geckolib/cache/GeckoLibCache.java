package software.bernie.geckolib.cache;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.FileLoader;
import software.bernie.geckolib.loading.json.FormatVersion;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.CompoundException;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;
import net.minecraft.class_3302.class_4045;
import net.minecraft.class_3304;
import net.minecraft.class_3695;

/**
 * Cache class for holding loaded {@link Animation Animations}
 * and {@link GeoModel Models}
 */
public final class GeckoLibCache {
	private static final Set<String> EXCLUDED_NAMESPACES = ObjectOpenHashSet.of("moreplayermodels", "customnpcs", "gunsrpg");

	private static Map<class_2960, BakedAnimations> ANIMATIONS = Collections.emptyMap();
	private static Map<class_2960, BakedGeoModel> MODELS = Collections.emptyMap();

	public static Map<class_2960, BakedAnimations> getBakedAnimations() {
		return ANIMATIONS;
	}

	public static Map<class_2960, BakedGeoModel> getBakedModels() {
		return MODELS;
	}

	public static void registerReloadListener() {
		class_310 mc = class_310.method_1551();

		if (mc != null && mc.method_1478() instanceof class_3304 resourceManager)
			resourceManager.method_14477(GeckoLibCache::reload);
	}

	public static CompletableFuture<Void> reload(class_4045 stage, class_3300 resourceManager,
			class_3695 preparationsProfiler, class_3695 reloadProfiler, Executor backgroundExecutor,
			Executor gameExecutor) {
		Map<class_2960, BakedAnimations> animations = new Object2ObjectOpenHashMap<>();
		Map<class_2960, BakedGeoModel> models = new Object2ObjectOpenHashMap<>();

		return CompletableFuture.allOf(
				loadAnimations(backgroundExecutor, resourceManager, animations::put),
				loadModels(backgroundExecutor, resourceManager, models::put))
				.thenCompose(stage::method_18352).thenAcceptAsync(empty -> {
					GeckoLibCache.ANIMATIONS = animations;
					GeckoLibCache.MODELS = models;
				}, gameExecutor);
	}

	private static CompletableFuture<Void> loadAnimations(Executor backgroundExecutor, class_3300 resourceManager, BiConsumer<class_2960, BakedAnimations> elementConsumer) {
		return loadResources(backgroundExecutor, resourceManager, "animations", resource -> {
			try {
				return FileLoader.loadAnimationsFile(resource, resourceManager);
			}
			catch (CompoundException ex) {
				ex.withMessage(resource.toString() + ": Error loading animation file").printStackTrace();

				return new BakedAnimations(new Object2ObjectOpenHashMap<>());
			}
			catch (Exception ex) {
				throw GeckoLibConstants.exception(resource, "Error loading animation file", ex);
			}
		}, elementConsumer);
	}

	private static CompletableFuture<Void> loadModels(Executor backgroundExecutor, class_3300 resourceManager, BiConsumer<class_2960, BakedGeoModel> elementConsumer) {
		return loadResources(backgroundExecutor, resourceManager, "geo", resource -> {
			try {
				Model model = FileLoader.loadModelFile(resource, resourceManager);

				if (model.formatVersion() != FormatVersion.V_1_12_0)
					throw new IllegalArgumentException("Unsupported geometry json version. Supported versions: 1.12.0");

				return BakedModelFactory.getForNamespace(resource.method_12836()).constructGeoModel(GeometryTree.fromModel(model));
			}
			catch (Exception ex) {
				throw GeckoLibConstants.exception(resource, "Error loading model file", ex);
			}
		}, elementConsumer);
	}

	private static <T> CompletableFuture<Void> loadResources(Executor executor, class_3300 resourceManager,
			String type, Function<class_2960, T> loader, BiConsumer<class_2960, T> map) {
		return CompletableFuture.supplyAsync(
				() -> resourceManager.method_14488(type, fileName -> fileName.toString().endsWith(".json")), executor)
				.thenApplyAsync(resources -> {
					Map<class_2960, CompletableFuture<T>> tasks = new Object2ObjectOpenHashMap<>();

					for (class_2960 resource : resources.keySet()) {
						tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));
					}

					return tasks;
				}, executor)
				.thenAcceptAsync(tasks -> {
					for (Entry<class_2960, CompletableFuture<T>> entry : tasks.entrySet()) {
						// Skip known namespaces that use an "animation" or "geo" folder as well
						if (!EXCLUDED_NAMESPACES.contains(entry.getKey().method_12836().toLowerCase(Locale.ROOT)))
							map.accept(entry.getKey(), entry.getValue().join());
					}
				}, executor);
	}

	/**
	 * Register a new namespace to be excluded from GeckoLib's resource loader
	 *
	 * @param namespace The namespace to exclude
	 */
	public static synchronized void registerNamespaceExclusion(String namespace) {
		EXCLUDED_NAMESPACES.add(namespace);
	}
}
