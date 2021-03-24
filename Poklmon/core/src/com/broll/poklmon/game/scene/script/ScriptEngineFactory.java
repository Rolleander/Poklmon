package com.broll.poklmon.game.scene.script;

import com.badlogic.gdx.utils.GdxRuntimeException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptEngineFactory {

	public static ScriptEngine createScriptEngine() {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("rhino");
		if(engine==null) {
			 engine = factory.getEngineByName("JavaScript");
		}
		if(engine==null) {
			throw new GdxRuntimeException("No scripting Engine found!");
		}
		return engine;
	}
}
