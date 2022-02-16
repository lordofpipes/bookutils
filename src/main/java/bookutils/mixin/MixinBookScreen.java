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
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookScreen.class)
public abstract class MixinBookScreen extends Screen {
    protected MixinBookScreen(Text title) {
        super(title);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        if (!AutoConfig.getConfigHolder(ModConfig.class).get().smallBookGui) super.renderBackground(matrices);
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
