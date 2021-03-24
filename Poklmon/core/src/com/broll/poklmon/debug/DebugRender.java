package com.broll.poklmon.debug;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.renders.DebugRenderBoolVariables;
import com.broll.poklmon.debug.renders.DebugRenderFloatVariables;
import com.broll.poklmon.debug.renders.DebugRenderIntVariables;
import com.broll.poklmon.debug.renders.DebugRenderPlayer;
import com.broll.poklmon.debug.renders.DebugRenderStringVariables;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.input.InputReceiver;
import com.broll.poklmon.input.KeyPressReceiver;
import com.broll.poklmon.resource.GUIFonts;

import java.util.ArrayList;
import java.util.List;

public class DebugRender {

	private GameManager game;
	private List<DebugRenderSite> debugRenderSites = new ArrayList<DebugRenderSite>();
	private DebugRenderSite currentSite = null;

	public DebugRender(GameManager game, InputReceiver inputReceiver) {
		this.game = game;
		debugRenderSites.add(new DebugRenderIntVariables(game));
		debugRenderSites.add(new DebugRenderPlayer(game));
		debugRenderSites.add(new DebugRenderBoolVariables(game));
		debugRenderSites.add(new DebugRenderFloatVariables(game));
		debugRenderSites.add(new DebugRenderStringVariables(game));
		if (PoklmonGame.DEBUG_MODE) {
			inputReceiver.setKeyPressReceiver(new KeyPressReceiver() {
				@Override
				public void keyPressed(int keyCode) {
					for (DebugRenderSite site : debugRenderSites) {
						if (site.getKeyCode() == keyCode) {
							if (currentSite == site) {
								currentSite = null;
							} else {
								currentSite = site;
							}
						}
					}
				}
			});

		}
	}

	public void render(Graphics g) {
		if (currentSite != null) {
			g.setColor(ColorUtil.newColor(0f, 0f, 0f, 0.5f));
			g.fillRect(0, 0, 800, 600);
			g.setFont(GUIFonts.tinyText);
			g.setColor(Color.WHITE);
			currentSite.render(g);
		}
	}

	public void update() {

	}

}
