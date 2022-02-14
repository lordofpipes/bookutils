package me.samipourquoi.gui;

import me.samipourquoi.Epiphany;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class EpiphanyOptionsWidget extends ButtonWidget {
	public EpiphanyOptionsWidget(int x, int y, ButtonWidget.PressAction action) {
		super(x, y, 20, 20, new TranslatableText("narrator.button.epi_options"), action);
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(Epiphany.EPI_WIDGETS);
		EpiphanyOptionsWidget.IconLocation iconLocation = this.isHovered() ?
				IconLocation.OPTIONS_HOVERED :
				IconLocation.OPTIONS;
		this.drawTexture(matrices, this.x, this.y, iconLocation.u, iconLocation.v, this.width, this.height);
	}

	@Environment(EnvType.CLIENT)
	protected enum IconLocation {
		OPTIONS(0, 0),
		OPTIONS_HOVERED(20, 0);

		final int u;
		final int v;

		IconLocation(int j, int k) {
			this.u = j;
			this.v = k;
		}
	}
}
