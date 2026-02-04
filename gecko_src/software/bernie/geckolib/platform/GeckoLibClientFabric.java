package software.bernie.geckolib.platform;

import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_572;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.service.GeckoLibClient;

/**
 * Fabric service implementation for clientside functionalities
 */
public class GeckoLibClientFabric implements GeckoLibClient {
    /**
     * Helper method for retrieving an (ideally) cached instance of the armor model for a given Item
     * <p>
     * If no custom model applies to this item, the {@code defaultModel} is returned
     */
    @NotNull
    @Override
    public <T extends class_1309 & GeoAnimatable> class_572<?> getArmorModelForItem(T animatable, class_1799 stack, class_1304 slot, class_572<class_1309> defaultModel) {
        return GeoRenderProvider.of(stack).getGeoArmorRenderer(animatable, stack, slot, defaultModel) instanceof GeoArmorRenderer<?> geoArmorRenderer ? geoArmorRenderer : defaultModel;
    }

    /**
     * Helper method for retrieving an (ideally) cached instance of the GeoModel for the given item
     *
     * @return The GeoModel for the item, or null if not applicable
     */
    @Nullable
    @Override
    public GeoModel<?> getGeoModelForItem(class_1799 item) {
        if (GeoRenderProvider.of(item).getGeoItemRenderer() instanceof GeoRenderer<?> geoItemRenderer)
            return geoItemRenderer.getGeoModel();

        return null;
    }

    /**
     * Helper method for retrieving an (ideally) cached instance of the GeoModel for the given item's armor renderer
     *
     * @return The GeoModel for the item, or null if not applicable
     */
    @Nullable
    @Override
    public GeoModel<?> getGeoModelForArmor(class_1799 armour) {
        if (GeoRenderProvider.of(armour).getGeoArmorRenderer(null, armour, null, null) instanceof GeoArmorRenderer<?> armorRenderer)
            return armorRenderer.getGeoModel();

        return null;
    }
}
