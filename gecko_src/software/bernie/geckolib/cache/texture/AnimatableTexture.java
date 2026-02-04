package software.bernie.geckolib.cache.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.util.RenderUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_1011;
import net.minecraft.class_1044;
import net.minecraft.class_1049;
import net.minecraft.class_1079;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_3532;
import net.minecraft.class_4573;
import net.minecraft.class_7771;

/**
 * Wrapper for {@link class_1049 SimpleTexture} implementation allowing for casual use of animated non-atlas textures
 */
public class AnimatableTexture extends class_1049 {
	private AnimationContents animationContents = null;
	private boolean isAnimated = false;

	public AnimatableTexture(final class_2960 location) {
		super(location);
	}

	@Override
	public void method_4625(class_3300 manager) throws IOException {
		class_3298 resource = manager.getResourceOrThrow(this.field_5224);

		try {
			class_1011 nativeImage;

			try (InputStream inputstream = resource.method_14482()) {
				nativeImage = class_1011.method_4309(inputstream);
			}

			this.animationContents = resource.method_14481().method_43041(class_1079.field_5337).map(animMeta -> new AnimationContents(nativeImage, animMeta)).orElse(null);

			if (this.animationContents != null) {
				if (!this.animationContents.isValid()) {
					nativeImage.close();

					return;
				}

				this.isAnimated = true;

				onRenderThread(() -> {
					TextureUtil.prepareImage(method_4624(), 0, this.animationContents.frameSize.comp_1049(), this.animationContents.frameSize.comp_1050());
					nativeImage.method_4312(0, 0, 0, 0, 0, this.animationContents.frameSize.comp_1049(), this.animationContents.frameSize.comp_1050(), false, false);
				});
			}
		}
		catch (RuntimeException exception) {
			GeckoLibConstants.LOGGER.warn("Failed reading metadata of: {}", this.field_5224, exception);
		}
	}

	/**
	 * Returns whether the texture found any valid animation metadata when loading.
	 * <p>
	 * If false, then this is no different to a standard {@link class_1049}
	 */
	public boolean isAnimated() {
		return this.isAnimated;
	}

	public static void setAndUpdate(class_2960 texturePath) {
		setAndUpdate(texturePath, (int) RenderUtil.getCurrentTick());
	}

	/**
	 * Setting a specific frame for the animated texture does not work well because of how Minecraft buffers rendering passes
	 * <p>
	 * Use the non-specified method above unless you know what you're doing
	 */
	public static void setAndUpdate(class_2960 texturePath, int frameTick) {
		class_1044 texture = class_310.method_1551().method_1531().method_4619(texturePath);

		if (texture instanceof AnimatableTexture animatableTexture)
			animatableTexture.setAnimationFrame(frameTick);

		RenderSystem.setShaderTexture(0, texture.method_4624());
	}

	public void setAnimationFrame(int tick) {
		if (this.animationContents != null)
			this.animationContents.animatedTexture.setCurrentFrame(tick);
	}

	private static void onRenderThread(class_4573 renderCall) {
		if (!RenderSystem.isOnRenderThread()) {
			RenderSystem.recordRenderCall(renderCall);
		}
		else {
			renderCall.execute();
		}
	}

	private class AnimationContents {
		private final class_7771 frameSize;
		private final Texture animatedTexture;

		private AnimationContents(class_1011 image, class_1079 animMeta) {
			this.frameSize = animMeta.method_24143(image.method_4307(), image.method_4323());
			this.animatedTexture = generateAnimatedTexture(image, animMeta);
		}

		private boolean isValid() {
			return this.animatedTexture != null;
		}

		private Texture generateAnimatedTexture(class_1011 image, class_1079 animMeta) {
			if (!class_3532.method_48117(image.method_4307(), this.frameSize.comp_1049()) || !class_3532.method_48117(image.method_4323(), this.frameSize.comp_1050())) {
				GeckoLibConstants.LOGGER.error("Image {} size {},{} is not multiple of frame size {},{}", AnimatableTexture.this.field_5224, image.method_4307(), image.method_4323(), this.frameSize.comp_1049(), this.frameSize.comp_1050());

				return null;
			}

			int columns = image.method_4307() / this.frameSize.comp_1049();
			int rows = image.method_4323() / this.frameSize.comp_1050();
			int frameCount = columns * rows;
			List<Frame> frames = new ObjectArrayList<>();

			animMeta.method_33460((frame, frameTime) -> frames.add(new Frame(frame, frameTime)));

			if (frames.isEmpty()) {
				for (int frame = 0; frame < frameCount; ++frame) {
					frames.add(new Frame(frame, animMeta.method_4684()));
				}
			}
			else {
				int index = 0;
				IntSet unusedFrames = new IntOpenHashSet();

				for (Frame frame : frames) {
					if (frame.time <= 0) {
						GeckoLibConstants.LOGGER.warn("Invalid frame duration on sprite {} frame {}: {}", AnimatableTexture.this.field_5224, index, frame.time);
						unusedFrames.add(frame.index);
					}
					else if (frame.index < 0 || frame.index >= frameCount) {
						GeckoLibConstants.LOGGER.warn("Invalid frame index on sprite {} frame {}: {}", AnimatableTexture.this.field_5224, index, frame.index);
						unusedFrames.add(frame.index);
					}

					index++;
				}

				if (!unusedFrames.isEmpty())
					GeckoLibConstants.LOGGER.warn("Unused frames in sprite {}: {}", AnimatableTexture.this.field_5224, Arrays.toString(unusedFrames.toArray()));
			}

			return frames.size() <= 1 ? null : new Texture(image, frames.toArray(new Frame[0]), columns, animMeta.method_4685());
		}

		private record Frame(int index, int time) {}

		private class Texture implements AutoCloseable {
			private final class_1011 baseImage;
			private final Frame[] frames;
			private final int framePanelSize;
			private final boolean interpolating;
			private final class_1011 interpolatedFrame;
			private final int totalFrameTime;

			private int currentFrame;
			private int currentSubframe;

			private Texture(class_1011 baseImage, Frame[] frames, int framePanelSize, boolean interpolating) {
				this.baseImage = baseImage;
				this.frames = frames;
				this.framePanelSize = framePanelSize;
				this.interpolating = interpolating;
				this.interpolatedFrame = interpolating ? new class_1011(AnimationContents.this.frameSize.comp_1049(), AnimationContents.this.frameSize.comp_1050(), false) : null;
				int time = 0;

				for (Frame frame : this.frames) {
					time += frame.time;
				}

				this.totalFrameTime = time;
			}

			private int getFrameX(int frameIndex) {
				return frameIndex % this.framePanelSize;
			}

			private int getFrameY(int frameIndex) {
				return frameIndex / this.framePanelSize;
			}

			public void setCurrentFrame(int ticks) {
				ticks %= this.totalFrameTime;

				if (ticks == this.currentSubframe)
					return;

				int lastSubframe = this.currentSubframe;
				int lastFrame = this.currentFrame;
				int time = 0;

				for (Frame frame : this.frames) {
					time += frame.time;

					if (ticks < time) {
						this.currentFrame = frame.index;
						this.currentSubframe = ticks % frame.time;

						break;
					}
				}

				if (this.currentFrame != lastFrame && this.currentSubframe == 0) {
					onRenderThread(() -> {
						TextureUtil.prepareImage(AnimatableTexture.this.method_4624(), 0, AnimationContents.this.frameSize.comp_1049(), AnimationContents.this.frameSize.comp_1050());
						this.baseImage.method_4312(0, 0, 0, getFrameX(this.currentFrame) * AnimationContents.this.frameSize.comp_1049(), getFrameY(this.currentFrame) * AnimationContents.this.frameSize.comp_1050(), AnimationContents.this.frameSize.comp_1049(), AnimationContents.this.frameSize.comp_1050(), false, false);
					});
				}
				else if (this.currentSubframe != lastSubframe && this.interpolating) {
					onRenderThread(this::generateInterpolatedFrame);
				}
			}

			private void generateInterpolatedFrame() {
				Frame frame = this.frames[this.currentFrame];
				double frameProgress = 1 - (double)this.currentSubframe / (double)frame.time;
				int nextFrameIndex = this.frames[(this.currentFrame + 1) % this.frames.length].index;

				if (frame.index != nextFrameIndex) {
					for (int y = 0; y < this.interpolatedFrame.method_4323(); ++y) {
						for (int x = 0; x < this.interpolatedFrame.method_4307(); ++x) {
							int prevFramePixel = getPixel(frame.index, x, y);
							int nextFramePixel = getPixel(nextFrameIndex, x, y);
							int blendedRed = interpolate(frameProgress, prevFramePixel >> 16 & 255, nextFramePixel >> 16 & 255);
							int blendedGreen = interpolate(frameProgress, prevFramePixel >> 8 & 255, nextFramePixel >> 8 & 255);
							int blendedBlue = interpolate(frameProgress, prevFramePixel & 255, nextFramePixel & 255);

							this.interpolatedFrame.method_4305(x, y, prevFramePixel & -16777216 | blendedRed << 16 | blendedGreen << 8 | blendedBlue);
						}
					}

					TextureUtil.prepareImage(AnimatableTexture.this.method_4624(), 0, AnimationContents.this.frameSize.comp_1049(), AnimationContents.this.frameSize.comp_1050());
					this.interpolatedFrame.method_4312(0, 0, 0, 0, 0, AnimationContents.this.frameSize.comp_1049(), AnimationContents.this.frameSize.comp_1050(), false, false);
				}
			}

			private int getPixel(int frameIndex, int x, int y) {
				return this.baseImage.method_4315(x + getFrameX(frameIndex) * AnimationContents.this.frameSize.comp_1049(), y + getFrameY(frameIndex) * AnimationContents.this.frameSize.comp_1050());
			}

			private int interpolate(double frameProgress, double prevColour, double nextColour) {
				return (int)(frameProgress * prevColour + (1 - frameProgress) * nextColour);
			}

			@Override
			public void close() {
				this.baseImage.close();

				if (this.interpolatedFrame != null)
					this.interpolatedFrame.close();
			}
		}
	}
}
