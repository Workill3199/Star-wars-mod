package software.bernie.geckolib.loading.json.raw;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.class_3518;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.util.JsonUtil;

/**
 * Container class for model property information, only used in deserialization at startup
 */
public record ModelProperties(@Nullable Boolean animationArmsDown, @Nullable Boolean animationArmsOutFront,
							  @Nullable Boolean animationDontShowArmor, @Nullable Boolean animationInvertedCrouch,
							  @Nullable Boolean animationNoHeadBob, @Nullable Boolean animationSingleArmAnimation,
							  @Nullable Boolean animationSingleLegAnimation, @Nullable Boolean animationStationaryLegs,
							  @Nullable Boolean animationStatueOfLibertyArms, @Nullable Boolean animationUpsideDown,
							  @Nullable String identifier, @Nullable Boolean preserveModelPose,
							  double textureHeight, double textureWidth,
							  @Nullable Double visibleBoundsHeight, double[] visibleBoundsOffset,
							  @Nullable Double visibleBoundsWidth) {
	public static JsonDeserializer<ModelProperties> deserializer() throws JsonParseException {
		return (json, type, context) -> {
			JsonObject obj = json.getAsJsonObject();
			Boolean animationArmsDown = JsonUtil.getOptionalBoolean(obj, "animationArmsDown");
			Boolean animationArmsOutFront = JsonUtil.getOptionalBoolean(obj, "animationArmsOutFront");
			Boolean animationDontShowArmor = JsonUtil.getOptionalBoolean(obj, "animationDontShowArmor");
			Boolean animationInvertedCrouch = JsonUtil.getOptionalBoolean(obj, "animationInvertedCrouch");
			Boolean animationNoHeadBob = JsonUtil.getOptionalBoolean(obj, "animationNoHeadBob");
			Boolean animationSingleArmAnimation = JsonUtil.getOptionalBoolean(obj, "animationSingleArmAnimation");
			Boolean animationSingleLegAnimation = JsonUtil.getOptionalBoolean(obj, "animationSingleLegAnimation");
			Boolean animationStationaryLegs = JsonUtil.getOptionalBoolean(obj, "animationStationaryLegs");
			Boolean animationStatueOfLibertyArms = JsonUtil.getOptionalBoolean(obj, "animationStatueOfLibertyArms");
			Boolean animationUpsideDown = JsonUtil.getOptionalBoolean(obj, "animationUpsideDown");
			String identifier = class_3518.method_15253(obj, "identifier", null);
			Boolean preserveModelPose = JsonUtil.getOptionalBoolean(obj, "preserve_model_pose");
			double textureHeight = class_3518.method_34927(obj, "texture_height");
			double textureWidth = class_3518.method_34927(obj, "texture_width");
			Double visibleBoundsHeight = JsonUtil.getOptionalDouble(obj, "visible_bounds_height");
			double[] visibleBoundsOffset = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "visible_bounds_offset", null));
			Double visibleBoundsWidth = JsonUtil.getOptionalDouble(obj, "visible_bounds_width");

			return new ModelProperties(animationArmsDown, animationArmsOutFront, animationDontShowArmor, animationInvertedCrouch,
					animationNoHeadBob, animationSingleArmAnimation, animationSingleLegAnimation, animationStationaryLegs,
					animationStatueOfLibertyArms, animationUpsideDown, identifier, preserveModelPose, textureHeight,
					textureWidth, visibleBoundsHeight, visibleBoundsOffset, visibleBoundsWidth);
		};
	}
}
