package software.bernie.geckolib.service;

import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_8710;
import net.minecraft.class_9139;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.constant.dataticket.SerializableDataTicket;
import software.bernie.geckolib.network.packet.*;

/**
 * Loader-agnostic service interface for GeckoLib's networking functionalities
 */
public interface GeckoLibNetworking {
    static void init() {
        registerPacket(BlockEntityAnimTriggerPacket.TYPE, BlockEntityAnimTriggerPacket.CODEC, true);
        registerPacket(BlockEntityDataSyncPacket.TYPE, BlockEntityDataSyncPacket.CODEC, true);
        registerPacket(EntityAnimTriggerPacket.TYPE, EntityAnimTriggerPacket.CODEC, true);
        registerPacket(EntityDataSyncPacket.TYPE, EntityDataSyncPacket.CODEC, true);
        registerPacket(SingletonAnimTriggerPacket.TYPE, SingletonAnimTriggerPacket.CODEC, true);
        registerPacket(SingletonDataSyncPacket.TYPE, SingletonDataSyncPacket.CODEC, true);
    }

    /**
     * Register a GeckoLib packet for use
     */
    @ApiStatus.Internal
    private static <B extends class_2540, P extends MultiloaderPacket> void registerPacket(class_8710.class_9154<P> payloadType, class_9139<B, P> codec, boolean isClientBound) {
        GeckoLibServices.NETWORK.registerPacketInternal(payloadType, codec, isClientBound);
    }

    /**
     * Register a GeckoLib packet for use
     * <p>
     * <b><u>FOR GECKOLIB USE ONLY</u></b>
     */
    @ApiStatus.Internal
    <B extends class_2540, P extends MultiloaderPacket> void registerPacketInternal(class_8710.class_9154<P> payloadType, class_9139<B, P> codec, boolean isClientBound);

    /**
     * Send a packet to all players currently tracking a given entity
     * <p>
     * Good as a shortcut for sending a packet to all players that may have an interest in a given entity or its dealings
     * <p>
     * Will also send the packet to the entity itself if the entity is also a player
     */
    void sendToAllPlayersTrackingEntity(MultiloaderPacket packet, class_1297 trackingEntity);

    /**
     * Send a packet to all players tracking a given block position
     */
    void sendToAllPlayersTrackingBlock(MultiloaderPacket packet, class_3218 level, class_2338 pos);

    /**
     * Send a packet to the given player
     */
    void sendToPlayer(MultiloaderPacket packet, class_3222 player);

    /**
     * Sync a {@link SerializableDataTicket} from server to clientside for the given block
     */
    default <D> void syncBlockEntityAnimData(class_2338 pos, SerializableDataTicket<D> dataTicket, D data, class_3218 level) {
        sendToAllPlayersTrackingBlock(new BlockEntityDataSyncPacket<>(pos, dataTicket, data), level, pos);
    }
    /**
     * {@link software.bernie.geckolib.animatable.GeoBlockEntity#triggerAnim(String, String) Trigger} an animation for the given {@link software.bernie.geckolib.animatable.GeoBlockEntity GeoBlockEntity}
     */
    default void triggerBlockEntityAnim(class_2338 pos, @Nullable String controllerName, String animName, class_3218 level) {
        sendToAllPlayersTrackingBlock(new BlockEntityAnimTriggerPacket(pos, controllerName, animName), level, pos);
    }

    /**
     * Sync a {@link SerializableDataTicket} from server to clientside for the given entity
     */
    default <D> void syncEntityAnimData(class_1297 entity, boolean isReplacedEntity, SerializableDataTicket<D> dataTicket, D data) {
        sendToAllPlayersTrackingEntity(new EntityDataSyncPacket<>(entity.method_5628(), isReplacedEntity, dataTicket, data), entity);
    }
    /**
     * {@link software.bernie.geckolib.animatable.GeoEntity#triggerAnim(String, String) Trigger} an animation for the given {@link software.bernie.geckolib.animatable.GeoEntity GeoEntity}
     */
    default void triggerEntityAnim(class_1297 entity, boolean isReplacedEntity, @Nullable String controllerName, String animName) {
        sendToAllPlayersTrackingEntity(new EntityAnimTriggerPacket(entity.method_5628(), isReplacedEntity, controllerName, animName), entity);
    }

    /**
     * Sync a {@link SerializableDataTicket} from server to clientside for the given {@link software.bernie.geckolib.animatable.SingletonGeoAnimatable SingletonGeoAnimatable}
     */
    default <D> void syncSingletonAnimData(long instanceId, SerializableDataTicket<D> dataTicket, D data, class_1297 entityToTrack) {
        sendToAllPlayersTrackingEntity(new SingletonDataSyncPacket<>(getClass().toString(), instanceId, dataTicket, data), entityToTrack);
    }
    /**
     * {@link software.bernie.geckolib.animatable.SingletonGeoAnimatable#triggerAnim(class_1297, long, String, String) Trigger} an animation for the given {@link software.bernie.geckolib.animatable.SingletonGeoAnimatable SingletonGeoAnimatable}
     */
    default void triggerSingletonAnim(String animatableClassName, class_1297 entityToTrack, long instanceId, @Nullable String controllerName, String animName) {
        sendToAllPlayersTrackingEntity(new SingletonAnimTriggerPacket(animatableClassName, instanceId, controllerName, animName), entityToTrack);
    }
}
