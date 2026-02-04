package com.starwars.component;

import com.starwars.StarWarsMod;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Boolean> ACTIVE = register("active", builder -> builder.codec(com.mojang.serialization.Codec.BOOL));
    public static final ComponentType<String> COLOR = register("color", builder -> builder.codec(com.mojang.serialization.Codec.STRING));

    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(StarWarsMod.MOD_ID, name), (builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerDataComponentTypes() {
        StarWarsMod.LOGGER.info("Registering Data Component Types for " + StarWarsMod.MOD_ID);
    }
}
