package com.broll.poklmon.debug.renders;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Input.Keys;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugRenderSite;
import com.broll.poklmon.game.GameManager;

public class DebugRenderFloatVariables extends DebugRenderSite {

	public DebugRenderFloatVariables(GameManager game) {
		super(game);
	}

	@Override
	public void render(Graphics g) {
		initDrawing();
		drawTitle(g, "Float Variables");
		HashMap<String, Float> vars = game.getPlayer().getData().getGameVariables().getFloats();
		Iterator<String> keyset = vars.keySet().iterator();
		while (keyset.hasNext()) {
			String key = keyset.next();
			String value = vars.get(key).toString();
			drawValuePair(g, key, value);
		}
	}

	@Override
	public int getKeyCode() {
		return Keys.F4;
	}

}
