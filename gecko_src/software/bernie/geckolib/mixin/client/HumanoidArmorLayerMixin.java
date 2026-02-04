package software.bernie.geckolib.mixin.client;

import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_572;
import net.minecraft.class_970;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.Color;

/**
 * Injection into the render point for armour on HumanoidModels (Players, Zombies, etc) to defer to GeckoLib item-armor rendering as applicable
 * <p>
 * Does nothing if GeckoLib has nothing to handle for the given arguments
 */
@Mixin(class_970.class)
public abstract class HumanoidArmorLayerMixin<T extends class_1309, A extends class_572<T>> {
    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;usesInnerModel(Lnet/minecraft/world/entity/EquipmentSlot;)Z"), cancellable = true)
    public void geckolib$renderGeckoLibModel(class_4587 poseStack, class_4597 bufferSource, T entity, class_1304 equipmentSlot, int packedLight, A baseModel, CallbackInfo ci) {
        final class_1799 stack = entity.method_6118(equipmentSlot);
        final class_572<?> geckolibModel = GeoRenderProvider.of(stack).getGeoArmorRenderer(entity, stack, equipmentSlot, baseModel);

        if (geckolibModel != null) {
            if (geckolibModel instanceof GeoArmorRenderer<?> geoArmorRenderer)
                geoArmorRenderer.prepForRender(entity, stack, equipmentSlot, baseModel);

            baseModel.method_2818((A)geckolibModel);
            geckolibModel.method_2828(poseStack, null, packedLight, class_4608.field_21444, Color.WHITE.argbInt());
            ci.cancel();
        }
    }
}