package com.broll.poklmon.menu.state.sites;

import com.badlogic.gdx.graphics.Color;
import com.broll.pokllib.poklmon.EXPCalculator;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

public class InfoSite extends StateSite {

	public InfoSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data) {
		super(poklmonInfo, poklmon, data);

	}

	private int exp, nextexp, missing;
	private float exppercent;

	@Override
	protected void initData() {

		exp = poklmon.getExp();
		int level = poklmon.getLevel();
		nextexp = EXPCalculator.calcEXP(poklmonInfo.getExpLearnType(), level + 1);
		int lastexp = EXPCalculator.calcEXP(poklmonInfo.getExpLearnType(), level);
		missing = nextexp - exp;

		exppercent = (float) (exp - lastexp) / (float) (nextexp - lastexp);

	}

	@Override
	public void render(Graphics g, float x, float y) {

		String catchDate = poklmon.getDateCaught();
		int levelCaught = poklmon.getLevelCaught();
		int pokldex = poklmonInfo.getPokldexNumber();
		int ballType=poklmon.getPoklball();
		String realName = poklmonInfo.getName();
		String item = null;
		int itemId = poklmon.getCarryItem();
		if (itemId > -1) {
			item = data.getItems().getItem(itemId).getName();
		}

		this.x = x;
		this.y = y;

		g.setFont(GUIFonts.hudText);

		lines = 0;
		renderLine(g, "Gefangen", catchDate);
		renderLine(g, "Mit Level", "" + levelCaught);
		Image ball=data.getGraphics().getBattleGraphicsContainer().getPokeballs().getSprite(ballType, 0).getScaledCopy(1.5f);
		renderImageLine(g,"Ball",ball);

		renderLine(g, "Pokldex", "Nr." + pokldex);
		renderLine(g, "Poklmon", "" + realName);
		if (item == null) {
			renderLine(g, "", "");
		} else {
			renderLine(g, "Trägt", item);
		}
		renderLine(g, "EXP", "" + exp);
		renderLine(g, "N.Level", "" + nextexp);
		renderLine(g, "Noch", "" + missing);

		this.y += 25;
		renderEXPBar(g, x + 10, this.y, exppercent);

	}

	private void renderEXPBar(Graphics g, float x, float y, float expPercent) {
		int w = 430;
		int h = 20;

		int p = 5;
		Color c1 = ColorUtil.newColor(216, 208, 176);
		Color c2 = ColorUtil.newColor(192, 184, 112);

		for (int i = 0; i < w / p; i++) {
			if (i % 2 == 0) {
				g.setColor(c1);
			} else {
				g.setColor(c2);
			}
			g.fillRect(x + i * p, y, p, h);
		}

		float b = (float) w * expPercent;
		g.setColor(ColorUtil.newColor(64, 200, 248));
		g.fillRect(x, y, b, h);

		g.setColor(ColorUtil.newColor(80, 104, 96));
		g.drawRect(x, y, w, h);
	}

}
