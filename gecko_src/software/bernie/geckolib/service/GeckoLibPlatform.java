package software.bernie.geckolib.service;

import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.class_9331;

/**
 * Loader-agnostic service interface for general loader-specific functions
 */
public interface GeckoLibPlatform {
    /**
     * @return Whether the current runtime is an in-dev (non-production) environment, for running debug-only tasks
     */
    boolean isDevelopmentEnvironment();

    /**
     * @return Whether the current runtime is on the client side regardless of logical context
     */
    boolean isPhysicalClient();

    /**
     * @return The root game directory (./run)
     */
    Path getGameDir();

    /**
     * Register a {@link class_9331}
     * <p>
     * This is mostly just used for storing the animatable ID on ItemStacks
     */
    <T> Supplier<class_9331<T>> registerDataComponent(String id, UnaryOperator<class_9331.class_9332<T>> builder);
}
