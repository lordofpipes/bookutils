// Copyright (c) 2022 lordpipe, samipourquoi, and BookUtils contributors
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.
//
// SPDX-FileCopyrightText: 2022 lordpipe
// SPDX-FileCopyrightText: 2020 samipourquoi
// SPDX-License-Identifier: GPL-3.0-or-later

package bookutils.gui;

import bookutils.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.util.math.MatrixStack;

public class BookScreenRescaler {
    public static final float SCALING_FACTOR = 0.5f;
    public static int TRANSLATE_X = 0;
    public static int TRANSLATE_Y = 0;

    public static boolean isInSmallBookMode() {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        return (currentScreen instanceof BookScreen
                || currentScreen instanceof BookEditScreen)
                && AutoConfig.getConfigHolder(ModConfig.class).get().smallBookGui;
    }

    public static void rescaleStart(MatrixStack matrices) {
        matrices.push();
        if (AutoConfig.getConfigHolder(ModConfig.class).get().smallBookGui) {
            matrices.scale(0.5f, 0.5f, 1);
            matrices.translate(TRANSLATE_X, TRANSLATE_Y, 0);
        }
    }

    public static void rescaleEnd(MatrixStack matrices) {
        matrices.pop();
    }

    public static void updateTranslateValues(int width, int height) {
        TRANSLATE_X = width + 75;
        TRANSLATE_Y = height + 10;
    }

    public static int mapMouseX(int mouseX) {
        return isInSmallBookMode() ?
                (int) (mouseX / SCALING_FACTOR) - TRANSLATE_X :
                mouseX;
    }

    public static int mapMouseY(int mouseY) {
        return isInSmallBookMode() ?
                (int) (mouseY / SCALING_FACTOR) - TRANSLATE_Y :
                mouseY;
    }
}
