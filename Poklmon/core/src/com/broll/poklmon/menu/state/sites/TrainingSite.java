package com.broll.poklmon.menu.state.sites;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.poklmon.util.FpCalculator;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

public class TrainingSite extends SpikesStateSite {


	public TrainingSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data) {
		super(poklmonInfo, poklmon, data);

	}


	private short[] fp;
	private String sumText;
	
	@Override
	protected void initData() {
		this.lineWidth = 300;
		fp = poklmon.getFp();

		int sum = 0;

		for (short s : fp) {
			sum += s;
		}
		int perc=(int) (((float)sum/(float)FpCalculator.MAX_SUM)*100);

		initGraphes(fp);
		sumText=perc+"%";
	}

	@Override
	public void render(Graphics g, float x, float y) {

		this.x = x;
		this.y = y;
		updateGraphes();

		g.setFont(GUIFonts.hudText);

		lines = 0;
		
		renderLine(g, "Trainingsfortschritt", "" + sumText);

		this.x += 40;
		this.y += 50;

		g.setColor(ColorUtil.newColor(0, 0, 0, 50));
		float w = (GRAPH_WIDTH + GRAPH_SPACING) * fp.length - GRAPH_SPACING;

		g.fillRect(this.x - 10, this.y, w + 20, GRAPH_HEIGHT);

		for (int i = 0; i < fp.length; i++) {
			renderGraph(g,  i);
		}
	}

}
