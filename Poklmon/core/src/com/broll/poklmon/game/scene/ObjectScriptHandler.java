package com.broll.poklmon.game.scene;

import com.broll.poklmon.script.ObjectScriptExtension;
import com.broll.poklmon.script.ProcessingUtils;
import com.broll.poklmon.script.ScriptEngineFactory;
import com.broll.poklmon.script.ScriptingEnvironment;
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
        engine = ScriptEngineFactory.createScriptEngine();
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

    public void run()  {
        if (extension != null) {
            extension.runBefore(environment);
        }
        ProcessingUtils.invokeFunction(engine, script, "f");

        if (extension != null) {
            extension.runAfter(environment);
        }
    }

    public MapObject getSource() {
        return source;
    }

}
