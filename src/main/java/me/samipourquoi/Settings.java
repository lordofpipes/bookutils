package me.samipourquoi;

import net.minecraft.client.options.BooleanOption;

import java.io.PrintWriter;

public class Settings {
	public static boolean SMALL_BOOK = false;

	public static boolean BOOKMARKING = false;

	public static void loadOptions(String key, String value) {
		if (key.equals("small_book")) {
			Settings.SMALL_BOOK = value.equals("true");
		} else if (key.equals("bookmarking")) {
			Settings.BOOKMARKING = value.equals("true");
		}
	}

	public static void writeOptions(PrintWriter printWriter) {
		printWriter.println("small_book:" + SMALL_BOOK);
		printWriter.println("bookmarking:" + BOOKMARKING);
	}

	public static class OPTIONS {
		public static BooleanOption SMALL_BOOK = new BooleanOption(
			"options.small_book",
			gameOptions -> Settings.SMALL_BOOK,
			(gameOptions, enableSmallBook) -> Settings.SMALL_BOOK = enableSmallBook);

		public static BooleanOption BOOKMARKING = new BooleanOption(
				"options.bookmarking",
				gameOptions -> Settings.BOOKMARKING,
				((gameOptions, enableBookmarking) -> Settings.BOOKMARKING = enableBookmarking));
	}
}
