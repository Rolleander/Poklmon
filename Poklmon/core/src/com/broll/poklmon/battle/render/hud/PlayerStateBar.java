package com.broll.poklmon.battle.render.hud;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;

public class PlayerStateBar extends StateBar {
	private static int expBarWidth = 206;
	private static int expBarHeight = 6;
	public static Color expBarColor = ColorUtil.newColor(64, 200, 248);
	private FontUtils fontUtils=new FontUtils();

	public PlayerStateBar(DataContainer data) {
		initGraphics(true, data);
	}

	public void render(Graphics g, PlayerPoklmon poklmon) {
		int x = 460;
		int y = 320;

		box.draw(x, y);

		// draw name
		drawName(g, x + 55, y + 12, poklmon.getName());

		// draw level
		drawLevel(g, x + 240, y + 12, poklmon.getLevel());

		// draw kp bar
		drawHealthBar(x + 155, y + 55, poklmon.getAttributes().getHealthPercent());

		// draw kp
		g.setFont(GUIFonts.hudText);
		int kp = poklmon.getAttributes().getHealth();
		int maxkp = poklmon.getAttributes().getMaxhealth();
		String text = kp + "/" + maxkp;
		int w = fontUtils.getWidth( GUIFonts.hudText,text);
		g.drawString(text, x + 300 - w, y + 68);

		// draw exp
		float exp = poklmon.getEXPPercent();
		int width = (int) (exp * expBarWidth);
		g.setColor(expBarColor);
		g.fillRect(x + 103, y + 107, width, expBarHeight);

		// draw status
		MainFightStatus status = poklmon.getStatusChanges().getMainStatus();
		if (status != null) {
			HudRenderUtils.renderMainStatus(g,fontUtils,status, x + 41, y + 49);
		}
	}

}
