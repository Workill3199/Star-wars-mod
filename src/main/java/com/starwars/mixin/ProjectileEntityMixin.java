package com.starwars.mixin;

import com.starwars.component.ModDataComponentTypes;
import com.starwars.item.ModItems;
import com.starwars.item.custom.LightsaberItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {

    @Shadow public abstract void setOwner(Entity entity);

    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof PlayerEntity player) {
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof LightsaberItem) {
                // Check if active (we need to access the helper, or check NBT directly)
                // Since isActive is private in LightsaberItem, we check Data Component manually
                if (Boolean.TRUE.equals(stack.get(ModDataComponentTypes.ACTIVE))) {
                    // Reflect!
                    Entity self = (Entity)(Object)this;
                    Vec3d velocity = self.getVelocity();
                    self.setVelocity(velocity.multiply(-1));
                    this.setOwner(player);
                    
                    // Cancel the hit so it doesn't damage the player
                    ci.cancel();
                }
            }
        }
    }
}
