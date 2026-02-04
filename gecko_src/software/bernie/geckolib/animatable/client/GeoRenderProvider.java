package software.bernie.geckolib.animatable.client;

import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_572;
import net.minecraft.class_756;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/**
 * Internal interface for safely providing a custom item and armor renderer instances at runtime
 * <p>
 * This can be safely instantiated as a new anonymous class inside your {@link class_1792} class
 * <p>
 * This is a functional equivalent to Forge's {@code IClientItemExtensions}. If on Forge, either can be used
 *
 * @see software.bernie.geckolib.renderer.GeoItemRenderer GeoItemRenderer
 * @see software.bernie.geckolib.renderer.GeoArmorRenderer GeoArmorRenderer
 */
public interface GeoRenderProvider {
    GeoRenderProvider DEFAULT = new GeoRenderProvider() {};

    /**
     * Get the GeoRenderProvider instance for the given ItemStack, if applicable
     *
     * @param itemStack The ItemStack to get the provider instance for
     * @return The GeoRenderProvider instance for this stack, or a defaulted empty instance if not defined
     */
    static GeoRenderProvider of(class_1799 itemStack) {
        return of(itemStack.method_7909());
    }

    /**
     * Get the GeoRenderProvider instance for the given Item, if applicable
     *
     * @param item The ItemStack to get the provider instance for
     * @return The GeoRenderProvider instance for this item, or a defaulted empty instance if not defined
     */
    static GeoRenderProvider of(class_1792 item) {
        if (item instanceof GeoItem geoItem)
            return (GeoRenderProvider)geoItem.getRenderProvider();

        return DEFAULT;
    }

    /**
     * Get the cached {@link class_756} instance for this provider.
     * <p>
     * Normally this would be an instance of {@link GeoItemRenderer}
     *
     * @return The cached BEWLR instance for this provider, or null if not applicable
     */
    @Nullable
    default class_756 getGeoItemRenderer() {
        return null;
    }

    /**
     * Get the cached {@link GeoArmorRenderer} instance for this provider.
     * <p>
     * Normally this would be an instance of {@link GeoArmorRenderer}
     *
     * @param livingEntity The entity currently wearing the item, if applicable
     * @param itemStack The ItemStack for this provider
     * @param equipmentSlot The slot the ItemStack is currently in, if applicable
     * @param original The base HumanoidModel (usually the default vanilla armor model), if applicable
     * @return The cached HumanoidModel instance for this provider, or null if not applicable
     */
    @Nullable
    default <T extends class_1309> class_572<?> getGeoArmorRenderer(@Nullable T livingEntity, class_1799 itemStack, @Nullable class_1304 equipmentSlot, @Nullable class_572<T> original) {
        return null;
    }
}