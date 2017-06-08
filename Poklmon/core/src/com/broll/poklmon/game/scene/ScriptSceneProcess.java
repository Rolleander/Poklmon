package com.broll.poklmon.game.scene;

public class ScriptSceneProcess implements Runnable {

	private SceneEndListener endListener;
	private ObjectScriptHandler objectHandler;

	public ScriptSceneProcess(SceneEndListener endListener, ObjectScriptHandler object) {
		this.endListener = endListener;
		this.objectHandler = object;
	}

	@Override
	public void run() {
		// run script
		objectHandler.run();
		endListener.sceneEnded();
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
