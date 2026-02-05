package com.starwars.world.gen;

import com.starwars.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.DURASTEEL_ORE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.KYBER_CRYSTAL_ORE_PLACED_KEY);
        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(net.minecraft.world.biome.BiomeKeys.DESERT),
                GenerationStep.Feature.SURFACE_STRUCTURES, ModPlacedFeatures.TATOOINE_MARKET_PLACED_KEY);
        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(net.minecraft.world.biome.BiomeKeys.PLAINS, net.minecraft.world.biome.BiomeKeys.SAVANNA, net.minecraft.world.biome.BiomeKeys.BADLANDS),
                GenerationStep.Feature.SURFACE_STRUCTURES, ModPlacedFeatures.ENEMY_OUTPOST_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(net.minecraft.world.biome.BiomeKeys.FOREST, net.minecraft.world.biome.BiomeKeys.JUNGLE, net.minecraft.world.biome.BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.SURFACE_STRUCTURES, ModPlacedFeatures.JEDI_TEMPLE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(net.minecraft.world.biome.BiomeKeys.DESERT, net.minecraft.world.biome.BiomeKeys.BADLANDS),
                GenerationStep.Feature.SURFACE_STRUCTURES, ModPlacedFeatures.DROID_FACTORY_PLACED_KEY);
        
        ModEntityGeneration.addEntityGeneration();
    }
}
