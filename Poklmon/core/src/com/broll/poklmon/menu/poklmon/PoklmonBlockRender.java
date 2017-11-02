package com.broll.poklmon.menu.poklmon;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.render.hud.HudRenderUtils;
import com.broll.poklmon.battle.render.hud.StateBar;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.menu.MenuUtils;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

public class PoklmonBlockRender {
	public static int HEIGHT = 130;
	public static int WIDTH = 380;
	private static int KP_BAR_WIDTH = 222;
	private DataContainer data;
	private FontUtils fontUtils=new FontUtils();

	public PoklmonBlockRender(DataContainer data) {
		this.data = data;
	}

	public void render(Graphics g, float x, float y, PoklmonData poklmon, boolean selected) {
		Image block;
		SpriteSheet blocks = data.getGraphics().getMenuGraphicsContainer().getTeamBlock();
		if (poklmon == null) {
			block = blocks.getSprite(0, 0);
			// draw background
			block.draw(x, y);
			if (selected) {
				g.setColor(ColorUtil.newColor(250, 250, 250, 50));
				g.fillRect(x, y, WIDTH, HEIGHT);
			}
			return;
		} else {
			if (selected) {
				block = blocks.getSprite(0, 2);
			} else {
				block = blocks.getSprite(0, 1);
			}
			// draw background
			block.draw(x, y);
		}

		int id = poklmon.getPoklmon();
		Poklmon poklInfo = data.getPoklmons().getPoklmon(id);

		// draw image
		Image poklImage = data.getGraphics().getPoklmonImage(poklInfo.getGraphicName());
		float ix = x + 6 + 48;
		float iy = y + 6 + 48;
		poklImage.drawCentered(ix, iy);

		// draw infos
		String name = poklmon.getName();
		if (name == null) {
			name = poklInfo.getName();
		}

		float tx = x + 107;
		float ty = y - 3;
		g.setFont(GUIFonts.dialogText);
		g.setColor(ColorUtil.newColor(250, 250, 250));
		g.drawString(name, tx + 5, ty);

		tx+=15;
		// draw status
		MainFightStatus status = poklmon.getStatus();
		if (status != null) {
			HudRenderUtils.renderMainStatus(g,fontUtils, status, (int) (tx), (int) (ty+50));
		}
		
		g.setFont(GUIFonts.smallText);
		//draw item
		int itemId = poklmon.getCarryItem();
		if (itemId > -1) {
			String item = data.getItems().getItem(itemId).getName();
			g.setColor(ColorUtil.newColor(250, 250, 250));
			MenuUtils.drawFancyString(g, item, tx, ty+75);
		}

		
		// draw level
		int level = poklmon.getLevel();
		g.setColor(ColorUtil.newColor(50, 50, 50, 150));
		g.fillRect(x + 6, y + 82, 96, 20);
		g.setColor(ColorUtil.newColor(250, 250, 250));
		MenuUtils.drawFancyString(g, "Lv." + level, x + 10, y + 78);

		ty += 20;

		// draw kp bar
		float kp = poklmon.getKp();
		short kpDV = poklmon.getDv()[0];
		short kpFP = poklmon.getFp()[0];
		float maxKp = PoklmonAttributeCalculator.getKP(poklInfo, level, kpDV, kpFP);
		float percent = kp / maxKp;
		if (percent < 0) {
			percent = 0;
		} else if (percent > 1) {
			percent = 1;
		}

		Image bar = StateBar
				.getHealtBarSprite(data.getGraphics().getBattleGraphicsContainer().getHealthbars(), percent);
		float w = percent * KP_BAR_WIDTH;
		bar.draw(x + 50, y + 111, w, 10);

		// kp bar info
		g.setColor(ColorUtil.newColor(250, 250, 250));
		g.drawString((int) kp + " / " + (int) maxKp, x + 285, y + 103);

	}

}
