package software.bernie.geckolib.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1044;
import net.minecraft.class_1049;
import net.minecraft.class_1060;
import net.minecraft.class_2960;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import software.bernie.geckolib.cache.texture.AnimatableTexture;

/**
 * Injection into TextureManager's access point for runtime-derived textures to allow GeckoLib to swap them out with {@link AnimatableTexture} for animated texture purposes
 * <p>
 * Because AnimatedTexture extends {@link net.minecraft.class_1049 SimpleTexture}, the replacement should be seamless
 */
@Mixin(value = class_1060.class, priority = 2000)
public abstract class TextureManagerMixin {
	@Shadow
	public abstract void register(class_2960 path, class_1044 texture);

	@WrapOperation(method = "getTexture(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/texture/AbstractTexture;",
			at = @At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/texture/SimpleTexture;"),
			require = 0)
	private class_1049 geckolib$replaceAnimatableTexture(class_2960 location, Operation<class_1049> original) {
		AnimatableTexture animatableTexture = new AnimatableTexture(location);

		register(location, animatableTexture);

		return animatableTexture.isAnimated() ? animatableTexture : new class_1049(location);
	}

	@WrapWithCondition(method = "getTexture(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/texture/AbstractTexture;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/AbstractTexture;)V"),
			require = 0)
	private boolean geckolib$skipAnimatableTextureRegistration(class_1060 textureManager, class_2960 location, class_1044 texture) {
		return !(texture instanceof AnimatableTexture);
	}
}
