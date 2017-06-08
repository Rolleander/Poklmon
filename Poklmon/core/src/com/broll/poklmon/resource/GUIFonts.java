package com.broll.poklmon.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GUIFonts {
	public static BitmapFont dialogText, hudText, titleText, smallText, tinyText;
	private static String fontPath = "resource/fonts/";

	public static void loadFonts() {
		dialogText = loadFont("dialogFont.fnt");
		hudText = loadFont("hudFont.fnt");
		smallText = loadFont("smallFont.fnt");
		titleText = loadFont("titleFont.fnt");
		tinyText = loadFont("tinyFont.fnt");
	}

	private static BitmapFont loadFont(String name) {
		BitmapFont font = new BitmapFont(Gdx.files.internal(ResourceUtils.DATA_PATH + fontPath + name),true);
		return font;
	}
}
