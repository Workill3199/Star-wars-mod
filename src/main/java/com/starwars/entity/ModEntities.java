package com.starwars.entity;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.BattleDroidEntity;
import com.starwars.entity.custom.C3POEntity;
import com.starwars.entity.custom.EwokEntity;
import com.starwars.entity.custom.JawaEntity;
import com.starwars.entity.custom.R2D2Entity;
import com.starwars.entity.custom.StormtrooperEntity;
import com.starwars.entity.projectile.LaserProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;

public class ModEntities {
    public static final EntityType<R2D2Entity> R2D2 = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "r2d2"),
            EntityType.Builder.create(R2D2Entity::new, SpawnGroup.CREATURE)
                    .dimensions(0.75f, 1.2f)
                    .build("r2d2")
    );

    public static final EntityType<BattleDroidEntity> BATTLE_DROID = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "battle_droid"),
            EntityType.Builder.create(BattleDroidEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.9f)
                    .build("battle_droid")
    );

    public static final EntityType<StormtrooperEntity> STORMTROOPER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "stormtrooper"),
            EntityType.Builder.create(StormtrooperEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.8f)
                    .build("stormtrooper")
    );

    public static final EntityType<C3POEntity> C3PO = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "c3po"),
            EntityType.Builder.create(C3POEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 1.8f)
                    .build("c3po")
    );

    public static final EntityType<JawaEntity> JAWA = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "jawa"),
            EntityType.Builder.create(JawaEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.4f, 1.0f)
                    .build("jawa")
    );

    public static final EntityType<EwokEntity> EWOK = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "ewok"),
            EntityType.Builder.create(EwokEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.5f, 0.9f)
                    .build("ewok")
    );

    public static final EntityType<LaserProjectileEntity> LASER_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(StarWarsMod.MOD_ID, "laser_projectile"),
            EntityType.Builder.<LaserProjectileEntity>create(LaserProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .build("laser_projectile")
    );

    public static void registerModEntities() {
        StarWarsMod.LOGGER.info("Registering Mod Entities for " + StarWarsMod.MOD_ID);
    }

    public static void registerSpawnRestrictions() {
        SpawnRestriction.register(R2D2, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
        SpawnRestriction.register(C3PO, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
        SpawnRestriction.register(JAWA, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
        SpawnRestriction.register(EWOK, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
        
        SpawnRestriction.register(BATTLE_DROID, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
        SpawnRestriction.register(STORMTROOPER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
    }
}
