package com.broll.poklmon.game.scene.script.commands;

import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.ScriptProcessInteraction;
import com.broll.poklmon.map.object.MapObject;

public class CustomScripts {
	private GameManager game;
	private MapObject object;
	private ScriptProcessInteraction process;

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
