package com.broll.poklmon.script.commands;

import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.script.ScriptProcessInteraction;

public class CustomScripts {
	private GameManager game;
	private MapObject object;
	private com.broll.poklmon.script.ScriptProcessInteraction process;

	public CustomScripts(GameManager game) {
		this.game = game;
	}

	public void init(MapObject object, ScriptProcessInteraction sceneProcess) {
		this.object = object;
		this.process = sceneProcess;
	}

	public void call(String name) {

	}

}
