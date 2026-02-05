package com.starwars.util;

import com.starwars.StarWarsMod;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final RegistryKey<LootTable> STAR_WARS_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(StarWarsMod.MOD_ID, "chests/star_wars_loot"));

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && (
                    key.equals(LootTables.SIMPLE_DUNGEON_CHEST) ||
                    key.equals(LootTables.DESERT_PYRAMID_CHEST) ||
                    key.equals(LootTables.ABANDONED_MINESHAFT_CHEST) ||
                    key.equals(LootTables.END_CITY_TREASURE_CHEST)
            )) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(STAR_WARS_LOOT));

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
