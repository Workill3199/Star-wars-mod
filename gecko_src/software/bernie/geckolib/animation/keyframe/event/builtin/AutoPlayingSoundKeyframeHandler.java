package software.bernie.geckolib.animation.keyframe.event.builtin;

import net.minecraft.class_1297;
import net.minecraft.class_1569;
import net.minecraft.class_243;
import net.minecraft.class_2586;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_3419;
import net.minecraft.class_7923;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.util.ClientUtil;

/**
 * Built-in helper for a {@link software.bernie.geckolib.animation.AnimationController.SoundKeyframeHandler SoundKeyframeHandler} that automatically plays the sound defined in the keyframe data
 * <p>
 * Due to an inability to determine the position of the sound for all animatables, this handler only supports {@link software.bernie.geckolib.animatable.GeoEntity GeoEntity} and {@link software.bernie.geckolib.animatable.GeoBlockEntity GeoBlockEntity}
 * <p>
 * The expected keyframe data format is one of the below:
 * <pre>{@code
 * namespace:soundid
 * namespace:soundid|volume|pitch
 * }</pre>
 */
public class AutoPlayingSoundKeyframeHandler<A extends GeoAnimatable> implements AnimationController.SoundKeyframeHandler<A> {
    @Override
    public void handle(SoundKeyframeEvent<A> event) {
        String[] segments = event.getKeyframeData().getSound().split("\\|");
        class_3414 sound = class_7923.field_41172.method_10223(class_2960.method_29186(segments[0]).getOrThrow());

        if (sound != null) {
            class_1297 entity = event.getAnimatable() instanceof class_1297 e ? e : null;
            class_243 position = entity != null ? entity.method_19538() : event.getAnimatable() instanceof class_2586 blockEntity ? blockEntity.method_11016().method_46558() : null;

            if (position != null) {
                float volume = segments.length > 1 ? Float.parseFloat(segments[1]) : 1;
                float pitch = segments.length > 2 ? Float.parseFloat(segments[2]) : 1;
                class_3419 source = entity == null ? class_3419.field_15245 : entity instanceof class_1569 ? class_3419.field_15251 : class_3419.field_15254;

                ClientUtil.getLevel().method_43128(null, position.field_1352, position.field_1351, position.field_1350, sound, source, volume, pitch);
            }
        }
    }
}
