package com.broll.poklmon.script;

import com.badlogic.gdx.utils.GdxRuntimeException;

import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptEngineFactory {

	public static ScriptEngine createScriptEngine() {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("Nashorn");
		if(engine==null) {
			engine = factory.getEngineByName("rhino");
		}
		if(engine==null) {
			throw new GdxRuntimeException("No scripting Engine found!");
		}
		return engine;
	}
}
