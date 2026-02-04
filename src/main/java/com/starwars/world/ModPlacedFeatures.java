package com.starwars.world;

import com.starwars.StarWarsMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> DURASTEEL_ORE_PLACED_KEY = registerKey("durasteel_ore_placed");
    public static final RegistryKey<PlacedFeature> KYBER_CRYSTAL_ORE_PLACED_KEY = registerKey("kyber_crystal_ore_placed");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(StarWarsMod.MOD_ID, name));
    }
}
