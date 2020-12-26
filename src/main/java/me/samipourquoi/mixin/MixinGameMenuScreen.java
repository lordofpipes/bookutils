package me.samipourquoi.mixin;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class MixinGameMenuScreen extends Screen {
	protected MixinGameMenuScreen(Text title) {
		super(title);
	}

	@Inject(method = "initWidgets", at = @At("HEAD"))
	private void addToggleButton(CallbackInfo ci) {
		ButtonWidget buttonWidget2 = this.addButton(
				new ButtonWidget(this.width / 2 - 102,
						this.height / 4 + 120 + -16,
						204,
						20,
						new TranslatableText("menu.returnToMenu"),
						(buttonWidgetx) -> { });
	}
}
