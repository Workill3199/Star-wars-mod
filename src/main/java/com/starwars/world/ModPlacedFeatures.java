package com.starwars.world;

import com.starwars.StarWarsMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> DURASTEEL_ORE_PLACED_KEY = registerKey("durasteel_ore_placed");
    public static final RegistryKey<PlacedFeature> KYBER_CRYSTAL_ORE_PLACED_KEY = registerKey("kyber_crystal_ore_placed");
    public static final RegistryKey<PlacedFeature> TATOOINE_MARKET_PLACED_KEY = registerKey("tatooine_market_placed");
    public static final RegistryKey<PlacedFeature> ENEMY_OUTPOST_PLACED_KEY = registerKey("enemy_outpost_placed");
    public static final RegistryKey<PlacedFeature> JEDI_TEMPLE_PLACED_KEY = registerKey("jedi_temple_placed");
    public static final RegistryKey<PlacedFeature> HIGH_TECH_JEDI_TEMPLE_PLACED_KEY = registerKey("high_tech_jedi_temple_placed");
    public static final RegistryKey<PlacedFeature> DROID_FACTORY_PLACED_KEY = registerKey("droid_factory_placed");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(StarWarsMod.MOD_ID, name));
    }
}
