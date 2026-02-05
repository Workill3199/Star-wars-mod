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

        BlockState stone = Blocks.STONE_BRICKS.getDefaultState();
        BlockState mossy = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
        BlockState cracked = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
        BlockState pillar = Blocks.CHISELED_STONE_BRICKS.getDefaultState();
        BlockState floor = Blocks.POLISHED_ANDESITE.getDefaultState();

        int width = 21;
        int length = 35;
        int height = 12;

        // 1. Foundation & Clearing
        for (int x = -width/2; x <= width/2; x++) {
            for (int z = 0; z < length; z++) {
                world.setBlockState(origin.add(x, 0, z), floor, 3);
                for (int y = 1; y < height; y++) {
                    world.setBlockState(origin.add(x, y, z), Blocks.AIR.getDefaultState(), 3);
                }
            }
        }

        // 2. Walls & Roof
        for (int x = -width/2; x <= width/2; x++) {
            for (int z = 0; z < length; z++) {
                if (x == -width/2 || x == width/2 || z == 0 || z == length - 1) {
                    for (int y = 1; y < height; y++) {
                        BlockState wallBlock = random.nextFloat() < 0.2 ? mossy : (random.nextFloat() < 0.2 ? cracked : stone);
                        world.setBlockState(origin.add(x, y, z), wallBlock, 3);
                    }
                }
                // Roof (Pyramid style?) - Flat for now with skylights
                world.setBlockState(origin.add(x, height, z), stone, 3);
            }
        }

        // 3. Atrium (Central Garden)
        // 10x10 area in the middle
        int atriumStart = 10;
        for (int x = -5; x <= 5; x++) {
            for (int z = atriumStart; z < atriumStart + 10; z++) {
                // Open roof
                world.setBlockState(origin.add(x, height, z), Blocks.GLASS.getDefaultState(), 3);
                // Grass floor
                world.setBlockState(origin.add(x, 0, z), Blocks.GRASS_BLOCK.getDefaultState(), 3);
                // Water feature in center
                if (Math.abs(x) <= 2 && Math.abs(z - (atriumStart + 5)) <= 2) {
                     world.setBlockState(origin.add(x, 0, z), Blocks.WATER.getDefaultState(), 3);
                }
            }
        }
        // Small Tree in center
        BlockPos treePos = origin.add(0, 1, atriumStart + 5);
        world.setBlockState(treePos, Blocks.OAK_LOG.getDefaultState(), 3);
        world.setBlockState(treePos.up(), Blocks.OAK_LOG.getDefaultState(), 3);
        world.setBlockState(treePos.up(2), Blocks.OAK_LEAVES.getDefaultState(), 3);
        world.setBlockState(treePos.up(2).east(), Blocks.OAK_LEAVES.getDefaultState(), 3);
        world.setBlockState(treePos.up(2).west(), Blocks.OAK_LEAVES.getDefaultState(), 3);
        world.setBlockState(treePos.up(2).north(), Blocks.OAK_LEAVES.getDefaultState(), 3);
        world.setBlockState(treePos.up(2).south(), Blocks.OAK_LEAVES.getDefaultState(), 3);

        // 4. Library Wing (Left Side)
        for (int z = 5; z < 15; z++) {
            world.setBlockState(origin.add(-width/2 + 2, 1, z), Blocks.BOOKSHELF.getDefaultState(), 3);
            world.setBlockState(origin.add(-width/2 + 2, 2, z), Blocks.BOOKSHELF.getDefaultState(), 3);
        }

        // 5. Training Wing (Right Side)
        for (int z = 5; z < 15; z+=2) {
            world.setBlockState(origin.add(width/2 - 3, 1, z), Blocks.HAY_BLOCK.getDefaultState(), 3); // Dummy
            world.setBlockState(origin.add(width/2 - 3, 2, z), Blocks.PUMPKIN.getDefaultState(), 3);
        }

        // 6. Main Sanctum (Back)
        BlockPos sanctumPos = origin.add(0, 1, length - 5);
        // Raised Platform
        for (int x = -3; x <= 3; x++) {
            for (int z = length - 8; z < length - 2; z++) {
                world.setBlockState(origin.add(x, 1, z), Blocks.POLISHED_ANDESITE_SLAB.getDefaultState(), 3);
            }
        }
        
        // 7. Loot
        BlockPos lootPos = origin.add(0, 2, length - 3);
        world.setBlockState(lootPos, Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(lootPos) instanceof LootableContainerBlockEntity lootable) {
            lootable.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/jedi_temple")), random.nextLong());
        }

        // 8. Hidden Crypt (Basement)
        // Trapdoor entrance under a carpet in the library
        BlockPos trapdoorPos = origin.add(-width/2 + 5, 0, 10);
        world.setBlockState(trapdoorPos, Blocks.AIR.getDefaultState(), 3); // Hole
        
        // Crypt Room
        for (int x = -3; x <= 3; x++) {
            for (int z = 0; z < 6; z++) {
                for (int y = -4; y < 0; y++) {
                    if (y == -4) world.setBlockState(trapdoorPos.add(x, y, z), mossy, 3);
                    else if (x == -3 || x == 3 || z == 0 || z == 5) world.setBlockState(trapdoorPos.add(x, y, z), cracked, 3);
                    else world.setBlockState(trapdoorPos.add(x, y, z), Blocks.AIR.getDefaultState(), 3);
                }
            }
        }
        // Crypt Loot
        BlockPos cryptLoot = trapdoorPos.add(0, -3, 3);
        world.setBlockState(cryptLoot, Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(cryptLoot) instanceof LootableContainerBlockEntity lootable) {
            lootable.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/jedi_temple")), random.nextLong());
        }
        // Spawner in crypt (Skeleton)
        world.setBlockState(trapdoorPos.add(0, -3, 2), Blocks.SPAWNER.getDefaultState(), 3);
        // Note: Configuring spawner requires NBT which is hard here. We'll spawn entities manually.

        // 9. Defenders
        int enemyCount = 3 + random.nextInt(3);
        for (int i = 0; i < enemyCount; i++) {
             // Spawn some skeletons as "Remnants"
             SkeletonEntity skeleton = EntityType.SKELETON.create(world.toServerWorld());
             if (skeleton != null) {
                 skeleton.refreshPositionAndAngles(origin.getX() + (random.nextInt(10) - 5), origin.getY() + 1, origin.getZ() + 10 + random.nextInt(10), 0, 0);
                 skeleton.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
                 world.spawnEntity(skeleton);
             }
        }
        
        // Crypt Guardian
        ZombieEntity zombie = EntityType.ZOMBIE.create(world.toServerWorld());
        if (zombie != null) {
            zombie.refreshPositionAndAngles(trapdoorPos.getX(), trapdoorPos.getY() - 3, trapdoorPos.getZ() + 1, 0, 0);
            zombie.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
            world.spawnEntity(zombie);
        }

        return true;
    }
}
