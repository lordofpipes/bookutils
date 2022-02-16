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

package bookutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;

public class BookUtils implements ClientModInitializer {
    public static final String MODID = "bookutils";
    public static final Logger LOGGER = LoggerFactory.getLogger("bookutils");

    @Override
    public void onInitializeClient() {
        registerConfig();
        registerKeybinds(AutoConfig.getConfigHolder(ModConfig.class).getConfig());
        LOGGER.info("Loaded BookUtils");
    }

    private void registerKeybinds(ModConfig config) {
        KeyBinding toggleSmallKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.bookutils.toggleSmallGui",
            InputUtil.Type.KEYSYM,
            -1,
            "category.bookutils"
        ));
        KeyBinding toggleBookmarkingKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.bookutils.toggleBookmarking",
            InputUtil.Type.KEYSYM,
            -1,
            "category.bookutils"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleSmallKeybind.wasPressed()) {
                config.smallBookGui = !config.smallBookGui;
                AutoConfig.getConfigHolder(ModConfig.class).save();
                client.player.sendMessage(
                    new TranslatableText(config.smallBookGui ? "message.bookutils.enabledSmallGui" : "message.bookutils.disabledSmallGui"), false);
            }
            while (toggleBookmarkingKeybind.wasPressed()) {
                config.bookmarking = !config.bookmarking;
                AutoConfig.getConfigHolder(ModConfig.class).save();
                client.player.sendMessage(
                    new TranslatableText(config.smallBookGui ? "message.bookutils.enabledBookmarking" : "message.bookutils.disabledBookmarking"), false);
            }
        });
    }

    private void registerConfig() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
    }
}
