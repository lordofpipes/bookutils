package me.samipourquoi.mixin;

import me.samipourquoi.Settings;
import me.samipourquoi.gui.BookScreenRescaler;
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

	@Inject(method = "render",
			at = @At("HEAD"))
	private void rescaleStart(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		BookScreenRescaler.rescaleStart(matrices);
	}

	@Inject(method = "render",
			at = @At("TAIL"))
	private void rescaleEnd(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		BookScreenRescaler.rescaleEnd(matrices);
	}

	@Inject(method = "init",
			at = @At("HEAD"))
	private void updateTranslateValues(CallbackInfo info) {
		BookScreenRescaler.updateTranslateValues(this.width, this.height);
	}
}
