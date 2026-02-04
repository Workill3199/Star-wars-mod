package software.bernie.geckolib.constant;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.constant.dataticket.SerializableDataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_2350;
import net.minecraft.class_2586;
import net.minecraft.class_811;

/**
 * Stores the default (builtin) {@link DataTicket DataTickets} used in Geckolib
 * <p>
 * Additionally handles registration of {@link SerializableDataTicket SerializableDataTickets}
 */
public final class DataTickets {
	private static final Map<String, SerializableDataTicket<?>> SERIALIZABLE_TICKETS = new ConcurrentHashMap<>();
	
	// Builtin tickets
	// These tickets are used by GeckoLib by default, usually added in by the GeoRenderer for use in animations
	public static final DataTicket<class_2586> BLOCK_ENTITY = new DataTicket<>("block_entity", class_2586.class);
	public static final DataTicket<class_1799> ITEMSTACK = new DataTicket<>("itemstack", class_1799.class);
	public static final DataTicket<class_1297> ENTITY = new DataTicket<>("entity", class_1297.class);
	public static final DataTicket<class_1304> EQUIPMENT_SLOT = new DataTicket<>("equipment_slot", class_1304.class);
	public static final DataTicket<EntityModelData> ENTITY_MODEL_DATA = new DataTicket<>("entity_model_data", EntityModelData.class);
	public static final DataTicket<Double> TICK = new DataTicket<>("tick", Double.class);
	public static final DataTicket<class_811> ITEM_RENDER_PERSPECTIVE = new DataTicket<>("item_render_perspective", class_811.class);
	
	// Builtin serializable tickets
	// These are not used anywhere by default, but are provided as examples and for ease of use
	public static final SerializableDataTicket<Integer> ANIM_STATE = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofInt(GeckoLibConstants.id("anim_state")));
	public static final SerializableDataTicket<String> ANIM = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofString(GeckoLibConstants.id("anim")));
	public static final SerializableDataTicket<Integer> USE_TICKS = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofInt(GeckoLibConstants.id("use_ticks")));
	public static final SerializableDataTicket<Boolean> ACTIVE = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofBoolean(GeckoLibConstants.id("active")));
	public static final SerializableDataTicket<Boolean> OPEN = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofBoolean(GeckoLibConstants.id("open")));
	public static final SerializableDataTicket<Boolean> CLOSED = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofBoolean(GeckoLibConstants.id("closed")));
	public static final SerializableDataTicket<class_2350> DIRECTION = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofEnum(GeckoLibConstants.id("direction"), class_2350.class));

	@Nullable
	public static SerializableDataTicket<?> byName(String id) {
		return SERIALIZABLE_TICKETS.getOrDefault(id, null);
	}

	/**
	 * Register a {@link SerializableDataTicket} with GeckoLib for handling custom data transmission
	 * <p>
	 * It is recommended you don't call this directly, and instead call it via {@link GeckoLibUtil#addDataTicket}
	 *
	 * @param ticket The SerializableDataTicket instance to register
	 * @return The registered instance
	 */
	public static <D> SerializableDataTicket<D> registerSerializable(SerializableDataTicket<D> ticket) {
		SerializableDataTicket<?> existingTicket = SERIALIZABLE_TICKETS.putIfAbsent(ticket.id(), ticket);

		if (existingTicket != null)
			GeckoLibConstants.LOGGER.error("Duplicate SerializableDataTicket registered! This will cause issues. Existing: " + existingTicket.id() + ", New: " + ticket.id());

		return ticket;
	}
}
