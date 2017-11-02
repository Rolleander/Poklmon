package com.broll.poklmon.game.scene;

public class ControlSceneProcess extends ScriptSceneProcess{

	private Runnable process;

	public ControlSceneProcess(Runnable process, SceneEndListener sceneEndListener) {
		super(null, null);	
		this.process=process;
		this.endListener=sceneEndListener;
	}

	@Override
	public void runScript() {
		//run process
		process.run();	
	}
}
