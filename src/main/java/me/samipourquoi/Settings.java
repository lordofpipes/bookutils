package me.samipourquoi;

import net.minecraft.client.options.BooleanOption;

public class Settings {
	public static boolean SMALL_BOOK = false;

	public static class OPTIONS {
		public static BooleanOption SMALL_BOOK = new BooleanOption(
			"options.small_book",
			gameOptions -> Settings.SMALL_BOOK,
			(gameOptions, enableSmallBook) -> Settings.SMALL_BOOK = enableSmallBook);
	}
}
