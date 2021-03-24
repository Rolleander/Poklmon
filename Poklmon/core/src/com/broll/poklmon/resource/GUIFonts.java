package com.broll.poklmon.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class GUIFonts {
	public static BitmapFont dialogText, hudText, titleText, smallText, tinyText;
	private static String fontPath = "resource/fonts/";
	private static Array<BitmapFont> fonts=new Array<BitmapFont>();

	public static void loadFonts() {
		dialogText = loadFont("dialogFont.fnt");
		hudText = loadFont("hudFont.fnt");
		smallText = loadFont("smallFont.fnt");
		titleText = loadFont("titleFont.fnt");
		tinyText = loadFont("tinyFont.fnt");
	}

	public static void dispose(){
		for(BitmapFont font: fonts){
			font.dispose();
		}
	}

	private static BitmapFont loadFont(String name) {
		BitmapFont font = new BitmapFont(Gdx.files.internal(ResourceUtils.DATA_PATH + fontPath + name),true);
		fonts.add(font);
		return font;
	}
}
