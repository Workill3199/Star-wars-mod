package com.starwars.networking.packet;

import com.starwars.StarWarsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AbilityUsePayload(String abilityId) implements CustomPayload {
    public static final CustomPayload.Id<AbilityUsePayload> ID = new CustomPayload.Id<>(Identifier.of(StarWarsMod.MOD_ID, "ability_use"));
    public static final PacketCodec<RegistryByteBuf, AbilityUsePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            AbilityUsePayload::abilityId,
            AbilityUsePayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
