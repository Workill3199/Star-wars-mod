package com.starwars.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.util.Formatting;

import java.util.List;

public class HolocronItem extends Item {
    public HolocronItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            String[] loreMessages = {
                "Peace is a lie, there is only passion... wait, wrong Holocron.",
                "There is no emotion, there is peace.",
                "The Force will be with you, always.",
                "Do or do not. There is no try.",
                "Fear is the path to the dark side.",
                "Luminous beings are we, not this crude matter.",
                "The crystal is the heart of the blade."
            };
            
            String message = loreMessages[world.random.nextInt(loreMessages.length)];
            user.sendMessage(Text.literal("Holocron: " + message).formatted(Formatting.AQUA, Formatting.ITALIC), false);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, net.minecraft.item.TooltipType type) {
        tooltip.add(Text.translatable("item.star_wars_mod.holocron.tooltip").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
