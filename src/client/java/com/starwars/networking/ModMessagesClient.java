package com.starwars.networking;

import com.starwars.client.ClientForceData;
import com.starwars.client.ClientSkillData;
import com.starwars.networking.packet.ForceSyncPayload;
import com.starwars.networking.packet.SkillSyncPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModMessagesClient {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ForceSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientForceData.setForce(payload.force());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(SkillSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientSkillData.setSkills(payload.skills());
            });
        });
    }
}
