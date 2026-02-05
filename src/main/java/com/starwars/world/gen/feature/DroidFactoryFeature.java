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

        if (!world.getBlockState(origin.down()).isSolidBlock(world, origin.down())) {
            return false;
        }

        BlockState floor = ModBlocks.IMPERIAL_PLATING.getDefaultState();
        BlockState wall = Blocks.POLISHED_BLACKSTONE_BRICKS.getDefaultState();
        BlockState roof = ModBlocks.DEATH_STAR_PANEL.getDefaultState();
        BlockState fluid = Blocks.LAVA.getDefaultState();
        BlockState machine = ModBlocks.DURASTEEL_PLATING.getDefaultState();

        int width = 19;
        int length = 29;
        int height = 10;

        // 1. Excavation & Foundation
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
                        world.setBlockState(origin.add(x, y, z), wall, 3);
                        // Industrial Windows
                        if (y > 3 && y < height - 2 && (z % 5 != 0) && (x == -width/2 || x == width/2)) {
                            world.setBlockState(origin.add(x, y, z), Blocks.IRON_BARS.getDefaultState(), 3);
                        }
                    }
                }
                world.setBlockState(origin.add(x, height, z), roof, 3);
            }
        }

        // 3. Assembly Lines (Conveyor Belts)
        for (int x : new int[]{-5, 5}) {
            for (int z = 4; z < length - 4; z++) {
                world.setBlockState(origin.add(x, 1, z), Blocks.ANVIL.getDefaultState(), 3); // "Belt"
                // Robotic Arms (End Rods / Pistons) above
                if (z % 3 == 0) {
                     world.setBlockState(origin.add(x, 4, z), Blocks.PISTON.getDefaultState().with(net.minecraft.block.PistonBlock.FACING, net.minecraft.util.math.Direction.DOWN), 3);
                     world.setBlockState(origin.add(x, 3, z), Blocks.END_ROD.getDefaultState().with(net.minecraft.block.EndRodBlock.FACING, net.minecraft.util.math.Direction.DOWN), 3);
                }
            }
        }

        // 4. Lava Vats (Central Waste Disposal)
        for (int x = -2; x <= 2; x++) {
            for (int z = 10; z <= 18; z++) {
                world.setBlockState(origin.add(x, 0, z), fluid, 3);
                world.setBlockState(origin.add(x, 1, z), fluid, 3);
                // Glass cover
                world.setBlockState(origin.add(x, 2, z), Blocks.GLASS.getDefaultState(), 3);
            }
        }
        
        // 5. Catwalks (Second Level)
        int catwalkY = 6;
        for (int z = 2; z < length - 2; z++) {
            // Central walkway
             world.setBlockState(origin.add(0, catwalkY, z), Blocks.IRON_TRAPDOOR.getDefaultState(), 3);
             // Side viewing platforms
             if (z % 6 == 0) {
                 world.setBlockState(origin.add(1, catwalkY, z), Blocks.IRON_TRAPDOOR.getDefaultState(), 3);
                 world.setBlockState(origin.add(-1, catwalkY, z), Blocks.IRON_TRAPDOOR.getDefaultState(), 3);
             }
        }
        // Ladder up
        for (int y = 1; y <= catwalkY; y++) {
            world.setBlockState(origin.add(0, y, 2), Blocks.LADDER.getDefaultState().with(net.minecraft.block.LadderBlock.FACING, net.minecraft.util.math.Direction.NORTH), 3);
        }

        // 6. Loot & Storage
        // Crate stacks
        for (int i = 0; i < 4; i++) {
            int bx = random.nextBoolean() ? -width/2 + 2 : width/2 - 2;
            int bz = random.nextInt(length - 4) + 2;
            world.setBlockState(origin.add(bx, 1, bz), Blocks.BARREL.getDefaultState(), 3);
            world.setBlockState(origin.add(bx, 2, bz), Blocks.BARREL.getDefaultState(), 3);
        }

        // Actual Chests
        BlockPos lootPos1 = origin.add(-8, 1, length - 3);
        world.setBlockState(lootPos1, Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(lootPos1) instanceof LootableContainerBlockEntity lootable) {
            lootable.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/droid_factory")), random.nextLong());
        }

        BlockPos lootPos2 = origin.add(8, 1, length - 3);
        world.setBlockState(lootPos2, Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(lootPos2) instanceof LootableContainerBlockEntity lootable) {
            lootable.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/droid_factory")), random.nextLong());
        }

        // 7. Control Room (Elevated, Back)
        BlockPos controlPos = origin.add(0, catwalkY + 1, length - 5);
        world.setBlockState(controlPos, ModBlocks.CIRCUIT_TABLE.getDefaultState(), 3);
        world.setBlockState(controlPos.east(), ModBlocks.DEATH_STAR_PANEL.getDefaultState(), 3);
        world.setBlockState(controlPos.west(), ModBlocks.DEATH_STAR_PANEL.getDefaultState(), 3);


        // 8. Spawn Enemies (Heavy Guard)
        int enemyCount = 10 + random.nextInt(8);
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
