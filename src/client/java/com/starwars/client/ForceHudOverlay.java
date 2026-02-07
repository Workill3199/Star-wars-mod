package com.starwars.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import com.starwars.StarWarsMod;

public class ForceHudOverlay implements HudRenderCallback {
    private static final Identifier FILLED_TEXTURE = Identifier.of(StarWarsMod.MOD_ID, "textures/gui/force_bar_filled.png");
    private static final Identifier EMPTY_TEXTURE = Identifier.of(StarWarsMod.MOD_ID, "textures/gui/force_bar_empty.png");

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        int x = 0;
        int y = 0;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        x = width / 2 + 10; // Right of hotbar
        y = height - 49;    // Above hunger bar

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // Get force amount
        int force = ClientForceData.getForce();
        int maxForce = 100; // Hardcoded for now, should be in Data

        // Draw empty bar (background)
        // context.drawTexture(EMPTY_TEXTURE, x, y, 0, 0, 10, 64, 10, 64); 
        // Using colored rectangles for now until textures are added
        
        // Background (Gray)
        context.fill(x, y, x + 80, y + 8, 0xFF404040);
        
        // Foreground (Cyan/Blue)
        int filledWidth = (int)((float)force / maxForce * 80);
        context.fill(x, y, x + filledWidth, y + 8, 0xFF00FFFF);
        
        // Text
        // context.drawText(client.textRenderer, "Force: " + force, x, y - 10, 0xFFFFFF, true);
    }
}
