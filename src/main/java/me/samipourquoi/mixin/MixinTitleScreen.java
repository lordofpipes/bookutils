package me.samipourquoi.mixin;

import me.samipourquoi.gui.EpiphanyOptionsMenu;
import me.samipourquoi.gui.EpiphanyOptionsWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen extends Screen {
	protected MixinTitleScreen(Text title) {
		super(title);
	}

	@Inject(
			method = "init",
			at = @At("TAIL")
	)
	private void addEpiphanyButton(CallbackInfo info) {
		EpiphanyOptionsWidget settingsButton = new EpiphanyOptionsWidget(
				this.width / 2 + 104,
				this.height / 4 + 96,
				buttonWidget -> this.client.openScreen(new EpiphanyOptionsMenu())
		);

		this.addButton(settingsButton);
	}
}
