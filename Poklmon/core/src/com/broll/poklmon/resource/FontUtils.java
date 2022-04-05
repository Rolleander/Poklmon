package com.broll.poklmon.resource;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public final class FontUtils {


    private FontUtils() {

    }

    public static  int getWidth(BitmapFont font, String text) {
        float width =0 ;
        float padRight = font.getData().padRight;
        float padLeft = font.getData().padLeft;
        for(int i=0; i<text.length(); i++){
            BitmapFont.Glyph glyph = font.getData().getGlyph(text.charAt(i));
            if(glyph!=null){
             width += (glyph.width + glyph.xoffset) * font.getScaleX() - padRight + padLeft;
            }
        }
        return (int) width;
    }

    public static int getHeight(BitmapFont font, String text) {
        return (int) font.getCapHeight();
    }

}
