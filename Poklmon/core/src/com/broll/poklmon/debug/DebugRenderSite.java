package com.broll.poklmon.debug;

import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.GameManager;

public abstract class DebugRenderSite {

	protected GameManager game;
	protected final static int Y_SPACING = 15;
	protected final static int X_SPACING = 250;
	protected final static int Y_START = 20;
	protected final static int X_START = 10;

	private int renderx, rendery;

	public DebugRenderSite(GameManager game) {
		this.game = game;
	}

	protected void initDrawing() {
		this.renderx = X_START;
		this.rendery = Y_START;
	}

	private void checkNewColumn() {
		if (rendery >= 600 - Y_SPACING) {
			rendery = Y_START;
			renderx += X_SPACING;
		}
	}

	protected void drawTitle(Graphics g, String text) {
		g.drawString("#DEBUG# "+text, X_START, 4);
	}

	protected void drawValuePair(Graphics g, String key, String value) {
		g.drawString(key + " : [" + value + "]", renderx, rendery);
		rendery += Y_SPACING;
		checkNewColumn();
	}
	
	protected void drawValuePair(Graphics g, String key, double value) {
		double f_neu =  (Math.round(value * 100) / 100.0);
		drawValuePair(g, key, ""+f_neu);
	}
	
	protected void drawValuePair(Graphics g, String key, int value) {
		drawValuePair(g, key, ""+value);
	}
	

	public abstract void render(Graphics g);

	public abstract int getKeyCode();

}
