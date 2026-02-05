package com.starwars.item;

import com.starwars.StarWarsMod;
import com.starwars.block.ModBlocks;
import com.starwars.entity.ModEntities;
import com.starwars.item.custom.BlasterItem;
import com.starwars.item.custom.LightsaberItem;
import com.starwars.item.custom.MandalorianArmorItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
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

    // Armor
    public static final Item CLONE_TROOPER_HELMET = registerItem("clone_trooper_helmet", new ArmorItem(ModArmorMaterials.CLONE_TROOPER, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1)));
    public static final Item CLONE_TROOPER_CHESTPLATE = registerItem("clone_trooper_chestplate", new ArmorItem(ModArmorMaterials.CLONE_TROOPER, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1)));
    public static final Item CLONE_TROOPER_LEGGINGS = registerItem("clone_trooper_leggings", new ArmorItem(ModArmorMaterials.CLONE_TROOPER, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1)));
    public static final Item CLONE_TROOPER_BOOTS = registerItem("clone_trooper_boots", new ArmorItem(ModArmorMaterials.CLONE_TROOPER, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)));

    public static final Item MANDALORIAN_HELMET = registerItem("mandalorian_helmet", new ArmorItem(ModArmorMaterials.MANDALORIAN, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1)));
    public static final Item MANDALORIAN_CHESTPLATE = registerItem("mandalorian_chestplate", new MandalorianArmorItem(ModArmorMaterials.MANDALORIAN, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1)));
    public static final Item MANDALORIAN_LEGGINGS = registerItem("mandalorian_leggings", new ArmorItem(ModArmorMaterials.MANDALORIAN, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1)));
    public static final Item MANDALORIAN_BOOTS = registerItem("mandalorian_boots", new ArmorItem(ModArmorMaterials.MANDALORIAN, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)));
    
    // Components
    public static final Item LIGHTSABER_HILT = registerItem("lightsaber_hilt", new Item(new Item.Settings()));
    public static final Item LIGHTSABER_EMITTER = registerItem("lightsaber_emitter", new Item(new Item.Settings()));
    public static final Item GALACTIC_GUIDE_BOOK = registerItem("galactic_guide_book", new Item(new Item.Settings().maxCount(1)));

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
    public static final Item HYPERFORGE = registerItem("hyperforge", new BlockItem(ModBlocks.HYPERFORGE, new Item.Settings()));
    public static final Item LIGHTSABER_FORGE = registerItem("lightsaber_forge", new BlockItem(ModBlocks.LIGHTSABER_FORGE, new Item.Settings()));

    // Spawn Eggs
    public static final Item R2D2_SPAWN_EGG = registerItem("r2d2_spawn_egg", new SpawnEggItem(ModEntities.R2D2, 0xFFFFFF, 0x0000FF, new Item.Settings()));
    public static final Item BATTLE_DROID_SPAWN_EGG = registerItem("battle_droid_spawn_egg", new SpawnEggItem(ModEntities.BATTLE_DROID, 0xC4A484, 0x5C4033, new Item.Settings()));
    public static final Item STORMTROOPER_SPAWN_EGG = registerItem("stormtrooper_spawn_egg", new SpawnEggItem(ModEntities.STORMTROOPER, 0xFFFFFF, 0x000000, new Item.Settings()));
    public static final Item C3PO_SPAWN_EGG = registerItem("c3po_spawn_egg", new SpawnEggItem(ModEntities.C3PO, 0xFFD700, 0x8B4513, new Item.Settings()));
    public static final Item JAWA_SPAWN_EGG = registerItem("jawa_spawn_egg", new SpawnEggItem(ModEntities.JAWA, 0x8B4513, 0x000000, new Item.Settings()));
    public static final Item EWOK_SPAWN_EGG = registerItem("ewok_spawn_egg", new SpawnEggItem(ModEntities.EWOK, 0x654321, 0xDEB887, new Item.Settings()));


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

            entries.add(CLONE_TROOPER_HELMET);
            entries.add(CLONE_TROOPER_CHESTPLATE);
            entries.add(CLONE_TROOPER_LEGGINGS);
            entries.add(CLONE_TROOPER_BOOTS);

            entries.add(MANDALORIAN_HELMET);
            entries.add(MANDALORIAN_CHESTPLATE);
            entries.add(MANDALORIAN_LEGGINGS);
            entries.add(MANDALORIAN_BOOTS);
            
            entries.add(LIGHTSABER_HILT);
            entries.add(LIGHTSABER_EMITTER);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(GALACTIC_GUIDE_BOOK);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RAW_DURASTEEL);
            entries.add(DURASTEEL_INGOT);
            entries.add(KYBER_CRYSTAL);
            entries.add(RED_KYBER_CRYSTAL);
            entries.add(GREEN_KYBER_CRYSTAL);
            entries.add(PURPLE_KYBER_CRYSTAL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(HYPERFORGE);
            entries.add(LIGHTSABER_FORGE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(DURASTEEL_ORE);
            entries.add(KYBER_CRYSTAL_ORE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(R2D2_SPAWN_EGG);
            entries.add(BATTLE_DROID_SPAWN_EGG);
            entries.add(STORMTROOPER_SPAWN_EGG);
            entries.add(C3PO_SPAWN_EGG);
            entries.add(JAWA_SPAWN_EGG);
            entries.add(EWOK_SPAWN_EGG);
        });
    }
}
