package com.starwars.item;

import com.starwars.StarWarsMod;
import com.starwars.block.ModBlocks;
import com.starwars.entity.ModEntities;
import com.starwars.item.custom.BlasterItem;
import com.starwars.item.custom.LightsaberItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item LIGHTSABER = registerItem("lightsaber", new LightsaberItem(ToolMaterials.DIAMOND, 3, -2.4f, new Item.Settings()));
    public static final Item BLASTER = registerItem("blaster", new BlasterItem(new Item.Settings().maxCount(1)));
    
    // Materials
    public static final Item RAW_DURASTEEL = registerItem("raw_durasteel", new Item(new Item.Settings()));
    public static final Item DURASTEEL_INGOT = registerItem("durasteel_ingot", new Item(new Item.Settings()));
    public static final Item KYBER_CRYSTAL = registerItem("kyber_crystal", new Item(new Item.Settings())); // Default blue
    public static final Item RED_KYBER_CRYSTAL = registerItem("red_kyber_crystal", new Item(new Item.Settings()));
    public static final Item GREEN_KYBER_CRYSTAL = registerItem("green_kyber_crystal", new Item(new Item.Settings()));
    public static final Item PURPLE_KYBER_CRYSTAL = registerItem("purple_kyber_crystal", new Item(new Item.Settings()));

    // Block Items
    public static final Item DURASTEEL_ORE = registerItem("durasteel_ore", new BlockItem(ModBlocks.DURASTEEL_ORE, new Item.Settings()));
    public static final Item KYBER_CRYSTAL_ORE = registerItem("kyber_crystal_ore", new BlockItem(ModBlocks.KYBER_CRYSTAL_ORE, new Item.Settings()));

    // Spawn Eggs
    public static final Item R2D2_SPAWN_EGG = registerItem("r2d2_spawn_egg", new SpawnEggItem(ModEntities.R2D2, 0xFFFFFF, 0x0000FF, new Item.Settings()));
    public static final Item BATTLE_DROID_SPAWN_EGG = registerItem("battle_droid_spawn_egg", new SpawnEggItem(ModEntities.BATTLE_DROID, 0xC4A484, 0x5C4033, new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(StarWarsMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        StarWarsMod.LOGGER.info("Registering Mod Items for " + StarWarsMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            ItemStack blue = new ItemStack(LIGHTSABER);
            ((LightsaberItem)LIGHTSABER).setColor(blue, "blue");
            entries.add(blue);

            ItemStack red = new ItemStack(LIGHTSABER);
            ((LightsaberItem)LIGHTSABER).setColor(red, "red");
            entries.add(red);

            ItemStack green = new ItemStack(LIGHTSABER);
            ((LightsaberItem)LIGHTSABER).setColor(green, "green");
            entries.add(green);

            ItemStack purple = new ItemStack(LIGHTSABER);
            ((LightsaberItem)LIGHTSABER).setColor(purple, "purple");
            entries.add(purple);
            
            entries.add(BLASTER);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RAW_DURASTEEL);
            entries.add(DURASTEEL_INGOT);
            entries.add(KYBER_CRYSTAL);
            entries.add(RED_KYBER_CRYSTAL);
            entries.add(GREEN_KYBER_CRYSTAL);
            entries.add(PURPLE_KYBER_CRYSTAL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(DURASTEEL_ORE);
            entries.add(KYBER_CRYSTAL_ORE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(R2D2_SPAWN_EGG);
            entries.add(BATTLE_DROID_SPAWN_EGG);
        });
    }
}
