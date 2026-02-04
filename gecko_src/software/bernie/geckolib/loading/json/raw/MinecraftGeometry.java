package software.bernie.geckolib.loading.json.raw;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.class_3518;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.util.JsonUtil;

/**
 * Container class for generic geometry information, only used in deserialization at startup
 */
public record MinecraftGeometry(Bone[] bones, @Nullable String cape, @Nullable ModelProperties modelProperties) {
	public static JsonDeserializer<MinecraftGeometry> deserializer() throws JsonParseException {
		return (json, type, context) -> {
			JsonObject obj = json.getAsJsonObject();
			Bone[] bones = JsonUtil.jsonArrayToObjectArray(class_3518.method_15292(obj, "bones", new JsonArray(0)), context, Bone.class);
			String cape = class_3518.method_15253(obj, "cape", null);
			ModelProperties modelProperties = class_3518.method_15283(obj, "description", null, context, ModelProperties.class);

			return new MinecraftGeometry(bones, cape, modelProperties);
		};
	}
}
