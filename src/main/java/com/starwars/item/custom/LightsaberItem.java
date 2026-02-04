package com.starwars.item.custom;

import com.starwars.component.ModDataComponentTypes;
import com.starwars.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LightsaberItem extends SwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public LightsaberItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, settings.attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));
    }
    
    public String getColor(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.COLOR, "blue");
    }

    public void setColor(ItemStack stack, String color) {
        stack.set(ModDataComponentTypes.COLOR, color);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        boolean active = isActive(stack);

        if (user.isSneaking()) {
            if (!world.isClient) {
                setActive(stack, !active);
                if (!active) {
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.LIGHTSABER_ON, SoundCategory.PLAYERS, 1.0f, 1.0f);
                } else {
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.LIGHTSABER_OFF, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
            return TypedActionResult.success(stack, world.isClient());
        } else {
            if (active) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(stack);
            } else {
                return TypedActionResult.pass(stack);
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return isActive(stack) ? UseAction.BLOCK : UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, net.minecraft.entity.LivingEntity user) {
        return 72000;
    }

    private boolean isActive(ItemStack stack) {
        return Boolean.TRUE.equals(stack.get(ModDataComponentTypes.ACTIVE));
    }

    private void setActive(ItemStack stack, boolean active) {
        stack.set(ModDataComponentTypes.ACTIVE, active);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<LightsaberItem> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
