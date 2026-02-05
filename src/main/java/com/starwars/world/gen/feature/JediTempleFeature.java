package com.starwars.world.gen.feature;

import com.mojang.serialization.Codec;
import com.starwars.StarWarsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class JediTempleFeature extends Feature<DefaultFeatureConfig> {
    public JediTempleFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        // Ensure we are on solid ground
        if (!world.getBlockState(origin.down()).isSolidBlock(world, origin.down())) {
            return false;
        }

        BlockState stoneBrick = Blocks.STONE_BRICKS.getDefaultState();
        BlockState mossyBrick = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
        BlockState crackedBrick = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
        BlockState quartz = Blocks.QUARTZ_BLOCK.getDefaultState();
        BlockState pillar = Blocks.QUARTZ_PILLAR.getDefaultState();

        int width = 11;
        int length = 15;
        int height = 8;

        // Base & Walls
        for (int x = -width/2; x <= width/2; x++) {
            for (int z = 0; z < length; z++) {
                // Floor
                world.setBlockState(origin.add(x, 0, z), random.nextFloat() < 0.7 ? stoneBrick : mossyBrick, 3);
                
                // Ceiling
                world.setBlockState(origin.add(x, height, z), stoneBrick, 3);

                // Walls
                if (x == -width/2 || x == width/2 || z == 0 || z == length - 1) {
                    for (int y = 1; y < height; y++) {
                        world.setBlockState(origin.add(x, y, z), random.nextFloat() < 0.8 ? stoneBrick : crackedBrick, 3);
                    }
                } else {
                    // Clear interior
                    for (int y = 1; y < height; y++) {
                        world.setBlockState(origin.add(x, y, z), Blocks.AIR.getDefaultState(), 3);
                    }
                }
            }
        }

        // Entrance Pillars
        for (int y = 1; y < height; y++) {
            world.setBlockState(origin.add(-2, y, 0), pillar, 3);
            world.setBlockState(origin.add(2, y, 0), pillar, 3);
        }
        world.setBlockState(origin.add(0, 1, 0), Blocks.AIR.getDefaultState(), 3);
        world.setBlockState(origin.add(0, 2, 0), Blocks.AIR.getDefaultState(), 3);

        // Traps!
        // TNT Trap in the middle hallway
        BlockPos trapPos = origin.add(0, 0, 5);
        world.setBlockState(trapPos, Blocks.TNT.getDefaultState(), 3);
        world.setBlockState(trapPos.up(), Blocks.STONE_PRESSURE_PLATE.getDefaultState(), 3);

        // Loot Room at the back
        BlockPos lootPos = origin.add(0, 1, length - 2);
        world.setBlockState(lootPos, Blocks.CHEST.getDefaultState(), 3);
        LootableContainerBlockEntity.setLootTable(world, random, lootPos, RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/jedi_temple")));

        // Enemies
        for (int i = 0; i < 3; i++) {
             // Spawn some skeletons as "Remnants"
             SkeletonEntity skeleton = EntityType.SKELETON.create(world.toServerWorld());
             if (skeleton != null) {
                 skeleton.refreshPositionAndAngles(origin.getX(), origin.getY() + 1, origin.getZ() + 5, 0, 0);
                 skeleton.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
                 world.spawnEntity(skeleton);
             }
        }

        return true;
    }
}
