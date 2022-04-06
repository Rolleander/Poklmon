package com.broll.poklmon.game.scene;

import com.broll.poklmon.script.CancelProcessingException;
import com.broll.poklmon.script.ScriptProcessingRunnable;

import javax.script.ScriptException;

public class ScriptSceneProcess extends ScriptProcessingRunnable {

    protected SceneEndListener endListener;
    private ObjectScriptHandler objectHandler;

    public ScriptSceneProcess(SceneEndListener endListener, ObjectScriptHandler object) {
        this.endListener = endListener;
        this.objectHandler = object;
    }

    protected void runScript() {
        // run script
        objectHandler.run();
    }

    @Override
    public void runProcess() {
        runScript();
        endListener.sceneEnded();
    }

}
