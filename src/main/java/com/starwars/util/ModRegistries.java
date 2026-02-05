package com.starwars.util;

import com.starwars.item.ModItems;
import com.starwars.villager.ModVillagers;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class ModRegistries {
    public static void registerModStuff() {
        registerCustomTrades();
        registerLootTables();
    }

    private static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.GALACTIC_TRADER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModItems.RAW_DURASTEEL, 4),
                    6, 2, 0.05f
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    new ItemStack(ModItems.GALACTIC_GUIDE_BOOK, 1),
                    1, 10, 0.05f
            ));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.GALACTIC_TRADER, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 10),
                    new ItemStack(ModItems.DURASTEEL_INGOT, 1),
                    4, 5, 0.05f
            ));
        });
    }

    private static void registerLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            // Overworld: Lightsaber Hilt in Desert Pyramids and Jungle Temples
            if (LootTables.DESERT_PYRAMID_CHEST.equals(key) || LootTables.JUNGLE_TEMPLE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.3f)) // 30% chance
                        .with(ItemEntry.builder(ModItems.LIGHTSABER_HILT))
                        .apply(net.minecraft.loot.function.SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            // Nether: Lightsaber Emitter in Nether Fortresses and Bastions
            if (LootTables.NETHER_BRIDGE_CHEST.equals(key) || LootTables.BASTION_TREASURE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.3f)) // 30% chance
                        .with(ItemEntry.builder(ModItems.LIGHTSABER_EMITTER))
                        .apply(net.minecraft.loot.function.SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
