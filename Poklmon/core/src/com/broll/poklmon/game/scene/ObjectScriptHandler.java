package com.broll.poklmon.game.scene;

import com.broll.poklmon.game.scene.script.ObjectScriptExtension;
import com.broll.poklmon.game.scene.script.ScriptEngineFactory;
import com.broll.poklmon.game.scene.script.ScriptingEnvironment;
import com.broll.poklmon.map.object.MapObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class ObjectScriptHandler {
	private MapObject source;
	private String script;
	private ScriptEngine engine;
	private ScriptingEnvironment environment;
	private ObjectScriptExtension extension;

	public ObjectScriptHandler(MapObject object, String script, ScriptingEnvironment gameInterface) {
		this.source = object;
		this.environment = gameInterface;
		buildScript(script);
	    engine =  ScriptEngineFactory.createScriptEngine();
		this.script = gameInterface.getImporter().buildScript(this.script);
	}

	public void setExtension(ObjectScriptExtension extension) {
		this.extension = extension;
	}

	private void buildScript(String source) {
		script = "function f() { \n";
		script += source;
		script += "}";
	}

	public ScriptEngine getEngine() {
		return engine;
	}

	public void run() throws ScriptException, NoSuchMethodException {
		if (extension != null) {
			extension.runBefore(environment);
		}
			engine.eval(script);
			Invocable invocable = (Invocable) engine;
			invocable.invokeFunction("f");
	
		if (extension != null) {
			extension.runAfter(environment);
		}
	}

	public MapObject getSource() {
		return source;
	}

}
