package com.starwars.entity.projectile;

import com.starwars.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class LaserProjectileEntity extends PersistentProjectileEntity {
    public LaserProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public LaserProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.LASER_PROJECTILE, owner, world, new ItemStack(Items.ARROW), null);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return ItemStack.EMPTY;
    }
}
