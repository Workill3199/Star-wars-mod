package com.starwars.block;

import com.starwars.StarWarsMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block DURASTEEL_ORE = registerBlock("durasteel_ore",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(4.0f).requiresTool()));
    
    public static final Block KYBER_CRYSTAL_ORE = registerBlock("kyber_crystal_ore",
            new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE).strength(4.5f).requiresTool()));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(StarWarsMod.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        StarWarsMod.LOGGER.info("Registering Mod Blocks for " + StarWarsMod.MOD_ID);
    }
}
