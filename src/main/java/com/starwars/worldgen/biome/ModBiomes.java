package com.starwars.worldgen.biome;

import com.starwars.StarWarsMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class ModBiomes {
    public static final RegistryKey<Biome> TATOOINE = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(StarWarsMod.MOD_ID, "tatooine"));

    public static void registerModBiomes() {
        StarWarsMod.LOGGER.info("Registering Mod Biomes for " + StarWarsMod.MOD_ID);
    }
}
