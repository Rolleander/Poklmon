package com.broll.poklmon.data.basics;

import com.badlogic.gdx.graphics.Color;

public class ColorUtil {
	public static Color newColor(int r, int g, int b){
		return new Color((float)r/255f, (float)g/255f, (float)b/255f, 1);
	}

	public static Color newColor(int r,int g, int b, int a) {
		return new Color((float)r/255f, (float)g/255f, (float)b/255f, (float)a/255f);
	}

	public static Color newColor(float r, float g, float b, float a) {
		return new Color(r,g,b,a);
	}
}
