package com.starwars.villager;

import com.google.common.collect.ImmutableSet;
import com.starwars.StarWarsMod;
import com.starwars.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> GALACTIC_POI_KEY = poiKey("galactic_poi");
    public static final PointOfInterestType GALACTIC_POI = registerPoi("galactic_poi", ModBlocks.HYPERFORGE);

    public static final VillagerProfession GALACTIC_TRADER = registerProfession("galactic_trader", GALACTIC_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(StarWarsMod.MOD_ID, name),
                VillagerProfessionBuilder.create().id(Identifier.of(StarWarsMod.MOD_ID, name)).workstation(type).workSound(SoundEvents.ENTITY_VILLAGER_WORK_ARMORER).build());
    }

    private static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(StarWarsMod.MOD_ID, name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(StarWarsMod.MOD_ID, name));
    }

    public static void registerVillagers() {
        StarWarsMod.LOGGER.info("Registering Villagers for " + StarWarsMod.MOD_ID);
    }
}
