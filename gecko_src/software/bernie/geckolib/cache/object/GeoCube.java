package software.bernie.geckolib.cache.object;

import net.minecraft.class_243;

/**
 * Baked cuboid for a {@link GeoBone}
 */
public record GeoCube(GeoQuad[] quads, class_243 pivot, class_243 rotation, class_243 size, double inflate, boolean mirror) {}
