package software.bernie.geckolib.loading.json.raw;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.class_3518;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.util.JsonUtil;

/**
 * Container class for locator class information, only used in deserialization at startup
 */
public record LocatorClass(@Nullable Boolean ignoreInheritedScale, double[] offset, double[] rotation) {
	public static JsonDeserializer<LocatorClass> deserializer() throws JsonParseException {
		return (json, type, context) -> {
			JsonObject obj = json.getAsJsonObject();
			Boolean ignoreInheritedScale = JsonUtil.getOptionalBoolean(obj, "ignore_inherited_scale");
			double[] offset = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "offset", null));
			double[] rotation = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "rotation", null));

			return new LocatorClass(ignoreInheritedScale, offset, rotation);
		};
	}
}
