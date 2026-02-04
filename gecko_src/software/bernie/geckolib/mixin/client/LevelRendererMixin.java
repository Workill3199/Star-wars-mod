package software.bernie.geckolib.mixin.client;

import net.minecraft.class_4184;
import net.minecraft.class_757;
import net.minecraft.class_761;
import net.minecraft.class_765;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MolangQueries;

/**
 * Capture pre-render data for Molang queries
 */
@Mixin(class_761.class)
public class LevelRendererMixin {
    @Shadow
    private int renderedEntities;

    @Inject(method = "renderLevel", at = @At(value = "HEAD"))
    public void geckolib$captureRenderedEntities(class_9779 deltaTracker, boolean renderBlockOutline, class_4184 camera, class_757 gameRenderer, class_765 lightTexture, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        final int renderedEntityCount = this.renderedEntities;

        MathParser.setVariable(MolangQueries.ACTOR_COUNT, () -> renderedEntityCount);
    }
}
