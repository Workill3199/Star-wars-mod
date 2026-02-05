package com.starwars.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class MandalorianArmorItem extends ArmorItem {
    public MandalorianArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && entity instanceof PlayerEntity player) {
            if (this.getType() == Type.CHESTPLATE) {
                ItemStack chestStack = player.getEquippedStack(EquipmentSlot.CHEST);
                boolean isWearing = chestStack.getItem() == this;
                
                if (isWearing) {
                    if (!player.getAbilities().allowFlying) {
                        player.getAbilities().allowFlying = true;
                        player.sendAbilitiesUpdate();
                    }
                } else {
                    // Item is in inventory but not worn
                    if (!player.isCreative() && !player.isSpectator() && player.getAbilities().allowFlying) {
                        // Check if they are wearing another jetpack (future proofing)
                        // For now, just disable
                        player.getAbilities().allowFlying = false;
                        player.getAbilities().flying = false;
                        player.sendAbilitiesUpdate();
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
