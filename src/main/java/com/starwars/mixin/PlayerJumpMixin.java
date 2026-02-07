package com.starwars.mixin;

import com.starwars.force.SkillData;
import com.starwars.item.ModItems;
import com.starwars.item.custom.LightsaberItem;
import com.starwars.util.IEntityDataSaver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class PlayerJumpMixin {
    @Inject(method = "getJumpVelocity", at = @At("RETURN"), cancellable = true)
    private void modifyJumpVelocity(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        if (entity instanceof PlayerEntity player) {
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof LightsaberItem) {
                int level = SkillData.getSkillLevel((IEntityDataSaver) player, SkillData.JUMP_SKILL);
                if (level > 0) {
                    float original = cir.getReturnValue();
                    cir.setReturnValue(original + (level * 0.1f)); // Increase jump height
                }
            }
        }
    }
}
