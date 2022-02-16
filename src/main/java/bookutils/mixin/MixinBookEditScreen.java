// Copyright (c) 2022 lordpipe, samipourquoi, and BookUtils contributors
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.
//
// SPDX-FileCopyrightText: 2022 lordpipe
// SPDX-FileCopyrightText: 2020 samipourquoi
// SPDX-License-Identifier: LGPL-3.0-or-later

package bookutils.mixin;

import bookutils.ModConfig;
import bookutils.gui.BookScreenRescaler;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public class MixinBookEditScreen extends Screen {
    NbtCompound tag;

    @Shadow
    private int currentPage;

    @Shadow
    private ItemStack itemStack;

    @Shadow
    protected void writeNbtData(boolean signBook) {};

    protected MixinBookEditScreen(Text title) {
        super(title);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void createCustomTags(PlayerEntity playerEntity, ItemStack itemStack, Hand hand, CallbackInfo info) {
        if (AutoConfig.getConfigHolder(ModConfig.class).get().bookmarking) {
            if (itemStack.getNbt() == null) {
                itemStack.setNbt(new NbtCompound());
            }
            this.tag = itemStack.getNbt();
            if (!tag.contains("current_page")) {
                tag.putInt("current_page", 0);
            }
            this.currentPage = tag.getInt("current_page");
        }
    }

    @Inject(method = "openNextPage", at = @At("TAIL"))
    private void openNextPageInject(CallbackInfo info) {
        if (this.tag == null) return;
        this.updateCurrentPageNBT();
    }

    @Inject(method = "openPreviousPage", at = @At("TAIL"))
    private void openPreviousPageInject(CallbackInfo info) {
        if (this.tag == null) return;
        this.updateCurrentPageNBT();
    }

    private void updateCurrentPageNBT() {
        if (this.tag == null) return;
        if (AutoConfig.getConfigHolder(ModConfig.class).get().bookmarking) {
            this.tag.putInt("current_page", this.currentPage);
        }
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        if (!AutoConfig.getConfigHolder(ModConfig.class).get().smallBookGui)
            super.renderBackground(matrices);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void rescaleStart(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        BookScreenRescaler.rescaleStart(matrices);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void rescaleEnd(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        BookScreenRescaler.rescaleEnd(matrices);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void updateTranslateValues(CallbackInfo info) {
        BookScreenRescaler.updateTranslateValues(this.width, this.height);
    }
}
