package com.starwars.sound;

import com.starwars.StarWarsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent LIGHTSABER_ON = registerSoundEvent("lightsaber_on");
    public static final SoundEvent LIGHTSABER_OFF = registerSoundEvent("lightsaber_off");
    public static final SoundEvent LIGHTSABER_IDLE = registerSoundEvent("lightsaber_idle");
    public static final SoundEvent LIGHTSABER_SWING = registerSoundEvent("lightsaber_swing");
    public static final SoundEvent BLASTER_FIRE = registerSoundEvent("blaster_fire");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(StarWarsMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        StarWarsMod.LOGGER.info("Registering Mod Sounds for " + StarWarsMod.MOD_ID);
    }
}
