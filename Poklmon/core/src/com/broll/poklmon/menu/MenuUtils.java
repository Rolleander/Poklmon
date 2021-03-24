package com.broll.poklmon.menu;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.dialog.MessageLineCutter;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;

public class MenuUtils {

	private static DataContainer data;

	public static void init(DataContainer d) {
		data = d;
	}

	public static void drawFancyString(Graphics g, String text, float x, float y) {
		drawFancyString(g, text, x, y, ColorUtil.newColor(50, 50, 50), g.getColor());
	}

	public static void drawFancyString(Graphics g, String text, float x, float y, Color shadow, Color color) {
		if (text == null) {
			return;
		}
		g.setColor(shadow);
		g.drawString(text, x + 1, y + 1);
		g.setColor(color);
		g.drawString(text, x, y);
	}

	public static int getTextWidth(Graphics g,FontUtils fontUtils, String text) {
		if (text == null) {
			return 0;
		}
		return fontUtils.getWidth(g.getFont(), text);
	}

	public static void drawBoxString(Graphics g, String text,FontUtils fontUtils, float x, float y, int width, int yplus) {
		String[] lines = MessageLineCutter.cutMessage(text, fontUtils,GUIFonts.dialogText, width, 15);
		for (String l : lines) {
			g.drawString(l, x, y);
			y += g.getFont().getLineHeight() + yplus;
		}
	}

	public static int drawButton(Graphics g, String text,FontUtils fontUtils, float x, float y, boolean selected) {
		g.setFont(GUIFonts.hudText);
		int w = getTextWidth(g,fontUtils, text) + 20;
		int h = 40;
		Image button;
		if (selected) {
			button = data.getGraphics().getMenuGraphicsContainer().getDesignButton().getSprite(0, 1);
		} else {
			button = data.getGraphics().getMenuGraphicsContainer().getDesignButton().getSprite(0, 0);
		}
		button.draw(x, y, w, h);
		g.setColor(ColorUtil.newColor(250, 250, 250));
		g.drawString(text, x + 10, y);
		return w;
	}

	public static int updateSelection(int selection, int width, int height) {
		if (GUIUpdate.isMoveRight()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection++;
		} else if (GUIUpdate.isMoveLeft()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection--;
		}
		if (GUIUpdate.isMoveDown()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection += width;
		} else if (GUIUpdate.isMoveUp()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection -= width;
		}

		int size = width * height;
		if (selection < 0) {
			selection += size;
		} else if (selection >= size) {
			selection -= size;
		}
		return selection;
	}
}
