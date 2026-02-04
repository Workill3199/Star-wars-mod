package software.bernie.geckolib.service;

import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_572;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

/**
 * Loader-agnostic service interface for clientside functionalities
 */
public interface GeckoLibClient {
    /**
     * Helper method for retrieving an (ideally) cached instance of the armor model for a given Item
     * <p>
     * If no custom model applies to this item, the {@code defaultModel} is returned
     */
    @NotNull
    <T extends class_1309 & GeoAnimatable> class_572<?> getArmorModelForItem(T animatable, class_1799 stack, class_1304 slot, class_572<class_1309> defaultModel);

    /**
     * Helper method for retrieving an (ideally) cached instance of the GeoModel for the given item
     *
     * @return The GeoModel for the item, or null if not applicable
     */
    @Nullable
    GeoModel<?> getGeoModelForItem(class_1799 item);

    /**
     * Helper method for retrieving an (ideally) cached instance of the GeoModel for the given item's armor renderer
     *
     * @return The GeoModel for the item, or null if not applicable
     */
    @Nullable
    GeoModel<?> getGeoModelForArmor(class_1799 armour);
}
