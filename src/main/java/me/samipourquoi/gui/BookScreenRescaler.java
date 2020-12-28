package me.samipourquoi.gui;

import me.samipourquoi.Settings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

public class BookScreenRescaler {
	public static final float SCALING_FACTOR = 0.5f;
	public static int TRANSLATE_X = 0;
	public static int TRANSLATE_Y = 0;

	public static boolean isInSmallBookMode() {
		Screen currentScreen = MinecraftClient.getInstance().currentScreen;
		return (currentScreen instanceof BookScreen
				|| currentScreen instanceof BookEditScreen)
				&& Settings.SMALL_BOOK;
	}

	public static void rescaleStart(MatrixStack matrices) {
		matrices.push();
		if (Settings.SMALL_BOOK) {
			matrices.scale(0.5f, 0.5f, 1);
			matrices.translate(TRANSLATE_X, TRANSLATE_Y, 0);
		}
	}

	public static void rescaleEnd(MatrixStack matrices) {
		matrices.pop();
	}

	public static void updateTranslateValues(int width, int height) {
		TRANSLATE_X = width + 75;
		TRANSLATE_Y = height + 10;
	}

	public static int mapMouseX(int mouseX) {
		return isInSmallBookMode() ?
				(int)(mouseX / SCALING_FACTOR) - TRANSLATE_X :
				mouseX;
	}

	public static int mapMouseY(int mouseY) {
		return isInSmallBookMode() ?
				(int) (mouseY / SCALING_FACTOR) - TRANSLATE_Y :
				mouseY;
	}
}
