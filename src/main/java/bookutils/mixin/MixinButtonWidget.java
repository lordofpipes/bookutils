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
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ButtonWidget.class)
public class MixinButtonWidget extends ClickableWidget {
    public MixinButtonWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return super.clicked(
            BookScreenRescaler.mapMouseX((int) mouseX),
            BookScreenRescaler.mapMouseY((int) mouseY)
        );
    }

    // prevent mojang from overriding this function by telling fabric it's just a normal function
    public void appendNarrations(NarrationMessageBuilder builder) { }
}
