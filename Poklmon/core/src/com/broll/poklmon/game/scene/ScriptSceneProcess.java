package com.broll.poklmon.game.scene;

import javax.script.ScriptException;

public class ScriptSceneProcess implements Runnable {

	protected SceneEndListener endListener;
	private ObjectScriptHandler objectHandler;

	public ScriptSceneProcess(SceneEndListener endListener, ObjectScriptHandler object) {
		this.endListener = endListener;
		this.objectHandler = object;
	}

	protected void runScript() throws NoSuchMethodException, ScriptException{
		// run script
		objectHandler.run();
	}

	@Override
	public void run() {
		try {
			runScript();
			endListener.sceneEnded();
		}catch (Exception e){
			//exception occured in script
			endListener.exceptionOccured(e);
		}
	}

	public synchronized void waitForResume() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void resume() {
		notify();
	}
}
