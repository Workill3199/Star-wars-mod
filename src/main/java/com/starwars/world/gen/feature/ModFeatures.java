package com.starwars.world.gen.feature;

import com.starwars.StarWarsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> TATOOINE_MARKET = registerFeature("tatooine_market",
            new TatooineMarketFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ENEMY_OUTPOST = registerFeature("enemy_outpost",
            new EnemyOutpostFeature(DefaultFeatureConfig.CODEC));

    private static <C extends net.minecraft.world.gen.feature.FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registries.FEATURE, Identifier.of(StarWarsMod.MOD_ID, name), feature);
    }

    public static void registerFeatures() {
        StarWarsMod.LOGGER.info("Registering Mod Features for " + StarWarsMod.MOD_ID);
    }
}
