package com.starwars.networking;

import com.starwars.networking.packet.AbilityUsePayload;
import com.starwars.networking.packet.ForceSyncPayload;
import com.starwars.networking.packet.SkillSyncPayload;
import com.starwars.networking.packet.SkillUnlockPayload;
import com.starwars.util.IEntityDataSaver;
import com.starwars.force.SkillData;
import com.starwars.force.ForceData;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import java.util.List;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;

public class ModMessages {
    public static void registerNetworking() {
        PayloadTypeRegistry.playS2C().register(ForceSyncPayload.ID, ForceSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SkillSyncPayload.ID, SkillSyncPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SkillUnlockPayload.ID, SkillUnlockPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(AbilityUsePayload.ID, AbilityUsePayload.CODEC);

        // Register Server Receiver for Skill Unlock
        ServerPlayNetworking.registerGlobalReceiver(SkillUnlockPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                int currentLevel = SkillData.getSkillLevel((IEntityDataSaver) context.player(), payload.skillId());
                int cost = currentLevel + 1;

                if (context.player().experienceLevel >= cost && currentLevel < 5) {
                    context.player().addExperienceLevels(-cost);
                    SkillData.unlockSkill((IEntityDataSaver) context.player(), payload.skillId());
                }
            });
        });

        // Register Server Receiver for Ability Use
        ServerPlayNetworking.registerGlobalReceiver(AbilityUsePayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                IEntityDataSaver playerSaver = (IEntityDataSaver) context.player();
                String ability = payload.abilityId();
                int level = SkillData.getSkillLevel(playerSaver, ability);

                if (level > 0) {
                    if (ability.equals(SkillData.PUSH_SKILL)) {
                         // Force Push Logic
                         int cost = 20 - (level * 2); // Less cost at higher levels
                         if (ForceData.removeForce(playerSaver, cost) > 0 || context.player().isCreative()) {
                             Vec3d look = context.player().getRotationVector();
                             List<Entity> entities = context.player().getWorld().getOtherEntities(context.player(), 
                                     context.player().getBoundingBox().expand(5.0 * level, 2.0, 5.0 * level)); // Range scales with level
                             
                             for (Entity entity : entities) {
                                 if (entity instanceof LivingEntity) {
                                     entity.addVelocity(look.x * level, 0.5, look.z * level);
                                     entity.velocityModified = true;
                                 }
                             }
                         }
                    } else if (ability.equals(SkillData.PULL_SKILL)) {
                         // Force Pull Logic
                         int cost = 20 - (level * 2);
                         if (ForceData.removeForce(playerSaver, cost) > 0 || context.player().isCreative()) {
                             Vec3d playerPos = context.player().getPos();
                             // Reduced range to ensure stability and focus
                             List<Entity> entities = context.player().getWorld().getOtherEntities(context.player(), 
                                     context.player().getBoundingBox().expand(5.0 * level));
                             
                             for (Entity entity : entities) {
                                 // Pull towards player
                                 Vec3d direction = playerPos.subtract(entity.getPos()).normalize();
                                 // Add stronger vertical lift to prevent getting stuck
                                 entity.addVelocity(direction.x * level * 0.2, 0.5, direction.z * level * 0.2);
                                 entity.velocityModified = true;
                             }
                         }
                    } else if (ability.equals(SkillData.LIGHTNING_SKILL)) {
                         // Force Lightning Logic
                         int cost = 25 - (level * 2);
                         if (ForceData.removeForce(playerSaver, cost) > 0 || context.player().isCreative()) {
                             Vec3d start = context.player().getEyePos();
                             Vec3d look = context.player().getRotationVector();
                             double range = 10.0 + (level * 2.0); // 12 to 20 blocks
                             Vec3d end = start.add(look.multiply(range));
                             
                             // Raycast logic (simplified)
                             Box box = context.player().getBoundingBox().expand(range);
                             List<Entity> entities = context.player().getWorld().getOtherEntities(context.player(), box);
                             
                             for (Entity entity : entities) {
                                 if (entity.getBoundingBox().intersects(start, end)) {
                                     // Damage
                                     float damage = 4.0f + (level * 1.5f); // 5.5 to 11.5 damage
                                     entity.damage(context.player().getDamageSources().lightningBolt(), damage);
                                     entity.setOnFireFor(level * 2); // Burn duration
                                     
                                     // Visuals (Particles)
                                     if (context.player().getWorld() instanceof ServerWorld serverWorld) {
                                         serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, entity.getX(), entity.getY() + entity.getHeight()/2, entity.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
                                     }
                                 }
                             }
                         }
                    }
                }
            });
        });
    }
}
