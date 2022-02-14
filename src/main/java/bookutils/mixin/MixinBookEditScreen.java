package me.samipourquoi.mixin;

import me.samipourquoi.Settings;
import me.samipourquoi.gui.BookScreenRescaler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public class MixinBookEditScreen extends Screen {
	CompoundTag tag;
	@Shadow
	private int currentPage;

	protected MixinBookEditScreen(Text title) {
		super(title);
	}

	@Inject(method = "<init>",
			at = @At("RETURN"))
	private void createCustomTags(PlayerEntity playerEntity, ItemStack itemStack, Hand hand, CallbackInfo info) {
		if (Settings.BOOKMARKING) {
			this.tag = itemStack.getTag();
			if (!tag.contains("current_page")) {
				tag.putInt("current_page", 0);
			}
			this.currentPage = tag.getInt("current_page");
		}
	}

	@Inject(method = "openNextPage", at = @At("TAIL"))
	private void openNextPageInject(CallbackInfo info) {
		this.updateCurrentPageNBT();
	}

	@Inject(method = "openPreviousPage", at = @At("TAIL"))
	private void openPreviousPageInject(CallbackInfo info) {
		this.updateCurrentPageNBT();
	}

	private void updateCurrentPageNBT() {
		if (Settings.BOOKMARKING) {
			this.tag.putInt("current_page", this.currentPage);
		}
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
