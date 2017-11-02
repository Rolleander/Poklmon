package com.broll.poklmon.battle.render.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;

public abstract class HudRenderUtils {

	public static void renderMainStatus(Graphics g,FontUtils fontUtils, MainFightStatus status, int x, int y) {

		int w = 59;
		int h = 22;
		int b = 8;
		g.setColor(ColorUtil.newColor(46, 46, 46));
		g.fillRoundRect(x, y, w, h, b);
		g.setColor(status.getColor());
		g.fillRoundRect(x + 2, y + 2, w - 4, h - 4, b - 4);
		g.setColor(ColorUtil.newColor(248, 248, 248));
		BitmapFont font = GUIFonts.tinyText;
		String txt = status.getIcontext();
		int tw = fontUtils.getWidth(font, txt);
		g.setFont(font);
		g.drawString(txt, x + w / 2 - tw / 2, y);
	}

}
