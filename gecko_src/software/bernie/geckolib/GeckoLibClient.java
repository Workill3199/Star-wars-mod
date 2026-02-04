package software.bernie.geckolib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.class_2960;
import net.minecraft.class_3264;
import net.minecraft.class_3300;
import net.minecraft.class_3695;
import net.minecraft.class_8710;
import org.jetbrains.annotations.ApiStatus;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.network.packet.MultiloaderPacket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Main GeckoLib client entrypoint
 */
public class GeckoLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(class_3264.field_14188)
                .registerReloadListener(new IdentifiableResourceReloadListener() {
                    @Override
                    public class_2960 getFabricId() {
                        return GeckoLibConstants.id("models_animations");
                    }

                    @Override
                    public CompletableFuture<Void> method_25931(class_4045 synchronizer, class_3300 manager,
                                                          class_3695 prepareProfiler, class_3695 applyProfiler, Executor prepareExecutor,
                                                          Executor applyExecutor) {
                        return GeckoLibCache.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
                    }
                });
    }

    @ApiStatus.Internal
    public static <P extends MultiloaderPacket> void registerPacket(class_8710.class_9154<P> packetType) {
        ClientPlayNetworking.registerGlobalReceiver(packetType, (packet, context) -> packet.receiveMessage(context.player(), context.client()::execute));
    }
}
