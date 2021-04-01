package com.broll.poklmon.script;

public interface ObjectScriptExtension {

	public void runBefore(com.broll.poklmon.script.ScriptingEnvironment runtime);
	
	public void runAfter(ScriptingEnvironment runtime);
	
	
	
}
