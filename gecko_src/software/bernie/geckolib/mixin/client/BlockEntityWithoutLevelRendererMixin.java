package software.bernie.geckolib.mixin.client;

import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_756;
import net.minecraft.class_811;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

/**
 * Injection into the BEWLR rendering point to defer to GeckoLib item rendering if applicable
 * <p>
 * Cancels the remainder of the render call if GeckoLib renders, otherwise does nothing
 */
@Mixin(class_756.class)
public class BlockEntityWithoutLevelRendererMixin {
    @Inject(method = "renderByItem", at = @At("HEAD"), cancellable = true)
    public void geckolib$renderGeckolibItem(class_1799 stack, class_811 displayContext, class_4587 poseStack, class_4597 bufferSource, int packedLight, int packedOverlay, CallbackInfo ci) {
        final class_756 geckolibRenderer = GeoRenderProvider.of(stack).getGeoItemRenderer();

        if (geckolibRenderer != null) {
            geckolibRenderer.method_3166(stack, displayContext, poseStack, bufferSource, packedLight, packedOverlay);

            ci.cancel();
        }
    }
}
