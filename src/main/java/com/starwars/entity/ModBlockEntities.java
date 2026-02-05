package com.starwars.entity;

import com.starwars.StarWarsMod;
import com.starwars.block.ModBlocks;
import com.starwars.entity.block.HyperforgeBlockEntity;
import com.starwars.entity.block.LightsaberForgeBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<HyperforgeBlockEntity> HYPERFORGE_BE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "hyperforge_be"),
            BlockEntityType.Builder.create(HyperforgeBlockEntity::new, ModBlocks.HYPERFORGE).build(null)
    );

    public static final BlockEntityType<LightsaberForgeBlockEntity> LIGHTSABER_FORGE_BE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "lightsaber_forge_be"),
            BlockEntityType.Builder.create(LightsaberForgeBlockEntity::new, ModBlocks.LIGHTSABER_FORGE).build(null)
    );

    public static void registerBlockEntities() {
        StarWarsMod.LOGGER.info("Registering Block Entities for " + StarWarsMod.MOD_ID);
    }
}
