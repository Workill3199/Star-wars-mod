package com.starwars.force;

import com.starwars.networking.packet.ForceSyncPayload;
import com.starwars.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class ForceData {
    public static int addForce(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int force = nbt.getInt("force");
        if (force + amount >= 100) {
            force = 100;
        } else {
            force += amount;
        }

        nbt.putInt("force", force);
        syncForce(force, (ServerPlayerEntity) player);
        return force;
    }

    public static int removeForce(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int force = nbt.getInt("force");
        if (force - amount < 0) {
            force = 0;
        } else {
            force -= amount;
        }

        nbt.putInt("force", force);
        syncForce(force, (ServerPlayerEntity) player);
        return force;
    }

    public static void syncForce(int force, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new ForceSyncPayload(force));
    }
}
