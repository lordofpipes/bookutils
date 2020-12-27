package me.samipourquoi.mixin;

import me.samipourquoi.Settings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Locale;
import java.util.Set;

@Mixin(GameRenderer.class)
public class MixinGameRender {
	@ModifyVariable(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;viewport(IIII)V"
			),
			index = 5
	)
	private int mapMouseX(int mouseX) {
		return MinecraftClient.getInstance().currentScreen instanceof BookScreen && Settings.SMALL_BOOK ?
				(int)(mouseX / Settings.SCALING_FACTOR) - Settings.TRANSLATE_X :
				mouseX;
	}

	@ModifyVariable(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;viewport(IIII)V"
			),
			index = 6
	)
	private int mapMouseY(int mouseY) {
		return MinecraftClient.getInstance().currentScreen instanceof BookScreen && Settings.SMALL_BOOK ?
				(int)(mouseY / Settings.SCALING_FACTOR) - Settings.TRANSLATE_Y :
				mouseY;
	}
}
