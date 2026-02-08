package com.starwars.worldgen.structure;

import com.starwars.StarWarsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {
    public static StructureType<JediTempleStructure> JEDI_TEMPLE;

    public static void registerStructureFeatures() {
        JEDI_TEMPLE = Registry.register(Registries.STRUCTURE_TYPE, Identifier.of(StarWarsMod.MOD_ID, "jedi_temple"), () -> JediTempleStructure.CODEC);
    }
}
