package software.bernie.geckolib.util;

import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_310;

/**
 * Helper class for segregating client-side code
 */
public final class ClientUtil {
	/**
	 * Get the player on the client
	 */
	public static class_1657 getClientPlayer() {
		return class_310.method_1551().field_1724;
	}

	/**
	 * Gets the current level on the client
	 */
	public static class_1937 getLevel() {
		return class_310.method_1551().field_1687;
	}
}
