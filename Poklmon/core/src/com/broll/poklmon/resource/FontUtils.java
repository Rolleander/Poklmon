package com.broll.poklmon.resource;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Logger;

public class FontUtils {

	private static Logger logger = new Logger(FontUtils.class.getName());
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
		try{
			update(font,text);
		}catch (Exception e){
			//sometimes glyphLayout.setText crashes with null pointer in line 162...
			logger.error("Exception in glyphlayout",e);
		}
		return (int) glyphLayout.width;
	}

	public int getHeight(BitmapFont font, String text) {
		return (int) font.getCapHeight();
	}

}
