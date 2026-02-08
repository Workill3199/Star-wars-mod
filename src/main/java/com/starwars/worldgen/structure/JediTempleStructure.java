package com.starwars.worldgen.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class JediTempleStructure extends Structure {
    public static final MapCodec<JediTempleStructure> CODEC = JediTempleStructure.createCodec(JediTempleStructure::new);

    public JediTempleStructure(Structure.Config config) {
        super(config);
    }

    @Override
    protected Optional<StructurePosition> getStructurePosition(Context context) {
        return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, collector -> {
            this.addPieces(collector, context);
        });
    }

    private void addPieces(StructurePiecesCollector collector, Context context) {
        ChunkPos chunkPos = context.chunkPos();
        // Center the structure in the chunk
        BlockPos blockPos = new BlockPos(chunkPos.getStartX(), 90, chunkPos.getStartZ()); // 90 is a fallback, actual height is handled by pieces or generation context
        
        // Use World Surface height
        int y = context.chunkGenerator().getHeight(chunkPos.getStartX(), chunkPos.getStartZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world(), context.noiseConfig());
        BlockPos centerPos = new BlockPos(chunkPos.getStartX(), y, chunkPos.getStartZ());

        JediTempleGenerator.addPieces(collector, centerPos, context.random());
    }

    @Override
    public StructureType<?> getType() {
        return ModStructures.JEDI_TEMPLE;
    }
}
