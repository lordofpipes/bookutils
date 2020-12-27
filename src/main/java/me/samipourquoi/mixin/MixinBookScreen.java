package me.samipourquoi.mixin;

import me.samipourquoi.Settings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookScreen.class)
public abstract class MixinBookScreen extends Screen {
	protected MixinBookScreen(Text title) {
		super(title);
	}

	@Override
	public void renderBackground(MatrixStack matrices) {
		if (!Settings.SMALL_BOOK) super.renderBackground(matrices);
	}

	@Inject(
			method = "render",
			at = @At("HEAD"))
	private void rescaleStart(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		matrices.push();
		if (Settings.SMALL_BOOK) {
			matrices.scale(0.5f, 0.5f, 1);
			matrices.translate(Settings.TRANSLATE_X, Settings.TRANSLATE_Y, 0);
		}
	}

	@Inject(
			method = "render",
			at = @At(
					value = "TAIL"
			))
	private void rescaleEnd(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		matrices.pop();
	}

	@Inject(
			method = "init",
			at = @At("HEAD"))
	private void updateTranslateValues(CallbackInfo info) {
		Settings.TRANSLATE_X = this.width + 75;
		Settings.TRANSLATE_Y = this.height + 10;
	}
}
