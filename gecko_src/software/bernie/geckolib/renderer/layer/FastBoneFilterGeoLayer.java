package software.bernie.geckolib.renderer.layer;

import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.class_1921;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;

/**
 * A more efficient version of {@link BoneFilterGeoLayer}
 * <p>
 * This version requires you provide the list of bones to filter up-front, so that the bone hierarchy doesn't need to be traversed
 */
public class FastBoneFilterGeoLayer<T extends GeoAnimatable> extends BoneFilterGeoLayer<T> {
	protected final Supplier<List<String>> boneSupplier;

	public FastBoneFilterGeoLayer(GeoRenderer<T> renderer) {
		this(renderer, List::of);
	}

	public FastBoneFilterGeoLayer(GeoRenderer<T> renderer, Supplier<List<String>> boneSupplier) {
		this(renderer, boneSupplier, (bone, animatable, partialTick) -> {});
	}

	public FastBoneFilterGeoLayer(GeoRenderer<T> renderer, Supplier<List<String>> boneSupplier, TriConsumer<GeoBone, T, Float> checkAndApply) {
		super(renderer, checkAndApply);

		this.boneSupplier = boneSupplier;
	}

	/**
	 * Return a list of bone names to grab to then be filtered
	 * <p>
	 * This is even more efficient if you use a cached list.
	 */
	protected List<String> getAffectedBones() {
		return boneSupplier.get();
	};

	@Override
	public void preRender(class_4587 poseStack, T animatable, BakedGeoModel bakedModel, @Nullable class_1921 renderType, class_4597 bufferSource,
						  @Nullable class_4588 buffer, float partialTick, int packedLight, int packedOverlay) {
		for (String boneName : getAffectedBones()) {
			this.renderer.getGeoModel().getBone(boneName).ifPresent(bone -> checkAndApply(bone, animatable, partialTick));
		}
	}
}
