package com.starwars.world.gen.feature;

import com.mojang.serialization.Codec;
import com.starwars.entity.ModEntities;
import com.starwars.villager.ModVillagers;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerType;

public class TatooineMarketFeature extends Feature<DefaultFeatureConfig> {
    public TatooineMarketFeature(Codec<DefaultFeatureConfig> configCodec) {
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

        // Build a street
        // Length 20, Width 5
        BlockState sandstone = Blocks.SMOOTH_SANDSTONE.getDefaultState();
        BlockState wall = Blocks.CUT_SANDSTONE.getDefaultState();
        
        for (int x = 0; x < 20; x++) {
            for (int z = 0; z < 5; z++) {
                BlockPos pos = origin.add(x, -1, z);
                world.setBlockState(pos, sandstone, 3);
                // Clear air above
                for(int y = 0; y < 5; y++) {
                    world.setBlockState(pos.up(y + 1), Blocks.AIR.getDefaultState(), 3);
                }
            }
            
            // Stalls on sides
            if (x % 5 == 0 && x < 16) {
                buildStall(world, origin.add(x, 0, 5), random, true);
                buildStall(world, origin.add(x, 0, -3), random, false);
            }
        }

        return true;
    }

    private void buildStall(StructureWorldAccess world, BlockPos pos, Random random, boolean side) {
        BlockState wool = Blocks.ORANGE_WOOL.getDefaultState();
        if (random.nextBoolean()) wool = Blocks.RED_WOOL.getDefaultState();
        
        BlockState fence = Blocks.BIRCH_FENCE.getDefaultState();

        // Floor
        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                world.setBlockState(pos.add(x, -1, z), Blocks.SANDSTONE.getDefaultState(), 3);
            }
        }

        // Posts
        world.setBlockState(pos.add(0, 0, 0), fence, 3);
        world.setBlockState(pos.add(2, 0, 0), fence, 3);
        world.setBlockState(pos.add(0, 0, 2), fence, 3);
        world.setBlockState(pos.add(2, 0, 2), fence, 3);
        
        world.setBlockState(pos.add(0, 1, 0), fence, 3);
        world.setBlockState(pos.add(2, 1, 0), fence, 3);
        world.setBlockState(pos.add(0, 1, 2), fence, 3);
        world.setBlockState(pos.add(2, 1, 2), fence, 3);

        // Roof
        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                world.setBlockState(pos.add(x, 2, z), wool, 3);
            }
        }

        // Spawn Trader
        if (random.nextFloat() < 0.5f) {
            // Spawn Jawa
            var jawa = ModEntities.JAWA.create(world.toServerWorld());
            if (jawa != null) {
                jawa.refreshPositionAndAngles(pos.getX() + 1.5, pos.getY(), pos.getZ() + 1.5, 0, 0);
                jawa.initialize(world.toServerWorld(), world.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
                world.spawnEntity(jawa);
            }
        } else {
            // Spawn Galactic Trader Villager
            VillagerEntity villager = EntityType.VILLAGER.create(world.toServerWorld());
            if (villager != null) {
                villager.refreshPositionAndAngles(pos.getX() + 1.5, pos.getY(), pos.getZ() + 1.5, 0, 0);
                villager.initialize(world.toServerWorld(), world.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
                
                VillagerData data = villager.getVillagerData().withProfession(ModVillagers.GALACTIC_TRADER).withType(VillagerType.DESERT);
                villager.setVillagerData(data);
                
                world.spawnEntity(villager);
            }
        }
    }
}
