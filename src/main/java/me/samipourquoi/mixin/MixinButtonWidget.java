package me.samipourquoi.mixin;

import me.samipourquoi.gui.BookScreenRescaler;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ButtonWidget.class)
public class MixinButtonWidget extends AbstractButtonWidget {
	public MixinButtonWidget(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	@Override
	protected boolean clicked(double mouseX, double mouseY) {
		return super.clicked(
				BookScreenRescaler.mapMouseX((int) mouseX),
				BookScreenRescaler.mapMouseY((int) mouseY)
		);
	}
}
