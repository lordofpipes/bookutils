package me.samipourquoi.mixin;

import me.samipourquoi.Settings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ButtonWidget.class)
public class MixinButtonWidget extends AbstractButtonWidget{
	public MixinButtonWidget(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	@Override
	protected boolean clicked(double mouseX, double mouseY) {
		if (MinecraftClient.getInstance().currentScreen instanceof BookScreen && Settings.SMALL_BOOK) {
			return super.clicked(mouseX / Settings.SCALING_FACTOR - Settings.TRANSLATE_X,
					mouseY / Settings.SCALING_FACTOR - Settings.TRANSLATE_Y);
		}

		return super.clicked(mouseX, mouseY);
	}
}
