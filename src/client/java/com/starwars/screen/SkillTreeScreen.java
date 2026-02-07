package com.starwars.screen;

import com.starwars.client.ClientSkillData;
import com.starwars.force.SkillData;
import com.starwars.networking.packet.SkillUnlockPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class SkillTreeScreen extends Screen {
    public SkillTreeScreen() {
        super(Text.literal("Force Skills"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Force Jump
        addSkillButton(centerX - 100, centerY - 50, SkillData.JUMP_SKILL, "Force Jump");

        // Force Push
        addSkillButton(centerX + 20, centerY - 50, SkillData.PUSH_SKILL, "Force Push");

        // Force Pull
        addSkillButton(centerX - 100, centerY + 20, SkillData.PULL_SKILL, "Force Pull");

        // Force Speed
        addSkillButton(centerX + 20, centerY + 20, SkillData.SPEED_SKILL, "Force Speed");
    }

    private void addSkillButton(int x, int y, String skillId, String name) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal(name), button -> {
            ClientPlayNetworking.send(new SkillUnlockPayload(skillId));
        })
        .position(x, y)
        .size(80, 20)
        .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        // Draw Levels
        drawSkillLevel(context, this.width / 2 - 100, this.height / 2 - 60, SkillData.JUMP_SKILL);
        drawSkillLevel(context, this.width / 2 + 20, this.height / 2 - 60, SkillData.PUSH_SKILL);
        drawSkillLevel(context, this.width / 2 - 100, this.height / 2 + 10, SkillData.PULL_SKILL);
        drawSkillLevel(context, this.width / 2 + 20, this.height / 2 + 10, SkillData.SPEED_SKILL);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawSkillLevel(DrawContext context, int x, int y, String skillId) {
        int level = ClientSkillData.getSkillLevel(skillId);
        int color = level > 0 ? 0x00FF00 : 0xAAAAAA;
        context.drawText(this.textRenderer, "Lvl: " + level, x, y, color, true);
    }
}
