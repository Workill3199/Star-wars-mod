package com.starwars.worldgen.dimension;

import com.starwars.StarWarsMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
    public static final RegistryKey<DimensionType> SPACE_DIM_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, Identifier.of(StarWarsMod.MOD_ID, "space"));
    public static final RegistryKey<World> SPACE_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(StarWarsMod.MOD_ID, "space"));

    public static void register() {
        StarWarsMod.LOGGER.info("Registering Dimensions for " + StarWarsMod.MOD_ID);
    }
}
