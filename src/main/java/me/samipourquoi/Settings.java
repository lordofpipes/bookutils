package me.samipourquoi;

import net.minecraft.client.options.BooleanOption;

import java.io.PrintWriter;

public class Settings {
	public static boolean SMALL_BOOK = false;
	public static final float SCALING_FACTOR = 0.5f;
	public static int TRANSLATE_X = 0;
	public static int TRANSLATE_Y = 0;

	public static void loadOptions(String key, String value) {
		if (key.equals("small_book")) {
			Settings.SMALL_BOOK = value.equals("true");
		}
	}

	public static void writeOptions(PrintWriter printWriter) {
		printWriter.println("small_book:" + SMALL_BOOK);
	}

	public static class OPTIONS {
		public static BooleanOption SMALL_BOOK = new BooleanOption(
			"options.small_book",
			gameOptions -> Settings.SMALL_BOOK,
			(gameOptions, enableSmallBook) -> Settings.SMALL_BOOK = enableSmallBook);
	}
}
