package com.starwars.util;

import com.starwars.item.ModItems;
import com.starwars.villager.ModVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class ModTrades {
    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.GALACTIC_TRADER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModItems.GALACTIC_GUIDE_BOOK, 1),
                    6, 2, 0.05f
            ));
            
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    new ItemStack(ModItems.RAW_DURASTEEL, 2),
                    10, 2, 0.05f
            ));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.GALACTIC_TRADER, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 15),
                    new ItemStack(ModItems.LIGHTSABER_HILT, 1),
                    3, 10, 0.05f
            ));
        });
        
        TradeOfferHelper.registerVillagerOffers(ModVillagers.GALACTIC_TRADER, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 20),
                    new ItemStack(ModItems.LIGHTSABER_EMITTER, 1),
                    3, 15, 0.05f
            ));
        });
        
        TradeOfferHelper.registerVillagerOffers(ModVillagers.GALACTIC_TRADER, 5, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 64),
                    new ItemStack(ModItems.KYBER_CRYSTAL_ORE, 1),
                    1, 30, 0.05f
            ));
        });
    }
}
