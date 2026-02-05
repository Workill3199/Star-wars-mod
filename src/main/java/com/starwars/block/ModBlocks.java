package com.starwars.block;

import com.starwars.StarWarsMod;
import com.starwars.block.custom.HyperforgeBlock;
import com.starwars.block.custom.LightsaberForgeBlock;
import com.starwars.block.custom.CircuitTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block DURASTEEL_ORE = registerBlock("durasteel_ore",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(4.0f).requiresTool()));

    public static final Block PLASTEEL_ORE = registerBlock("plasteel_ore",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(4.0f).requiresTool()));
    
    public static final Block KYBER_CRYSTAL_ORE = registerBlock("kyber_crystal_ore",
            new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE).strength(4.5f).requiresTool()));

    public static final Block HYPERFORGE = registerBlock("hyperforge",
            new HyperforgeBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).requiresTool()));

    public static final Block LIGHTSABER_FORGE = registerBlock("lightsaber_forge",
            new LightsaberForgeBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).requiresTool()));

    public static final Block CIRCUIT_TABLE = registerBlock("circuit_table",
            new CircuitTableBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).requiresTool()));

    // Tech Blocks
    public static final Block IMPERIAL_PLATING = registerBlock("imperial_plating",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(5.0f).requiresTool()));

    public static final Block DEATH_STAR_PANEL = registerBlock("death_star_panel",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(5.0f).requiresTool().luminance(state -> 5)));

    public static final Block DURASTEEL_PLATING = registerBlock("durasteel_plating",
            new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK).strength(6.0f).requiresTool()));

    public static final Block HOLOGRAPHIC_PROJECTOR = registerBlock("holographic_projector",
            new Block(AbstractBlock.Settings.copy(Blocks.GLASS).strength(0.5f).luminance(state -> 15).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(StarWarsMod.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        StarWarsMod.LOGGER.info("Registering Mod Blocks for " + StarWarsMod.MOD_ID);
    }
}
