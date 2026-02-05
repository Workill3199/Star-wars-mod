package com.starwars.world.gen.feature;

import com.mojang.serialization.Codec;
import com.starwars.StarWarsMod;
import com.starwars.block.ModBlocks;
import com.starwars.entity.ModEntities;
import com.starwars.entity.custom.JediMasterEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class HighTechJediTempleFeature extends Feature<DefaultFeatureConfig> {
    public HighTechJediTempleFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        // Ensure solid ground
        if (!world.getBlockState(origin.down()).isSolidBlock(world, origin.down())) {
            return false;
        }

        BlockState floor = ModBlocks.IMPERIAL_PLATING.getDefaultState();
        BlockState wall = ModBlocks.DURASTEEL_PLATING.getDefaultState();
        BlockState glass = ModBlocks.HOLOGRAPHIC_PROJECTOR.getDefaultState();
        BlockState pillar = ModBlocks.DEATH_STAR_PANEL.getDefaultState();
        BlockState light = Blocks.SEA_LANTERN.getDefaultState();

        int width = 15;
        int length = 25;
        int height = 12;

        // 1. Foundation
        for (int x = -width/2; x <= width/2; x++) {
            for (int z = 0; z < length; z++) {
                world.setBlockState(origin.add(x, 0, z), floor, 3);
                // Clear area above
                for (int y = 1; y < height; y++) {
                    world.setBlockState(origin.add(x, y, z), Blocks.AIR.getDefaultState(), 3);
                }
            }
        }

        // 2. Walls & Ceiling
        for (int x = -width/2; x <= width/2; x++) {
            for (int z = 0; z < length; z++) {
                if (x == -width/2 || x == width/2 || z == 0 || z == length - 1) {
                    for (int y = 1; y < height; y++) {
                        // Windows
                        if (y > 2 && y < height - 2 && (z % 4 != 0) && (x == -width/2 || x == width/2)) {
                            world.setBlockState(origin.add(x, y, z), glass, 3);
                        } else {
                            world.setBlockState(origin.add(x, y, z), wall, 3);
                        }
                    }
                }
                // Ceiling
                world.setBlockState(origin.add(x, height, z), floor, 3);
            }
        }

        // 3. Interior - Main Hall (First 10 blocks)
        for (int z = 2; z < 10; z += 4) {
            // Pillars
            for (int y = 1; y < height - 2; y++) {
                world.setBlockState(origin.add(-3, y, z), pillar, 3);
                world.setBlockState(origin.add(3, y, z), pillar, 3);
            }
        }

        // 4. Second Floor (Balcony)
        int floor2Y = 6;
        for (int x = -width/2 + 1; x <= width/2 - 1; x++) {
            for (int z = 10; z < length - 1; z++) {
                world.setBlockState(origin.add(x, floor2Y, z), floor, 3);
            }
        }
        // Stairs to second floor
        for (int i = 0; i < floor2Y; i++) {
             world.setBlockState(origin.add(0, 1 + i, 9 + i), Blocks.QUARTZ_STAIRS.getDefaultState().with(net.minecraft.block.StairsBlock.FACING, Direction.SOUTH), 3);
        }

        // 5. Tech Lab (Ground Floor Back)
        BlockPos labCenter = origin.add(0, 1, 18);
        world.setBlockState(labCenter, ModBlocks.CIRCUIT_TABLE.getDefaultState(), 3);
        world.setBlockState(labCenter.east(2), ModBlocks.HYPERFORGE.getDefaultState(), 3);
        world.setBlockState(labCenter.west(2), ModBlocks.LIGHTSABER_FORGE.getDefaultState(), 3);

        // 6. Archive / Holocron Room (Second Floor Back)
        BlockPos archivePos = origin.add(0, floor2Y + 1, length - 4);
        world.setBlockState(archivePos, Blocks.ENCHANTING_TABLE.getDefaultState(), 3); // Placeholder for "Console"
        
        // Holocron Pedestals
        world.setBlockState(archivePos.east(3), ModBlocks.DEATH_STAR_PANEL.getDefaultState(), 3);
        world.setBlockState(archivePos.west(3), ModBlocks.DEATH_STAR_PANEL.getDefaultState(), 3);

        // 7. Loot
        BlockPos loot1 = origin.add(5, 1, length - 2);
        world.setBlockState(loot1, Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(loot1) instanceof LootableContainerBlockEntity lootable) {
            lootable.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/high_tech_jedi_temple")), random.nextLong());
        }
        
        BlockPos loot2 = origin.add(-5, floor2Y + 1, length - 2);
        world.setBlockState(loot2, Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(loot2) instanceof LootableContainerBlockEntity lootable) {
            lootable.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/high_tech_jedi_temple")), random.nextLong());
        }

        // 8. Traps
        // Hidden TNT under the "Tech Lab" floor
        world.setBlockState(labCenter.down(), Blocks.TNT.getDefaultState(), 3);
        world.setBlockState(labCenter.down(2), Blocks.SCULK_SENSOR.getDefaultState(), 3); // High tech trap!

        // 9. Defenders
        int jediCount = 2 + random.nextInt(2);
        for (int i = 0; i < jediCount; i++) {
            JediMasterEntity jedi = ModEntities.JEDI_MASTER.create(world.toServerWorld());
            if (jedi != null) {
                jedi.refreshPositionAndAngles(origin.getX(), origin.getY() + 1, origin.getZ() + 15, 0, 0);
                jedi.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
                world.spawnEntity(jedi);
            }
        }
        
        // Add a few friendly droids? Or maybe security droids?
        // Let's add R2D2 as a "hacked" droid
        var r2d2 = ModEntities.R2D2.create(world.toServerWorld());
        if (r2d2 != null) {
             r2d2.refreshPositionAndAngles(origin.getX() + 3, origin.getY() + floor2Y + 1, origin.getZ() + 15, 0, 0);
             r2d2.initialize(world.toServerWorld(), world.getLocalDifficulty(origin), SpawnReason.STRUCTURE, null);
             world.spawnEntity(r2d2);
        }

        return true;
    }
}
