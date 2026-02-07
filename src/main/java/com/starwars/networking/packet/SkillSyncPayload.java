package com.starwars.networking.packet;

import com.starwars.StarWarsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public record SkillSyncPayload(Map<String, Integer> skills) implements CustomPayload {
    public static final CustomPayload.Id<SkillSyncPayload> ID = new CustomPayload.Id<>(Identifier.of(StarWarsMod.MOD_ID, "skill_sync"));
    public static final PacketCodec<RegistryByteBuf, SkillSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.map(HashMap::new, PacketCodecs.STRING, PacketCodecs.INTEGER),
            SkillSyncPayload::skills,
            SkillSyncPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
