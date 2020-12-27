package me.samipourquoi.mixin;

import me.samipourquoi.gui.EpiphanyOptionsMenu;
import me.samipourquoi.gui.EpiphanyOptionsWidget;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
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
		EpiphanyOptionsWidget settingsButton = new EpiphanyOptionsWidget(
				this.width / 2 + 106,
				this.height / 4 + 104,
				buttonWidget -> {
					this.client.openScreen(new EpiphanyOptionsMenu());
				}
		);

		this.addButton(settingsButton);
	}
}
