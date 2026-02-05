package com.starwars.world.gen.feature;

import com.mojang.serialization.Codec;
import com.starwars.StarWarsMod;
import com.starwars.block.ModBlocks;
import com.starwars.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class DroidFactoryFeature extends Feature<DefaultFeatureConfig> {
    public DroidFactoryFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        // Simple check to ensure we are on ground
        if (!world.getBlockState(origin.down()).isSolidBlock(world, origin.down())) {
            return false;
        }

        BlockState floor = ModBlocks.IMPERIAL_PLATING.getDefaultState();
        BlockState wall = ModBlocks.DURASTEEL_PLATING.getDefaultState();
        BlockState glass = Blocks.TINTED_GLASS.getDefaultState();
        BlockState iron = Blocks.IRON_BLOCK.getDefaultState();

        int width = 15;
        int length = 25;
        int height = 8;

        // Factory Floor
        for (int x = -width/2; x <= width/2; x++) {
            for (int z = 0; z < length; z++) {
                world.setBlockState(origin.add(x, 0, z), floor, 3);
                // Roof
                world.setBlockState(origin.add(x, height, z), wall, 3);
                
                // Walls
                if (x == -width/2 || x == width/2 || z == 0 || z == length - 1) {
                    for (int y = 1; y < height; y++) {
                         // Windows
                        if (y > 2 && y < height - 1 && (x != -width/2 && x != width/2)) {
                            world.setBlockState(origin.add(x, y, z), glass, 3);
                        } else {
                            world.setBlockState(origin.add(x, y, z), wall, 3);
                        }
                    }
                } else {
                    // Interior Air
                    for (int y = 1; y < height; y++) {
                        world.setBlockState(origin.add(x, y, z), Blocks.AIR.getDefaultState(), 3);
                    }
                }
            }
        }

        // Assembly Line (Conveyor Belt Look)
        for (int z = 3; z < length - 3; z++) {
            world.setBlockState(origin.add(0, 1, z), Blocks.POLISHED_ANDESITE.getDefaultState(), 3);
            if (z % 4 == 0) {
                 // Machinery
                 world.setBlockState(origin.add(0, 2, z), iron, 3);
                 world.setBlockState(origin.add(0, 3, z), ModBlocks.CIRCUIT_TABLE.getDefaultState(), 3);
            }
        }

        // Lava Vats (Traps)
        for (int x : new int[]{-4, 4}) {
            for (int z = 5; z < 10; z++) {
                world.setBlockState(origin.add(x, 0, z), Blocks.LAVA.getDefaultState(), 3);
                world.setBlockState(origin.add(x, 1, z), Blocks.AIR.getDefaultState(), 3);
            }
        }

        // Loot Chests
        BlockPos lootPos1 = origin.add(-6, 1, length - 2);
        world.setBlockState(lootPos1, Blocks.CHEST.getDefaultState(), 3);
        LootableContainerBlockEntity.setLootTable(world, random, lootPos1, RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/droid_factory")));

        BlockPos lootPos2 = origin.add(6, 1, length - 2);
        world.setBlockState(lootPos2, Blocks.CHEST.getDefaultState(), 3);
        LootableContainerBlockEntity.setLootTable(world, random, lootPos2, RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/droid_factory")));


        // Spawn Enemies (Heavy Guard)
        int enemyCount = 8 + random.nextInt(5);
        for (int i = 0; i < enemyCount; i++) {
            double offsetX = (random.nextDouble() - 0.5) * (width - 2);
            double offsetZ = (random.nextDouble() * (length - 2));
            
            if (random.nextBoolean()) {
                var droid = ModEntities.BATTLE_DROID.create(world.toServerWorld());
                if (droid != null) {
                    droid.refreshPositionAndAngles(origin.getX() + offsetX, origin.getY() + 1, origin.getZ() + offsetZ, random.nextFloat() * 360, 0);
                    droid.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
                    world.spawnEntity(droid);
                }
            } else {
                 // Commander Stormtrooper
                 var stormtrooper = ModEntities.STORMTROOPER.create(world.toServerWorld());
                if (stormtrooper != null) {
                    stormtrooper.refreshPositionAndAngles(origin.getX() + offsetX, origin.getY() + 1, origin.getZ() + offsetZ, random.nextFloat() * 360, 0);
                    stormtrooper.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
                    world.spawnEntity(stormtrooper);
                }
            }
        }

        return true;
    }
}
