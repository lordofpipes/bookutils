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

import bookutils.gui.BookScreenRescaler;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
public class MixinGameRender {
    @ModifyVariable(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;viewport(IIII)V"
        ),
        index = 5
    )
    private int mapMouseX(int mouseX) {
        return BookScreenRescaler.mapMouseX(mouseX);
    }

    @ModifyVariable(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;viewport(IIII)V"
        ),
        index = 6
    )
    private int mapMouseY(int mouseY) {
        return BookScreenRescaler.mapMouseY(mouseY);
    }
}
