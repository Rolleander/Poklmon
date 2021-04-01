package com.broll.poklmon.game.scene;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.script.ObjectInitEnvironment;
import com.broll.poklmon.script.ObjectRuntimeEnvironment;
import com.broll.poklmon.script.ObjectScriptExtension;
import com.broll.poklmon.main.GameStateManager;
import com.broll.poklmon.map.object.MapObject;
import com.esotericsoftware.minlog.Log;

import java.util.Stack;

public class SceneProcessManager {
	private ScriptSceneProcess scriptProcess;
	private GameManager game;
	private boolean scriptRunning = false;
	private ObjectRuntimeEnvironment runtimeEnvironment;
	private ObjectInitEnvironment initEnvironment;
	private Stack<ScriptInstance> triggerStack = new Stack<ScriptInstance>();
	private GameStateManager stateManager;

	public SceneProcessManager(GameManager game, GameStateManager stateManager) {
		this.game = game;
		this.stateManager=stateManager;
		runtimeEnvironment = new ObjectRuntimeEnvironment();
		initEnvironment = new ObjectInitEnvironment();
	}

	private void finishedScript() {
		if (!triggerStack.isEmpty()) {
			// work next script
			workScript(triggerStack.pop());
		} else {
			if (!game.isMenuOpen()) {
				enablePlayer();
				GameManager.TRIGGER_AGAIN_DELAY_TIMER = GameManager.TRIGGER_AGAIN_DELAY;
			}
			scriptRunning = false;
		}
	}

	private void workScript(ScriptInstance script) {
		scriptRunning = true;
		disablePlayer();
		if (script.isCodedScript()) {
			Runnable runnable = script.getCodedScript();
			scriptProcess = new ControlSceneProcess(runnable, new SceneEndListener() {
				@Override
				public void sceneEnded() {
					finishedScript();
				}
				@Override
				public void exceptionOccured(Exception e) {
					Log.error("Exception occured in ControlSceneProcess",e);
					stateManager.gameException(e);
				}
			});
		} else {
			MapObject object = script.getMapObject();
			final String triggerScript = script.getScript();
			ObjectScriptExtension extension = script.getExtension();
			// turn object towards player
			turnObjectToPlayer(object);
			runtimeEnvironment.addController(game, object);
			ObjectScriptHandler objectScriptHandler = new ObjectScriptHandler(object, triggerScript, runtimeEnvironment);
			objectScriptHandler.setExtension(extension);
			scriptProcess = new ScriptSceneProcess(new SceneEndListener() {
				@Override
				public void sceneEnded() {
					finishedScript();
				}
				@Override
				public void exceptionOccured(Exception e) {
					Log.error("Exception occured in ScriptSceneProcess ["+triggerScript+"]",e);
					stateManager.gameException(e);
				}
			}, objectScriptHandler);
			runtimeEnvironment.initController(object, scriptProcess);
			runtimeEnvironment.importObjects(objectScriptHandler.getEngine());
		}
		Thread thread = new Thread(scriptProcess);
		thread.start();
	}

	public void runScript(ScriptInstance script) {
		if (scriptRunning) {
			// enqueue
			triggerStack.add(script);
		} else {
			// do now
			workScript(script);
		}
	}

	public boolean runInitScript(MapObject object,final String script) {
		initEnvironment.addController(game, object);
		ObjectScriptHandler objectScriptHandler = new ObjectScriptHandler(object, script, initEnvironment);
		ScriptSceneProcess scriptProcess = new ScriptSceneProcess(new SceneEndListener() {
			@Override
			public void sceneEnded() {
			}
			@Override
			public void exceptionOccured(Exception e) {
				Log.error("Exception occured in Init-Script ["+script+"]",e);
				stateManager.gameException(e);
			}
		}, objectScriptHandler);
		initEnvironment.initController(object, scriptProcess);
		initEnvironment.importObjects(objectScriptHandler.getEngine());
		scriptProcess.run();
		// check for remote init
		String remoteInit = initEnvironment.getInitCommands().getRemoteInitScript();
		if (remoteInit != null) {
			// rerun
			return runInitScript(object, remoteInit);
		}
		return initEnvironment.getInitCommands().isDestroyObject();
	}

	private void disablePlayer() {
		game.setMovementAllowed(false);
	}

	private void enablePlayer() {
		game.setMovementAllowed(true);
		game.getMessageGuiControl().hideGui();
	}

	private void turnObjectToPlayer(MapObject object) {
		int xp = (int) game.getPlayer().getOverworld().getXpos();
		int yp = (int) game.getPlayer().getOverworld().getYpos();
		int x = (int) object.getXpos();
		int y = (int) object.getYpos();
		ObjectDirection dir = ObjectDirection.DOWN;
		if (x > xp) {
			dir = ObjectDirection.LEFT;
		} else if (x < xp) {
			dir = ObjectDirection.RIGHT;
		}
		if (y > yp) {
			dir = ObjectDirection.UP;
		} else if (y < yp) {
			dir = ObjectDirection.DOWN;
		}
		object.setDirection(dir);
	}

	public void resume() {
		scriptProcess.resume();
	}

	public void waitForResume() {
		scriptProcess.waitForResume();
	}

	public boolean isScriptRunning() {
		return scriptRunning;
	}

}
