package software.bernie.geckolib.loading.json.raw;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.class_3518;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.util.JsonUtil;

/**
 * Container class for cube information, only used in deserialization at startup
 */
public record Cube(@Nullable Double inflate, @Nullable Boolean mirror, double[] origin, double[] pivot, double[] rotation, double[] size, UVUnion uv) {
	public static JsonDeserializer<Cube> deserializer() throws JsonParseException {
		return (json, type, context) -> {
			JsonObject obj = json.getAsJsonObject();
			Double inflate = JsonUtil.getOptionalDouble(obj, "inflate");
			Boolean mirror = JsonUtil.getOptionalBoolean(obj, "mirror");
			double[] origin = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "origin", null));
			double[] pivot = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "pivot", null));
			double[] rotation = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "rotation", null));
			double[] size = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "size", null));
			UVUnion uvUnion = class_3518.method_15283(obj, "uv", null, context, UVUnion.class);

			return new Cube(inflate, mirror, origin, pivot, rotation, size, uvUnion);
		};
	}
}
