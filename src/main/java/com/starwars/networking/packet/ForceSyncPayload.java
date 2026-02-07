package com.starwars.networking.packet;

import com.starwars.StarWarsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ForceSyncPayload(int force) implements CustomPayload {
    public static final CustomPayload.Id<ForceSyncPayload> ID = new CustomPayload.Id<>(Identifier.of(StarWarsMod.MOD_ID, "force_sync"));
    public static final PacketCodec<RegistryByteBuf, ForceSyncPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, ForceSyncPayload::force, ForceSyncPayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
