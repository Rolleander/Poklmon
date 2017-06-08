package com.broll.poklmon.resource;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class FontUtils {

	
	public static int getWidth(BitmapFont font, String text) {
		GlyphLayout gl=new GlyphLayout(font,text);
		return (int) gl.width;
	}

	public static int getHeight(BitmapFont font, String text) {
		GlyphLayout gl=new GlyphLayout(font,text);
		return (int) gl.height;
	}

}
