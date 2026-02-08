package com.starwars.worldgen.structure;

import com.starwars.StarWarsMod;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class JediTempleGenerator extends StructurePiece {
    public static StructurePieceType JEDI_TEMPLE_PIECE;
    private static final RegistryKey<net.minecraft.loot.LootTable> JEDI_TEMPLE_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/jedi_temple"));

    public JediTempleGenerator(Random random, int x, int z) {
        super(JEDI_TEMPLE_PIECE, 0, new BlockBox(x, 64, z, x + 30, 64 + 20, z + 30));
        this.setOrientation(Direction.NORTH);
    }

    public JediTempleGenerator(StructureContext context, NbtCompound nbt) {
        super(JEDI_TEMPLE_PIECE, nbt);
    }

    public static void init() {
        JEDI_TEMPLE_PIECE = Registry.register(Registries.STRUCTURE_PIECE, Identifier.of(StarWarsMod.MOD_ID, "jedi_temple_piece"), JediTempleGenerator::new);
    }

    public static void addPieces(StructurePiecesCollector collector, BlockPos pos, Random random) {
        collector.addPiece(new JediTempleGenerator(random, pos.getX(), pos.getZ()));
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        // No extra data needed yet
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        // Simple Procedural Temple Generation
        // A pyramid style base with pillars
        
        // Base height
        int y = this.boundingBox.getMinY();
        int x = this.boundingBox.getMinX();
        int z = this.boundingBox.getMinZ();
        
        // Find ground level if needed, but we rely on structure placement to give us a good Y, 
        // however for a Piece, the Y is usually fixed relative to the start.
        // We will just build relative to boundingBox.

        // 1. Foundation (Solid Quartz/Stone) - 30x30
        for (int i = 0; i < 30; i++) {
            for (int k = 0; k < 30; k++) {
                // Floor
                this.addBlock(world, Blocks.QUARTZ_BRICKS.getDefaultState(), x + i, y, z + k, chunkBox);
                // Foundation depth (down to solid ground roughly)
                for(int d = 1; d < 5; d++) {
                    this.addBlock(world, Blocks.STONE_BRICKS.getDefaultState(), x + i, y - d, z + k, chunkBox);
                }
            }
        }

        // 2. Pillars (Corners and intervals)
        for (int i = 2; i <= 28; i += 6) {
            for (int k = 2; k <= 28; k += 6) {
                // Pillar height 10
                for (int h = 1; h <= 10; h++) {
                    this.addBlock(world, Blocks.QUARTZ_PILLAR.getDefaultState(), x + i, y + h, z + k, chunkBox);
                }
            }
        }

        // 3. Roof (Pyramid shape starting at y + 11)
        int roofStart = y + 11;
        int size = 30;
        for (int h = 0; h < 15; h++) {
            int currentSize = size - (h * 2);
            if (currentSize <= 0) break;
            
            int offset = h;
            for (int i = 0; i < currentSize; i++) {
                for (int k = 0; k < currentSize; k++) {
                    this.addBlock(world, Blocks.QUARTZ_STAIRS.getDefaultState(), x + offset + i, roofStart + h, z + offset + k, chunkBox);
                }
            }
        }
        
        // 4. Central Chamber (Air inside)
        // Clear out the center
        for (int i = 5; i < 25; i++) {
            for (int k = 5; k < 25; k++) {
                for (int h = 1; h < 10; h++) {
                     this.addBlock(world, Blocks.AIR.getDefaultState(), x + i, y + h, z + k, chunkBox);
                }
            }
        }

        // 5. Central Altar (Holocron/Loot)
        int centerX = x + 15;
        int centerZ = z + 15;
        this.addBlock(world, Blocks.GOLD_BLOCK.getDefaultState(), centerX, y + 1, centerZ, chunkBox);
        
        // Chest with Loot
        BlockPos chestPos = new BlockPos(centerX, y + 2, centerZ);
        this.addChest(world, chunkBox, random, chestPos, JEDI_TEMPLE_LOOT, Blocks.CHEST.getDefaultState());
    }
}
