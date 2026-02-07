package com.starwars.networking.packet;

import com.starwars.StarWarsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SkillUnlockPayload(String skillId) implements CustomPayload {
    public static final CustomPayload.Id<SkillUnlockPayload> ID = new CustomPayload.Id<>(Identifier.of(StarWarsMod.MOD_ID, "skill_unlock"));
    public static final PacketCodec<RegistryByteBuf, SkillUnlockPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            SkillUnlockPayload::skillId,
            SkillUnlockPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
