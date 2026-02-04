package software.bernie.geckolib.loading.json.raw;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.class_3518;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.util.JsonUtil;

/**
 * Container class for face UV information, only used in deserialization at startup
 */
public record FaceUV(@Nullable String materialInstance, double[] uv, double[] uvSize) {
	public static JsonDeserializer<FaceUV> deserializer() throws JsonParseException {
		return (json, type, context) -> {
			JsonObject obj = json.getAsJsonObject();
			String materialInstance = class_3518.method_15253(obj, "material_instance", null);
			double[] uv = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "uv", null));
			double[] uvSize = JsonUtil.jsonArrayToDoubleArray(class_3518.method_15292(obj, "uv_size", null));

			return new FaceUV(materialInstance, uv, uvSize);
		};
	}
}
