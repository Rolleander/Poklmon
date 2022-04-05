package com.broll.poklmon.battle.render.hud;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;

public abstract class StateBar {
	protected Image box;
	protected SpriteSheet healthBar;
	public static int healthBarWidth = 154;
	public static Color textColor = ColorUtil.newColor(80, 80, 80);

	protected void initGraphics(boolean player, DataContainer data) {
		if (player) {
			box = data.getGraphics().getBattleGraphicsContainer().getFightboxPlayer();
		} else {
			box = data.getGraphics().getBattleGraphicsContainer().getFightboxEnemy();
		}
		healthBar = data.getGraphics().getBattleGraphicsContainer().getHealthbars();
	}

	public static Image getHealtBarSprite(SpriteSheet healtbar, float percent) {
		if (percent >= 0.5) {
			return healtbar.getSprite(3, 0);
		} else if (percent >= 0.25) {
			return healtbar.getSprite(2, 0);
		} else {
			return healtbar.getSprite(1, 0);
		}
	}

	protected void drawHealthBar(int x, int y, float percent) {
		Image bar = getHealtBarSprite(healthBar, percent);
		if (percent > 0) {
			float w = percent * healthBarWidth;
			if (w < 1) {
				w = 1;
			}
			bar.draw(x, y, w, 10);
		}
	}

	protected void drawName(Graphics g, int x, int y, String name) {
		g.setFont(GUIFonts.hudText);
		g.setColor(textColor);
		g.drawString(name, x, y);
	}

	protected void drawLevel(Graphics g, int x, int y, int level) {
		g.setFont(GUIFonts.hudText);
		g.setColor(textColor);
		if (level >= 100) {
			g.drawString("Lv." + level, x - 15, y);
			return;
		}
		g.drawString("Lv." + level, x, y);
	}
}
