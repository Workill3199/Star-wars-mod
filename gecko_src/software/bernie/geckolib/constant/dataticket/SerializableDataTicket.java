package software.bernie.geckolib.constant.dataticket;

import net.minecraft.class_2960;
import net.minecraft.class_9129;
import net.minecraft.class_9135;
import net.minecraft.class_9139;
import software.bernie.geckolib.constant.DataTickets;

/**
 * Network-compatible {@link DataTicket} implementation
 * <p>
 * Used for sending data from server -> client in an easy manner
 */
public abstract class SerializableDataTicket<D> extends DataTicket<D> {
	public static final class_9139<class_9129, SerializableDataTicket<?>> STREAM_CODEC = class_9139.method_56434(
			class_9135.field_48554,
			SerializableDataTicket::id,
            DataTickets::byName);

	public SerializableDataTicket(String id, Class<? extends D> objectType) {
		super(id, objectType);
	}

	/**
	 * @return The {@link class_9139} for the given SerializableDataTicket
	 */
	public abstract class_9139<? super class_9129, D> streamCodec();

	// Pre-defined typings for use

	/**
	 * Generate a new {@code SerializableDataTicket<Double>} for the given id
	 *
	 * @param id The unique id of your ticket. Include your modid
	 */
	public static SerializableDataTicket<Double> ofDouble(class_2960 id) {
		return new SerializableDataTicket<>(id.toString(), Double.class) {
			@Override
			public class_9139<? super class_9129, Double> streamCodec() {
				return class_9135.field_48553;
			}
		};
	}

	/**
	 * Generate a new {@code SerializableDataTicket<Float>} for the given id
	 *
	 * @param id The unique id of your ticket. Include your modid
	 */
	public static SerializableDataTicket<Float> ofFloat(class_2960 id) {
		return new SerializableDataTicket<>(id.toString(), Float.class) {
			@Override
			public class_9139<? super class_9129, Float> streamCodec() {
				return class_9135.field_48552;
			}
		};
	}

	/**
	 * Generate a new {@code SerializableDataTicket<Boolean>} for the given id
	 *
	 * @param id The unique id of your ticket. Include your modid
	 */
	public static SerializableDataTicket<Boolean> ofBoolean(class_2960 id) {
		return new SerializableDataTicket<>(id.toString(), Boolean.class) {
			@Override
			public class_9139<? super class_9129, Boolean> streamCodec() {
				return class_9135.field_48547;
			}
		};
	}

	/**
	 * Generate a new {@code SerializableDataTicket<Integer>} for the given id
	 *
	 * @param id The unique id of your ticket. Include your modid
	 */
	public static SerializableDataTicket<Integer> ofInt(class_2960 id) {
		return new SerializableDataTicket<>(id.toString(), Integer.class) {
			@Override
			public class_9139<? super class_9129, Integer> streamCodec() {
				return class_9135.field_48550;
			}
		};
	}

	/**
	 * Generate a new {@code SerializableDataTicket<String>} for the given id
	 *
	 * @param id The unique id of your ticket. Include your modid
	 */
	public static SerializableDataTicket<String> ofString(class_2960 id) {
		return new SerializableDataTicket<>(id.toString(), String.class) {
			@Override
			public class_9139<? super class_9129, String> streamCodec() {
				return class_9135.field_48554;
			}
		};
	}

	/**
	 * Generate a new {@code SerializableDataTicket<Enum>} for the given id
	 *
	 * @param id The unique id of your ticket. Include your modid
	 */
	public static <E extends Enum<E>> SerializableDataTicket<E> ofEnum(class_2960 id, Class<E> enumClass) {
		return new SerializableDataTicket<>(id.toString(), enumClass) {
			@Override
			public class_9139<? super class_9129, E> streamCodec() {
				return new class_9139<>() {
                    @Override
                    public E decode(class_9129 buf) {
                        return Enum.valueOf(enumClass, buf.method_19772());
                    }

                    @Override
                    public void encode(class_9129 buf, E data) {
                        buf.method_10814(data.toString());
                    }
                };
			}
		};
	}
}
