package com.starwars.event;

import com.starwars.force.SkillData;
import com.starwars.networking.packet.AbilityUsePayload;
import com.starwars.screen.SkillTreeScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_STARWARS = "key.category.starwars";
    public static final String KEY_OPEN_SKILLS = "key.starwars.open_skills";
    public static final String KEY_FORCE_PUSH = "key.starwars.force_push";
    public static final String KEY_FORCE_PULL = "key.starwars.force_pull";

    public static KeyBinding openSkillsKey;
    public static KeyBinding forcePushKey;
    public static KeyBinding forcePullKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openSkillsKey.wasPressed()) {
                client.setScreen(new SkillTreeScreen());
            }
            while (forcePushKey.wasPressed()) {
                ClientPlayNetworking.send(new AbilityUsePayload(SkillData.PUSH_SKILL));
            }
            while (forcePullKey.wasPressed()) {
                ClientPlayNetworking.send(new AbilityUsePayload(SkillData.PULL_SKILL));
            }
        });
    }

    public static void register() {
        openSkillsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_SKILLS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KEY_CATEGORY_STARWARS
        ));

        forcePushKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FORCE_PUSH,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KEY_CATEGORY_STARWARS
        ));

        forcePullKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FORCE_PULL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                KEY_CATEGORY_STARWARS
        ));

        registerKeyInputs();
    }
}
