package com.starwars.item.custom;

import com.starwars.StarWarsMod;
import com.starwars.component.ModDataComponentTypes;
import com.starwars.sound.ModSounds;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LightsaberItem extends SwordItem implements GeoItem {
    public static Object renderer;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final float baseDamage;
    private final float attackSpeed;

    public LightsaberItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, settings);
        this.baseDamage = attackDamage + toolMaterial.getAttackDamage();
        this.attackSpeed = attackSpeed;
    }

    private void updateAttributes(ItemStack stack, boolean active) {
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
        
        float currentDamage = active ? this.baseDamage * 2 : 1.0f;

        builder.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(Identifier.of(StarWarsMod.MOD_ID, "lightsaber_damage"),
                        currentDamage, EntityAttributeModifier.Operation.ADD_VALUE),
                AttributeModifierSlot.MAINHAND);

        builder.add(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(Identifier.of(StarWarsMod.MOD_ID, "lightsaber_attack_speed"),
                        this.attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                AttributeModifierSlot.MAINHAND);

        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        boolean active = isActive(stack);

        if (user.isSneaking()) {
            if (!world.isClient) {
                boolean newState = !active;
                setActive(stack, newState);
                updateAttributes(stack, newState);

                float pitch = newState ? 1.0f : 0.8f;
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        newState ? ModSounds.LIGHTSABER_ON : ModSounds.LIGHTSABER_OFF,
                        SoundCategory.PLAYERS, 1.0f, pitch);
            }
            return TypedActionResult.success(stack, world.isClient());
        } else {
            if (active) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(stack);
            }
            return TypedActionResult.pass(stack);
        }
    }

    // --- COMPONENTES Y DATOS ---
    public String getColor(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.COLOR, "blue");
    }

    public void setColor(ItemStack stack, String color) {
        stack.set(ModDataComponentTypes.COLOR, color);
    }

    private boolean isActive(ItemStack stack) {
        return Boolean.TRUE.equals(stack.get(ModDataComponentTypes.ACTIVE));
    }

    private void setActive(ItemStack stack, boolean active) {
        stack.set(ModDataComponentTypes.ACTIVE, active);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return isActive(stack) ? UseAction.BLOCK : UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    // --- ANIMACIONES (GeckoLib) ---
    private static final RawAnimation DEPLOY = RawAnimation.begin().thenPlay("animation.lightsaber.deploy").thenLoop("animation.lightsaber.active");
    private static final RawAnimation RETRACT = RawAnimation.begin().thenPlay("animation.lightsaber.retract").thenLoop("animation.lightsaber.inactive");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            ItemStack stack = state.getData(software.bernie.geckolib.constant.DataTickets.ITEMSTACK);
            if (stack != null && isActive(stack)) {
                return state.setAndContinue(DEPLOY);
            }
            return state.setAndContinue(RETRACT);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}