package me.samipourquoi.gui;

import me.samipourquoi.Settings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class EpiphanyOptionsMenu extends Screen {
	protected ButtonListWidget list;

	public EpiphanyOptionsMenu() {
		super(new TranslatableText("menu.epi_options"));
	}

	@Override
	protected void init() {
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		this.list.addAll(new Option[]{ Settings.OPTIONS.SMALL_BOOK });
		this.children.add(this.list);

		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, button -> {
			this.client.openScreen(null);
		}));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.list.render(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 16777215);
		super.render(matrices, mouseX, mouseY, delta);
	}
}
