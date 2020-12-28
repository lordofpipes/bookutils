package me.samipourquoi.mixin;

import me.samipourquoi.Settings;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public class MixinBookEditScreen {
	@Shadow private int currentPage;
	CompoundTag tag;

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
	private void openNextPageInject(CallbackInfo info) { this.updateCurrentPageNBT(); }

	@Inject(method = "openPreviousPage", at = @At("TAIL"))
	private void openPreviousPageInject(CallbackInfo info) { this.updateCurrentPageNBT(); }

	private void updateCurrentPageNBT() {
		if (Settings.BOOKMARKING) {
			this.tag.putInt("current_page", this.currentPage);
		}
	}
}
