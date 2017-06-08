package com.broll.poklmon.battle.player;

import java.util.ArrayList;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.render.hud.HudRenderUtils;
import com.broll.poklmon.battle.render.hud.StateBar;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;

public class PoklmonSelectionRender {

	private final static int WIDTH = 350;
	private final static int HEIGHT = 100;
	private DataContainer data;
	private BattleManager battle;

	public PoklmonSelectionRender(DataContainer data, BattleManager battle) {
		this.data = data;
		this.battle = battle;
	}

	public void render(Graphics g, ArrayList<FightPoklmon> team, int selected) {
		/*
		 * g.setColor(ColorUtil.newColor(0,0,0,200)); //
		 * g.fillRect(0,0,800,600-DialogBox.HEIGHT); g.fillRect(0,0,800,600);
		 */
//TODO
	//	GradientFill gradient = new GradientFill(0, 0, ColorUtil.newColor(200, 200, 250), 0, 600, ColorUtil.newColor(50, 50, 150));
		//g.fill(new Rectangle(0, 0, 800, 600), gradient);

		for (int i = 0; i < 6; i++) {
			if (team.size() > i) {
				FightPoklmon fp = team.get(i);

				int x, y;
				int a = (400 - WIDTH) / 2;
				if (i % 2 == 0) {
					x = a;
				} else {
					x = 400 + a;
				}
				y = 60 + (i / 2) * (HEIGHT + 50);
				renderSelection(g, fp, x, y, i == selected);
			}

		}

	}

	public int[] getSelectPos(int selection) {
		int x = 0;
		int y = 0;
		int i = selection;
		int a = (400 - WIDTH) / 2;
		if (i % 2 == 0) {
			x = a;
		} else {
			x = 400 + a;
		}
		y = 60 + (i / 2) * (HEIGHT + 50);
		return new int[] { x, y };
	}

	private float yplus, angle;

	private void renderSelection(Graphics g, FightPoklmon poklmon, float x, float y, boolean selected) {

		if (selected) {
			angle += 0.08;
			y += Math.sin(angle) * 5;
			int b = 8;
			g.setColor(ColorUtil.newColor(50, 50, 50));
			g.fillRect(x - b, y - b, WIDTH + b * 2, HEIGHT + b * 2);
			b = 4;
			g.setColor(ColorUtil.newColor(250, 250, 250));
			g.fillRect(x - b, y - b, WIDTH + b * 2, HEIGHT + b * 2);

		}

		Image box = data.getGraphics().getBattleGraphicsContainer().getTeamBox();
		box.draw(x, y);

		String name = poklmon.getName();

		// draw image
		Image image = poklmon.getImage();
		g.drawImage(image, x + 2, y + 2);

		// overlay
		String overlay = null;
		if (poklmon.isFainted()) {
			overlay = "Besiegt";
		} else {
			if (poklmon == battle.getParticipants().getPlayer()) {
				overlay = "Im Kampf";
			}
		}

		g.setFont(GUIFonts.smallText);
		if (overlay != null) {
			int bh = 25;
			g.setColor(ColorUtil.newColor(0, 0, 0, 150));
			g.fillRect(x, y + HEIGHT - bh, 98, bh);

			float c = x + 49;
			g.setColor(ColorUtil.newColor(250, 250, 250));
			
			g.drawString(overlay, c -FontUtils.getWidth( GUIFonts.smallText,overlay) / 2, y + 72);
		}

		float ty = y - 3;
		g.setFont(GUIFonts.hudText);
		g.setColor(ColorUtil.newColor(250, 250, 250));
		g.drawString(name, x + 100, ty);

		g.setFont(GUIFonts.smallText);

		g.setColor(ColorUtil.newColor(250, 250, 250));
		int level = poklmon.getLevel();
		String ls = "Lv." + level;
		float tx = x + WIDTH - 5 - FontUtils.getWidth(GUIFonts.smallText,ls);
		ty += 8;
		g.drawString(ls, tx, ty);
		g.setColor(ColorUtil.newColor(50, 50, 50));
		g.drawString(ls, tx - 1, ty - 1);

		float health = poklmon.getAttributes().getHealthPercent();
		renderKPBar(g, x + 141, y + 36, health);

		g.setColor(ColorUtil.newColor(50, 50, 50));
		int kp = poklmon.getAttributes().getHealth();
		int maxkp = poklmon.getAttributes().getMaxhealth();
		tx = x + 100;
		ty += 44;
		g.drawString(kp + "/" + maxkp + " KP", tx, ty);
		ty += 25;

		ElementType baseElement = poklmon.getPoklmon().getBaseType();
		ElementType secondElement = poklmon.getPoklmon().getSecondaryType();
		String text = baseElement.getName();
		if (secondElement != null) {
			text += " " + secondElement.getName();
		}
		g.drawString(text, tx, ty);
		
		// draw status
		MainFightStatus status = poklmon.getStatusChanges().getMainStatus();
		if (status != null) {
			HudRenderUtils.renderMainStatus(g,status, (int)(x+WIDTH-62), (int)(y+HEIGHT-25));
		}

	}

	private void renderKPBar(Graphics g, float x, float y, float percent) {
		int kpbarwidth = 204;
		int kpbarheight = 10;

		/*
		 * g.setColor(ColorUtil.newColor(20,20,20)); int border=1; x-=border; y+=border;
		 * g.fillRect(x-border,y-border,kpbarwidth+border*2,kpbarheight+border*2
		 * ); g.setColor(ColorUtil.newColor(80,104,88));
		 * g.fillRect(x,y,kpbarwidth,kpbarheight);
		 */

		int kpw = (int) (percent * kpbarwidth);
		if (kpw == 0) {
			if (percent > 0) {
				kpw = 1;
			}
		}

		Image bar = StateBar.getHealtBarSprite(data.getGraphics().getBattleGraphicsContainer().getHealthbars(),
				percent);
		bar.draw(x, y, kpw, kpbarheight);
	}

}
