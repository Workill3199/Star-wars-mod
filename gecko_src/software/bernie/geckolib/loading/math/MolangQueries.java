package software.bernie.geckolib.loading.math;

import com.google.common.collect.Streams;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1407;
import net.minecraft.class_1409;
import net.minecraft.class_1410;
import net.minecraft.class_1412;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2350;
import net.minecraft.class_2586;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4582;
import net.minecraft.class_5146;
import net.minecraft.class_5354;
import net.minecraft.class_5498;
import net.minecraft.class_5766;
import net.minecraft.class_6025;
import net.minecraft.class_9817;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.navigation.*;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.loading.math.value.Variable;
import software.bernie.geckolib.util.ClientUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToDoubleFunction;

/**
 * Helper class for the builtin <a href="https://learn.microsoft.com/en-us/minecraft/creator/reference/content/molangreference/examples/molangconcepts/molangintroduction?view=minecraft-bedrock-stable">Molang</a> query string constants for the {@link MathParser}.
 * <p>
 * These do not constitute a definitive list of queries; merely the default ones
 * <p>
 * Note that the implementations of the various queries in GeckoLib may not necessarily match its implementation in Bedrock
 */
public final class MolangQueries {
	public static final String ACTOR_COUNT = "query.actor_count";
	public static final String ANIM_TIME = "query.anim_time";
	public static final String BLOCKING = "query.blocking";
	public static final String BLOCK_STATE = "query.block_state";
	public static final String BODY_X_ROTATION = "query.body_x_rotation";
	public static final String BODY_Y_ROTATION = "query.body_y_rotation";
	public static final String CAN_CLIMB = "query.can_climb";
	public static final String CAN_FLY = "query.can_fly";
	public static final String CAN_SWIM = "query.can_swim";
	public static final String CAN_WALK = "query.can_walk";
	public static final String CARDINAL_FACING = "query.cardinal_facing";
	public static final String CARDINAL_FACING_2D = "query.cardinal_facing_2d";
	public static final String CARDINAL_PLAYER_FACING = "query.cardinal_player_facing";
	public static final String DAY = "query.day";
	public static final String DEATH_TICKS = "query.death_ticks";
	public static final String DISTANCE_FROM_CAMERA = "query.distance_from_camera";
	public static final String EQUIPMENT_COUNT = "query.equipment_count";
	public static final String FRAME_ALPHA = "query.frame_alpha";
	public static final String GET_ACTOR_INFO_ID = "query.get_actor_info_id";
	public static final String GROUND_SPEED = "query.ground_speed";
	public static final String HAS_CAPE = "query.has_cape";
	public static final String HAS_COLLISION = "query.has_collision";
	public static final String HAS_GRAVITY = "query.has_gravity";
	public static final String HAS_HEAD_GEAR = "query.has_head_gear";
	public static final String HAS_OWNER = "query.has_owner";
	public static final String HAS_PLAYER_RIDER = "query.has_player_rider";
	public static final String HAS_RIDER = "query.has_rider";
	public static final String HEAD_X_ROTATION = "query.head_x_rotation";
	public static final String HEAD_Y_ROTATION = "query.head_y_rotation";
	public static final String HEALTH = "query.health";
	public static final String HURT_TIME = "query.hurt_time";
	public static final String INVULNERABLE_TICKS = "query.invulnerable_ticks";
	public static final String IS_ALIVE = "query.is_alive";
	public static final String IS_ANGRY = "query.is_angry";
	public static final String IS_BABY = "query.is_baby";
	public static final String IS_BREATHING = "query.is_breathing";
	public static final String IS_ENCHANTED = "query.is_enchanted";
	public static final String IS_FIRE_IMMUNE = "query.is_fire_immune";
	public static final String IS_FIRST_PERSON = "query.is_first_person";
	public static final String IS_INVISIBLE = "query.is_invisible";
	public static final String IS_IN_CONTACT_WITH_WATER = "query.is_in_contact_with_water";
	public static final String IS_IN_LAVA = "query.is_in_lava";
	public static final String IS_IN_WATER = "query.is_in_water";
	public static final String IS_IN_WATER_OR_RAIN = "query.is_in_water_or_rain";
	public static final String IS_LEASHED = "query.is_leashed";
	public static final String IS_MOVING = "query.is_moving";
	public static final String IS_ON_FIRE = "query.is_on_fire";
	public static final String IS_ON_GROUND = "query.is_on_ground";
	public static final String IS_POWERED = "query.is_powered";
	public static final String IS_RIDING = "query.is_riding";
	public static final String IS_SADDLED = "query.is_saddled";
	public static final String IS_SILENT = "query.is_silent";
	public static final String IS_SLEEPING = "query.is_sleeping";
	public static final String IS_SNEAKING = "query.is_sneaking";
	public static final String IS_SPRINTING = "query.is_sprinting";
	public static final String IS_STACKABLE = "query.is_stackable";
	public static final String IS_SWIMMING = "query.is_swimming";
	public static final String IS_USING_ITEM = "query.is_using_item";
	public static final String IS_WALL_CLIMBING = "query.is_wall_climbing";
	public static final String ITEM_MAX_USE_DURATION = "query.item_max_use_duration";
	public static final String LIFE_TIME = "query.life_time";
	public static final String MAIN_HAND_ITEM_MAX_DURATION = "query.main_hand_item_max_duration";
	public static final String MAIN_HAND_ITEM_USE_DURATION = "query.main_hand_item_use_duration";
	public static final String MAX_DURABILITY = "query.max_durability";
	public static final String MAX_HEALTH = "query.max_health";
	public static final String MOON_BRIGHTNESS = "query.moon_brightness";
	public static final String MOON_PHASE = "query.moon_phase";
	public static final String MOVEMENT_DIRECTION = "query.movement_direction";
	public static final String PLAYER_LEVEL = "query.player_level";
	public static final String REMAINING_DURABILITY = "query.remaining_durability";
	public static final String RIDER_BODY_X_ROTATION = "query.rider_body_x_rotation";
	public static final String RIDER_BODY_Y_ROTATION = "query.rider_body_y_rotation";
	public static final String RIDER_HEAD_X_ROTATION = "query.rider_head_x_rotation";
	public static final String RIDER_HEAD_Y_ROTATION = "query.rider_head_y_rotation";
	public static final String SCALE = "query.scale";
	public static final String SLEEP_ROTATION = "query.sleep_rotation";
	public static final String TIME_OF_DAY = "query.time_of_day";
	public static final String TIME_STAMP = "query.time_stamp";
	public static final String VERTICAL_SPEED = "query.vertical_speed";
	public static final String YAW_SPEED = "query.yaw_speed";

	private static final Map<String, Variable> VARIABLES = new ConcurrentHashMap<>();
	private static Actor<?> ACTOR = null;

	static {
		setDefaultQueryValues();
	}

	/**
	 * Returns whether a variable under the given identifier has already been registered, without creating a new instance
	 */
	public static boolean isExistingVariable(String name) {
		return VARIABLES.containsKey(name);
	}

	/**
	 * Register a new {@link Variable} with the math parsing system
	 * <p>
	 * Technically supports overriding by matching keys, though you should try to update the existing variable instances instead if possible
	 *
	 * @see MathParser#registerVariable(Variable)
	 */
	static void registerVariable(Variable variable) {
		VARIABLES.put(variable.name(), variable);
	}

	/**
	 * @return The registered {@link Variable} instance for the given name
	 *
	 * @see MathParser#getVariableFor(String)
	 */
	static Variable getVariableFor(String name) {
		return VARIABLES.computeIfAbsent(applyPrefixAliases(name, "query.", "q."), key -> new Variable(key, 0));
	}

	/**
	 * Parse a given string formatted with a prefix, swapping out any potential aliases for the defined proper name
	 *
	 * @param text The base text to parse
	 * @param properName The "correct" prefix to apply
	 * @param aliases The available prefixes to check and replace
	 * @return The unaliased string, or the original string if no aliases match
	 */
	private static String applyPrefixAliases(String text, String properName, String... aliases) {
		for (String alias : aliases) {
			if (text.startsWith(alias))
				return properName + text.substring(alias.length());
		}

		return text;
	}

	/**
	 * Update the currently rendering animatable. Should be called via {@link software.bernie.geckolib.model.GeoModel#applyMolangQueries(AnimationState, double) GeoModel.applyMolangQueries} when rendering
	 * @param animationState The AnimationState for the current render pass
	 * @param animTime The internal tick counter kept by the {@link AnimatableManager manager} for this animatable
	 */
	public static void updateActor(AnimationState<? extends GeoAnimatable> animationState, double animTime) {
		ACTOR = new Actor<>(animationState, animationState.getAnimatable(), animTime, class_310.method_1551(), class_310.method_1551().field_1687);
	}

	/**
	 * Container record holding animation frame information for the currently rendering animatable.
	 * <p>
	 * This is used by Molang queries to retrieve information for evaluation.
	 */
	public record Actor<T>(AnimationState<? extends GeoAnimatable> animationState, T animatable, double animTime, class_310 mc, class_1937 level) {}

	/**
	 * Set a variable value utilising the {@link #ACTOR} field, with convenient generic handling for ease of use
	 *
	 * @param name The variable name
	 * @param value The value supplier
	 * @param <T> The lowest-common type of object your actor needs to be in order to evaluate this variable
	 */
	public static <T> void setActorVariable(String name, ToDoubleFunction<Actor<T>> value) {
		getVariableFor(name).set(() -> value.applyAsDouble((Actor)getActor()));
	}

	private static Actor<?> getActor() {
		return ACTOR;
	}

	private static void setDefaultQueryValues() {
		getVariableFor("PI").set(Math.PI);
		getVariableFor("E").set(Math.E);
		setActorVariable(CARDINAL_PLAYER_FACING, actor -> actor.mc.field_1724.method_5735().ordinal());
		setActorVariable(DAY, actor -> actor.level.method_8510() / 24000d);
		setActorVariable(FRAME_ALPHA, actor -> actor.animationState().getPartialTick());
		setActorVariable(HAS_CAPE, actor -> actor.mc.field_1724.method_52814().comp_1627() != null ? 1 : 0);
		setActorVariable(IS_FIRST_PERSON, actor -> actor.mc.field_1690.method_31044() == class_5498.field_26664 ? 1 : 0);
		setActorVariable(LIFE_TIME, actor -> actor.animTime / 20d);
		setActorVariable(MOON_BRIGHTNESS, actor -> actor.level.method_30272());
		setActorVariable(MOON_PHASE, actor -> actor.level.method_30273());
		setActorVariable(PLAYER_LEVEL, actor -> actor.mc.field_1724.field_7520);
		setActorVariable(TIME_OF_DAY, actor -> actor.level.method_8532() / 24000f);
		setActorVariable(TIME_STAMP, actor -> actor.mc.field_1687.method_8510());

		setDefaultBlockEntityQueryValues();
		setDefaultEntityQueryValues();
		setDefaultLivingEntityQueryValues();
		setDefaultMobQueryValues();
		setDefaultItemQueryValues();
	}

	private static void setDefaultBlockEntityQueryValues() {
		MolangQueries.<class_2586>setActorVariable(BLOCK_STATE, actor -> actor.animatable.method_11010().method_26204().method_9595().method_11662().indexOf(actor.animatable.method_11010()));
	}

	private static void setDefaultEntityQueryValues() {
		MolangQueries.<class_1297>setActorVariable(BODY_X_ROTATION, actor -> actor.animatable instanceof class_1309 ? 0 : actor.animatable.method_5695(actor.animationState.getPartialTick()));
		MolangQueries.<class_1297>setActorVariable(BODY_Y_ROTATION, actor -> actor.animatable instanceof class_1309 living ? class_3532.method_16439(actor.animationState.getPartialTick(), living.field_6220, living.field_6283) : actor.animatable.method_5705(actor.animationState.getPartialTick()));
		MolangQueries.<class_1297>setActorVariable(CARDINAL_FACING, actor -> actor.animatable.method_5735().method_10146());
		MolangQueries.<class_1297>setActorVariable(CARDINAL_FACING_2D, actor -> {
			int directionId = actor.animatable.method_5735().method_10146();

			return directionId < 2 ? 6 : directionId;
		});
		MolangQueries.<class_1297>setActorVariable(DISTANCE_FROM_CAMERA, actor -> actor.mc.field_1773.method_19418().method_19326().method_1022(actor.animatable.method_19538()));
		MolangQueries.<class_1297>setActorVariable(GET_ACTOR_INFO_ID, actor -> actor.animatable.method_5628());
		MolangQueries.<class_1297>setActorVariable(HAS_COLLISION, actor -> !actor.animatable.field_5960 ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(HAS_GRAVITY, actor -> !actor.animatable.method_5740() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(HAS_OWNER, actor -> actor.animatable instanceof class_6025 ownable && ownable.method_6139() != null ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(HAS_PLAYER_RIDER, actor -> actor.animatable.method_5703(class_1657.class::isInstance) ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(HAS_RIDER, actor -> actor.animatable.method_5782() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_ALIVE, actor -> actor.animatable.method_5805() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_ANGRY, actor -> actor.animatable instanceof class_5354 neutralMob && neutralMob.method_29511() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_BREATHING, actor -> actor.animatable.method_5669() >= actor.animatable.method_5748() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_FIRE_IMMUNE, actor -> actor.animatable.method_5864().method_19946() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_INVISIBLE, actor -> actor.animatable.method_5767() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_IN_CONTACT_WITH_WATER, actor -> actor.animatable.method_5637() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_IN_LAVA, actor -> actor.animatable.method_5771() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_IN_WATER, actor -> actor.animatable.method_5799() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_IN_WATER_OR_RAIN, actor -> actor.animatable.method_5721() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_LEASHED, actor -> actor.animatable instanceof class_9817 leashable && leashable.method_60953() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_MOVING, actor -> actor.animationState.isMoving() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_ON_FIRE, actor -> actor.animatable.method_5809() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_ON_GROUND, actor -> actor.animatable.method_24828() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_POWERED, actor -> actor.animatable instanceof class_4582 powerable && powerable.method_6872() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_RIDING, actor -> actor.animatable.method_5765() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_SADDLED, actor -> actor.animatable instanceof class_5146 saddleable && saddleable.method_6725() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_SILENT, actor -> actor.animatable.method_5701() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_SNEAKING, actor -> actor.animatable.method_18276() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_SPRINTING, actor -> actor.animatable.method_5624() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(IS_SWIMMING, actor -> actor.animatable.method_5681() ? 1 : 0);
		MolangQueries.<class_1297>setActorVariable(MOVEMENT_DIRECTION, actor -> actor.animationState.isMoving() ? class_2350.method_58251(actor.animatable.method_18798()).method_10146() : 6);
		MolangQueries.<class_1297>setActorVariable(RIDER_BODY_X_ROTATION, actor -> actor.animatable.method_5782() ? actor.animatable.method_31483() instanceof class_1309 ? 0 : actor.animatable.method_31483().method_5695(actor.animationState.getPartialTick()) : 0);
		MolangQueries.<class_1297>setActorVariable(RIDER_BODY_Y_ROTATION, actor -> actor.animatable.method_5782() ? actor.animatable.method_31483() instanceof class_1309 living ? class_3532.method_16439(actor.animationState.getPartialTick(), living.field_6220, living.field_6283) : actor.animatable.method_31483().method_5705(actor.animationState.getPartialTick()) : 0);
		MolangQueries.<class_1297>setActorVariable(RIDER_HEAD_X_ROTATION, actor -> actor.animatable.method_31483() instanceof class_1309 living ? living.method_5695(actor.animationState.getPartialTick()) : 0);
		MolangQueries.<class_1297>setActorVariable(RIDER_HEAD_Y_ROTATION, actor -> actor.animatable.method_31483() instanceof class_1309 living ? living.method_5705(actor.animationState.getPartialTick()) : 0);
		MolangQueries.<class_1297>setActorVariable(VERTICAL_SPEED, actor -> actor.animatable.method_18798().field_1351);
		MolangQueries.<class_1297>setActorVariable(YAW_SPEED, actor -> actor.animatable.method_36454() - actor.animatable.field_5982);
	}

	private static void setDefaultLivingEntityQueryValues() {
		MolangQueries.<class_1309>setActorVariable(BLOCKING, actor -> actor.animatable.method_6039() ? 1 : 0);
		MolangQueries.<class_1309>setActorVariable(DEATH_TICKS, actor -> actor.animatable.field_6213 == 0 ? 0 : actor.animatable.field_6213 + actor.animationState.getPartialTick());
		MolangQueries.<class_1309>setActorVariable(EQUIPMENT_COUNT, actor -> Streams.stream(actor.animatable.method_5661()).filter(stack -> !stack.method_7960()).count());
		MolangQueries.<class_1309>setActorVariable(GROUND_SPEED, actor -> actor.animatable.method_18798().method_37267());
		MolangQueries.<class_1309>setActorVariable(HAS_HEAD_GEAR, actor -> !actor.animatable.method_6118(class_1304.field_6169).method_7960() ? 1 : 0);
		MolangQueries.<class_1309>setActorVariable(HEAD_X_ROTATION, actor -> actor.animatable.method_5695(actor.animationState.getPartialTick()));
		MolangQueries.<class_1309>setActorVariable(HEAD_Y_ROTATION, actor -> actor.animatable.method_5705(actor.animationState.getPartialTick()));
		MolangQueries.<class_1309>setActorVariable(HEALTH, actor -> actor.animatable.method_6032());
		MolangQueries.<class_1309>setActorVariable(HURT_TIME, actor -> actor.animatable.field_6235 == 0 ? 0 : actor.animatable.field_6235 - actor.animationState.getPartialTick());
		MolangQueries.<class_1309>setActorVariable(INVULNERABLE_TICKS, actor -> actor.animatable.field_6008 == 0 ? 0 : actor.animatable.field_6008 - actor.animationState.getPartialTick());
		MolangQueries.<class_1309>setActorVariable(IS_BABY, actor -> actor.animatable.method_6109() ? 1 : 0);
		MolangQueries.<class_1309>setActorVariable(IS_SLEEPING, actor -> actor.animatable.method_6113() ? 1 : 0);
		MolangQueries.<class_1309>setActorVariable(IS_USING_ITEM, actor -> actor.animatable.method_6115() ? 1 : 0);
		MolangQueries.<class_1309>setActorVariable(IS_WALL_CLIMBING, actor -> actor.animatable.method_6101() ? 1 : 0);
		MolangQueries.<class_1309>setActorVariable(MAIN_HAND_ITEM_MAX_DURATION, actor -> actor.animatable.method_6047().method_7935(actor.animatable));
		MolangQueries.<class_1309>setActorVariable(MAIN_HAND_ITEM_USE_DURATION, actor -> actor.animatable.method_6058() == class_1268.field_5808 ? actor.animatable.method_6048() / 20d + actor.animationState.getPartialTick() : 0);
		MolangQueries.<class_1309>setActorVariable(MAX_HEALTH, actor -> actor.animatable.method_6063());
		MolangQueries.<class_1309>setActorVariable(SCALE, actor -> actor.animatable.method_55693());
		MolangQueries.<class_1309>setActorVariable(SLEEP_ROTATION, actor -> Optional.ofNullable(actor.animatable.method_18401()).map(class_2350::method_10144).orElse(0f));
	}

	private static void setDefaultMobQueryValues() {
		MolangQueries.<class_1308>setActorVariable(CAN_CLIMB, actor -> !actor.animatable.method_5987() && actor.animatable.method_5942() instanceof class_1410 ? 1 : 0);
		MolangQueries.<class_1308>setActorVariable(CAN_FLY, actor -> !actor.animatable.method_5987() && actor.animatable.method_5942() instanceof class_1407 ? 1 : 0);
		MolangQueries.<class_1308>setActorVariable(CAN_SWIM, actor -> !actor.animatable.method_5987() && actor.animatable.method_5942() instanceof class_1412 || actor.animatable.method_5942() instanceof class_5766 ? 1 : 0);
		MolangQueries.<class_1308>setActorVariable(CAN_WALK, actor -> !actor.animatable.method_5987() && actor.animatable.method_5942() instanceof class_1409 || actor.animatable.method_5942() instanceof class_5766 ? 1 : 0);
	}

	private static void setDefaultItemQueryValues() {
		MolangQueries.<class_1792>setActorVariable(IS_ENCHANTED, actor -> actor.animationState.getData(DataTickets.ITEMSTACK).method_7942() ? 1 : 0);
		MolangQueries.<class_1792>setActorVariable(IS_STACKABLE, actor -> actor.animationState.getData(DataTickets.ITEMSTACK).method_7946() ? 1 : 0);
		MolangQueries.<class_1792>setActorVariable(ITEM_MAX_USE_DURATION, actor -> actor.animationState.getData(DataTickets.ITEMSTACK).method_7935(ClientUtil.getClientPlayer()));
		MolangQueries.<class_1792>setActorVariable(MAX_DURABILITY, actor -> actor.animationState.getData(DataTickets.ITEMSTACK).method_7936());
		MolangQueries.<class_1792>setActorVariable(REMAINING_DURABILITY, actor -> {
			class_1799 stack = actor.animationState.getData(DataTickets.ITEMSTACK);

			return stack.method_7963() ? stack.method_7936() - stack.method_7919() : 1;
		});
	}
}
