package com.broll.poklmon.debug.renders;

import com.badlogic.gdx.Input.Keys;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugRenderSite;
import com.broll.poklmon.game.GameManager;

import java.util.HashMap;
import java.util.Iterator;

public class DebugRenderIntVariables extends DebugRenderSite {

	public DebugRenderIntVariables(GameManager game) {
		super(game);
	}

	@Override
	public void render(Graphics g) {
		initDrawing();
		drawTitle(g, "Integer Variables");
		HashMap<String, Integer> vars = game.getPlayer().getData().getGameVariables().getIntegers();
		Iterator<String> keyset = vars.keySet().iterator();
		while (keyset.hasNext()) {
			String key = keyset.next();
			String value = vars.get(key).toString();
			drawValuePair(g, key, value);
		}
	}

	@Override
	public int getKeyCode() {
		return Keys.F3;
	}

}
