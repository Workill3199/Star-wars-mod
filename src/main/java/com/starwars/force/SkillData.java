package com.starwars.force;

import com.starwars.networking.packet.SkillSyncPayload;
import com.starwars.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class SkillData {
    public static final String JUMP_SKILL = "force_jump";
    public static final String PUSH_SKILL = "force_push";
    public static final String PULL_SKILL = "force_pull";
    public static final String SPEED_SKILL = "force_speed";

    public static int getSkillLevel(IEntityDataSaver player, String skillId) {
        NbtCompound nbt = player.getPersistentData();
        NbtCompound skills = nbt.getCompound("skills");
        return skills.getInt(skillId);
    }

    public static void setSkillLevel(IEntityDataSaver player, String skillId, int level) {
        NbtCompound nbt = player.getPersistentData();
        NbtCompound skills = nbt.getCompound("skills");
        skills.putInt(skillId, level);
        nbt.put("skills", skills);
        
        syncSkills(skills, (ServerPlayerEntity) player);
    }

    public static void syncSkills(NbtCompound skills, ServerPlayerEntity player) {
        // Convert NbtCompound to Map for Payload (or send NbtCompound if Payload supports it, 
        // but Map is cleaner for the Payload record)
        Map<String, Integer> skillMap = new HashMap<>();
        for (String key : skills.getKeys()) {
            skillMap.put(key, skills.getInt(key));
        }
        ServerPlayNetworking.send(player, new SkillSyncPayload(skillMap));
    }
    
    public static boolean unlockSkill(IEntityDataSaver player, String skillId) {
         int currentLevel = getSkillLevel(player, skillId);
         // Max level check can be done here or in UI
         if (currentLevel < 5) {
             setSkillLevel(player, skillId, currentLevel + 1);
             return true;
         }
         return false;
    }
}
