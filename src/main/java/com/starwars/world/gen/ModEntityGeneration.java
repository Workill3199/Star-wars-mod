package com.starwars.world.gen;

import com.starwars.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntityGeneration {
    public static void addEntityGeneration() {
        // Jawas - Desert (Keep natural spawn for now, or move to village generation later)
        // BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT),
        //        SpawnGroup.CREATURE, ModEntities.JAWA, 35, 2, 4);

        // Ewoks - Forests
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA),
                SpawnGroup.CREATURE, ModEntities.EWOK, 35, 3, 5);
                
        // C-3PO - Disabled natural spawn (Craftable)
        // BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT, BiomeKeys.PLAINS),
        //        SpawnGroup.CREATURE, ModEntities.C3PO, 10, 1, 1);
                
        // R2-D2 - Disabled natural spawn (Craftable)
        // BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT, BiomeKeys.PLAINS),
        //        SpawnGroup.CREATURE, ModEntities.R2D2, 10, 1, 1);

        // Stormtroopers - Disabled natural spawn (Structure only)
        // BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.DESERT, BiomeKeys.FOREST, BiomeKeys.SAVANNA, BiomeKeys.SNOWY_PLAINS),
        //        SpawnGroup.MONSTER, ModEntities.STORMTROOPER, 50, 2, 4);

        // Battle Droids - Disabled natural spawn (Structure only)
        // BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.DESERT, BiomeKeys.SAVANNA, BiomeKeys.BADLANDS),
        //        SpawnGroup.MONSTER, ModEntities.BATTLE_DROID, 50, 3, 5);
    }
}
