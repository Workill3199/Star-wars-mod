package com.starwars.world.gen.feature;

import com.mojang.serialization.Codec;
import com.starwars.block.ModBlocks;
import com.starwars.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class EnemyOutpostFeature extends Feature<DefaultFeatureConfig> {
    public EnemyOutpostFeature(Codec<DefaultFeatureConfig> configCodec) {
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
        BlockState wall = ModBlocks.DEATH_STAR_PANEL.getDefaultState();
        BlockState pillar = Blocks.POLISHED_BASALT.getDefaultState();

        // Platform 10x10
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                world.setBlockState(origin.add(x, 0, z), floor, 3);
                // Clear air above
                for(int y = 1; y < 6; y++) {
                    world.setBlockState(origin.add(x, y, z), Blocks.AIR.getDefaultState(), 3);
                }
            }
        }

        // Walls at corners
        for (int y = 1; y <= 3; y++) {
            world.setBlockState(origin.add(-5, y, -5), pillar, 3);
            world.setBlockState(origin.add(5, y, -5), pillar, 3);
            world.setBlockState(origin.add(-5, y, 5), pillar, 3);
            world.setBlockState(origin.add(5, y, 5), pillar, 3);
        }

        // Central Antenna
        for (int y = 1; y <= 5; y++) {
            world.setBlockState(origin.add(0, y, 0), wall, 3);
        }
        world.setBlockState(origin.add(0, 6, 0), Blocks.END_ROD.getDefaultState(), 3);

        // Spawn Enemies
        int enemyCount = 2 + random.nextInt(3);
        for (int i = 0; i < enemyCount; i++) {
            double offsetX = (random.nextDouble() - 0.5) * 8;
            double offsetZ = (random.nextDouble() - 0.5) * 8;
            
            if (random.nextBoolean()) {
                var stormtrooper = ModEntities.STORMTROOPER.create(world.toServerWorld());
                if (stormtrooper != null) {
                    stormtrooper.refreshPositionAndAngles(origin.getX() + offsetX, origin.getY() + 1, origin.getZ() + offsetZ, random.nextFloat() * 360, 0);
                    stormtrooper.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
                    world.spawnEntity(stormtrooper);
                }
            } else {
                var droid = ModEntities.BATTLE_DROID.create(world.toServerWorld());
                if (droid != null) {
                    droid.refreshPositionAndAngles(origin.getX() + offsetX, origin.getY() + 1, origin.getZ() + offsetZ, random.nextFloat() * 360, 0);
                    droid.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
                    world.spawnEntity(droid);
                }
            }
        }

        return true;
    }
}
