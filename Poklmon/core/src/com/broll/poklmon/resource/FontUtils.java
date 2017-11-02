package com.broll.poklmon.resource;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class FontUtils {

	private  GlyphLayout glyphLayout;
	private String currentString=null;


	public FontUtils(){

	}

	private void update(BitmapFont font, String text){
		if(glyphLayout==null){
			glyphLayout=new GlyphLayout(font,text);
			return;
		}
		if(!text.equals(currentString)){
			currentString=text;
			glyphLayout.setText(font,currentString);
		}
	}

	public  int getWidth(BitmapFont font, String text) {
		if(text==null){
			return 0;
		}
		update(font,text);
		return (int) glyphLayout.width;
	}

	public int getHeight(BitmapFont font, String text) {
		if(text==null){
			return 0;
		}
		update(font,text);
		return (int) glyphLayout.height;
	}

}
