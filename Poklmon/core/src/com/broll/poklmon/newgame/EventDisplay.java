package com.broll.poklmon.newgame;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class EventDisplay {

	private Image prof, poklmon;
	private boolean showProf, showPokmlon;
	private DataContainer data;

	public EventDisplay(DataContainer data) {
		this.data = data;
		prof = data.getGraphics().getEventGraphicsContainer().getProfPfiffikus();
		showProf = true;
	}

	public void showPokmlon(int id) {
		String imagename = data.getPoklmons().getPoklmon(id).getGraphicName();
		poklmon = data.getGraphics().getPoklmonImage(imagename).getScaledCopy(2);
		showPokmlon = true;
	}

	public void hidePoklmon() {
		showPokmlon = false;
	}

	public void setShowProf(boolean showProf) {
		this.showProf = showProf;
	}

	public void render(Graphics g) {
		if (showProf) {
			prof.drawCentered(400, 250);
		}

		if (showPokmlon) {
			poklmon.draw(100, 180);
		}
	}
}
