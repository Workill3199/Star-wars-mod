package software.bernie.geckolib.network.packet;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import net.minecraft.class_1657;
import net.minecraft.class_2540;
import net.minecraft.class_8710;
import net.minecraft.class_9135;
import net.minecraft.class_9139;

public record SingletonAnimTriggerPacket(String syncableId, long instanceId, String controllerName, String animName) implements MultiloaderPacket {
    public static final class_8710.class_9154<SingletonAnimTriggerPacket> TYPE = new class_9154<>(GeckoLibConstants.id("singleton_anim_trigger"));
    public static final class_9139<class_2540, SingletonAnimTriggerPacket> CODEC = class_9139.method_56905(
            class_9135.field_48554,
            SingletonAnimTriggerPacket::syncableId,
            class_9135.field_48551,
            SingletonAnimTriggerPacket::instanceId,
            class_9135.field_48554,
            SingletonAnimTriggerPacket::controllerName,
            class_9135.field_48554,
            SingletonAnimTriggerPacket::animName,
            SingletonAnimTriggerPacket::new);

    @Override
    public class_9154<? extends class_8710> method_56479() {
        return TYPE;
    }

    @Override
    public void receiveMessage(@Nullable class_1657 sender, Consumer<Runnable> workQueue) {
        workQueue.accept(() -> {
            GeoAnimatable animatable = GeckoLibUtil.getSyncedAnimatable(this.syncableId);

            if (animatable != null)
                animatable.getAnimatableInstanceCache().getManagerForId(this.instanceId).tryTriggerAnimation(this.controllerName, this.animName);
        });
    }
}
