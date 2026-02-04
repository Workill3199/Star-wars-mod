package com.starwars.entity.client;

import com.starwars.StarWarsMod;
import com.starwars.entity.projectile.LaserProjectileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class LaserProjectileRenderer extends ProjectileEntityRenderer<LaserProjectileEntity> {
    public static final Identifier TEXTURE = Identifier.of(StarWarsMod.MOD_ID, "textures/entity/projectiles/laser_bolt.png");

    public LaserProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(LaserProjectileEntity entity) {
        return TEXTURE;
    }
}
