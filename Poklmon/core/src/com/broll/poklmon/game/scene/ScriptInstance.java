package com.broll.poklmon.game.scene;

import com.broll.poklmon.script.ObjectScriptExtension;
import com.broll.poklmon.map.object.MapObject;

public class ScriptInstance {

	private MapObject object;
	private String script;
	private Runnable codedScript;
	private ObjectScriptExtension extension;

	public ScriptInstance(MapObject object) {
		this.object = object;
		this.script = object.getTriggerScript();
	}

	public ScriptInstance(MapObject object, ObjectScriptExtension extension) {
		this.object = object;
		this.extension = extension;
		this.script = object.getTriggerScript();
	}

	public ScriptInstance(MapObject object, String script) {
		this.object = object;
		this.script = script;
	}

	public ScriptInstance(Runnable run) {
		this.codedScript = run;
	}

	public Runnable getCodedScript() {
		return codedScript;
	}

	public MapObject getMapObject() {
		return object;
	}

	public String getScript() {
		return script;
	}

	public ObjectScriptExtension getExtension() {
		return extension;
	}
	
	public boolean isCodedScript(){
		return codedScript!=null;
	}
}
