package com.broll.poklmon.game;

import com.broll.poklmon.game.scene.ObjectTriggerCheck;
import com.broll.poklmon.game.scene.SceneProcessManager;
import com.broll.poklmon.game.scene.ScriptInstance;
import com.broll.poklmon.game.scene.script.ObjectRuntimeEnvironment;
import com.broll.poklmon.game.scene.script.ObjectScriptExtension;
import com.broll.poklmon.game.scene.script.ScriptingEnvironment;
import com.broll.poklmon.map.MapAnimation;
import com.broll.poklmon.map.MapContainer;
import com.broll.poklmon.map.MapDisplay;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.movement.MovementListener;
import com.broll.poklmon.player.Player;

import java.util.List;

public class PlayerMovementTrigger implements MovementListener {

	private GameManager game;
	private boolean triggered = false;
	private MapObject triggeredObject = null;

	public PlayerMovementTrigger(GameManager game) {
		this.game = game;
	}

	@Override
	public void movedToTile(int x, int y) {
		checkAllTriggers(x, y);
		// update step
		game.getPlayer().doneStep();
	}

	public void update() {
		if (triggered) {
			// wait for object to stop moving
			if (!triggeredObject.isMoving()) {
				triggered = false;
				runViewTriggerScript(game.getSceneProcessManager(), triggeredObject);
			}
		} else {
			// check for possible event view triggers
			checkViewTriggers(new ViewTrigger() {
				@Override
				public void triggered(MapObject object) {
					triggered = true;
					triggeredObject = object;
					// disable moving for all other characters and wait for
					// triggered event to stop moving
					game.setMovementAllowed(false);
				}
			});
		}
	}

	public boolean isTriggered() {
		return triggered;
	}

	private interface ViewTrigger {
		public void triggered(MapObject object);
	}

	private void checkViewTriggers(ViewTrigger trigger) {
		Player player = game.getPlayer();
		List<MapObject> objects = game.getObjects();
		MapContainer map = game.getMap();
		SceneProcessManager sceneProcessManager = game.getSceneProcessManager();
		if (!sceneProcessManager.isScriptRunning()) {
			// check view triggers
			MapObject interaction = ObjectTriggerCheck.checkViewTrigger(player, objects, map);
			if (interaction != null) {
				// walk object to player and trigger
				// int distance =
				// ObjectTriggerCheck.getObjectViewTriggerDistance();
				trigger.triggered(interaction);
			}
		}
	}

	private void checkAllTriggers(int x, int y) {
		Player player = game.getPlayer();
		List<MapObject> objects = game.getObjects();
		MapContainer map = game.getMap();
		SceneProcessManager sceneProcessManager = game.getSceneProcessManager();
		if (!sceneProcessManager.isScriptRunning()) {
			// check touch triggers
			MapObject interaction = ObjectTriggerCheck.checkTouchTrigger(player, objects, map);
			if (interaction != null) {
				// start trigger script
				sceneProcessManager.runScript(new ScriptInstance(interaction));
			} else {
				// check view triggers
				interaction = ObjectTriggerCheck.checkViewTrigger(player, objects, map);
				if (interaction != null) {
					// walk object to player and trigger it!
					// int distance =
					// ObjectTriggerCheck.getObjectViewTriggerDistance();
					runViewTriggerScript(sceneProcessManager, interaction);
				} else {
					// check aera triggers
					int area = map.getMap().getTile(x, y).getScriptID();
					if (area > 1) {
						// trigger area effects
						map.triggerAreaEffect(area - 2, x, y);
					}
				}
			}
		}
	}

	private void runViewTriggerScript(SceneProcessManager sceneProcessManager, final MapObject object) {
		float x = object.getXpos() * MapDisplay.TILE_SIZE + MapDisplay.TILE_SIZE / 2;
		float y = object.getYpos() * MapDisplay.TILE_SIZE - 35;
		game.getData().getSounds().playSound("trigger_sound");
		MapAnimation animation = new MapAnimation(game.getData().getGraphics().getMapGraphicsContainer().getAction(),
				x, y, 2000);
		animation.setLooping(false);
		game.getMap().showOverlayAnimation(animation);
		sceneProcessManager.runScript(new ScriptInstance(object, new ObjectScriptExtension() {
			@Override
			public void runBefore(ScriptingEnvironment runtime) {
				ObjectRuntimeEnvironment env = (ObjectRuntimeEnvironment) runtime;
				env.getSystemCommands().pause(1); // wait 1 sec
				int oid = object.getObjectId();
				env.getObjectCommands().walkObjectToPlayer(oid);
			}

			@Override
			public void runAfter(ScriptingEnvironment runtime) {
			}
		}));
	}

}
