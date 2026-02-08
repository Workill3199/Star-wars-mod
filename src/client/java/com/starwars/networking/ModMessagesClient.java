package com.starwars.networking;

import com.starwars.client.ClientForceData;
import com.starwars.client.ClientSkillData;
import com.starwars.networking.packet.ForceSyncPayload;
import com.starwars.networking.packet.SkillSyncPayload;
import com.starwars.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.NbtCompound;

public class ModMessagesClient {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ForceSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientForceData.setForce(payload.force());
                // Also update local player NBT for mixins
                if (context.client().player != null) {
                     IEntityDataSaver player = (IEntityDataSaver) context.client().player;
                     player.getPersistentData().putInt("force", payload.force());
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(SkillSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientSkillData.setSkills(payload.skills());
                
                // Also update local player NBT for mixins
                if (context.client().player != null) {
                     IEntityDataSaver player = (IEntityDataSaver) context.client().player;
                     NbtCompound skills = new NbtCompound();
                     payload.skills().forEach(skills::putInt);
                     player.getPersistentData().put("skills", skills);
                }
            });
        });
    }
}
