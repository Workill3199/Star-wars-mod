package com.starwars.mixin;

import com.starwars.force.SkillData;
import com.starwars.util.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerSpeedMixin {
    @Inject(method = "getMovementSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyMovementSpeed(CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        
        // Now works on both Client and Server because NBT is synced
        int level = SkillData.getSkillLevel((IEntityDataSaver) player, SkillData.SPEED_SKILL);

        if (level > 0) {
            float original = cir.getReturnValue();
            // Increase speed by 10% per level
            cir.setReturnValue(original * (1.0f + (level * 0.1f)));
        }
    }
}
