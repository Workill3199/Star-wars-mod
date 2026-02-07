package com.starwars.force;

import com.starwars.util.IEntityDataSaver;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ForceEvents {
    public static void registerEvents() {
        // Sync on Join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            IEntityDataSaver player = (IEntityDataSaver) handler.player;
            // Send current force (0 if new)
            ForceData.syncForce(player.getPersistentData().getInt("force"), handler.player);
            // Send skills
            SkillData.syncSkills(player.getPersistentData().getCompound("skills"), handler.player);
        });

        // Regen Force every second (20 ticks)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.age % 20 == 0) { // Every second
                    ForceData.addForce((IEntityDataSaver) player, 1); // Regen 1 force
                }
            }
        });
    }
}
