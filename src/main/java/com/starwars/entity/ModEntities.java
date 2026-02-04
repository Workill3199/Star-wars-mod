package com.starwars.entity;

import com.starwars.StarWarsMod;
import com.starwars.entity.custom.BattleDroidEntity;
import com.starwars.entity.custom.R2D2Entity;
import com.starwars.entity.projectile.LaserProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

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
}
