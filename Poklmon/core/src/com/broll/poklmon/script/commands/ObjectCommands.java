package com.broll.poklmon.script.commands;

import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.ScriptInstance;
import com.broll.poklmon.script.CommandControl;
import com.broll.poklmon.script.Invoke;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.movement.MoveCommandListener;

public class ObjectCommands extends CommandControl {

	public ObjectCommands(GameManager game) {
		super(game);
	}

	public void teleportObject(int oid, int x, int y) {
		MapObject object = game.getObject(oid);
		object.teleport(x, y);
	}

	public void destroy() {
		removeObject(object.getObjectId());
	}

	public void removeObject(int oid) {
		for (int i = 0; i < game.getObjects().size(); i++) {
			MapObject obj = game.getObjects().get(i);
			int id = obj.getObjectId();
			if (id == oid) {
				if (obj.isBlocking()) {
					obj.setBlocking(false);
				}
				game.getObjects().remove(i);
				return;
			}
		}
	}

	public void changeObjectVisibility(int oid, boolean visible) {
		game.getObject(oid).setVisible(visible);
	}

	public void changeObjectTrigger(int oid, boolean active) {
		game.getObject(oid).setTriggerActive(active);
	}

	public void triggerObject(int oid) {
		MapObject object = game.getObject(oid);
		game.getSceneProcessManager().runScript(new ScriptInstance(object));
	}

	public void triggerRemoteScript(int mapid, int oid) {
		try {
			MapFile map = PoklLib.data().readMap(mapid);
			String script = map.getObjects().get(oid).getTriggerScript();
			game.getSceneProcessManager().runScript(new ScriptInstance(object, script));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void moveObject(int oid, String dir) {
		String s = dir.trim().toUpperCase();
		if (s.equals("UP")) {
			moveObject(oid, ObjectDirection.UP);
		} else if (s.equals("DOWN")) {
			moveObject(oid, ObjectDirection.DOWN);
		} else if (s.equals("LEFT")) {
			moveObject(oid, ObjectDirection.LEFT);
		} else if (s.equals("RIGHT")) {
			moveObject(oid, ObjectDirection.RIGHT);
		}
	}

	public void moveObject(int oid, ObjectDirection direction) {
		MapObject mo = game.getObject(oid);
		moveObject(mo, direction, 1);
	}

	public void turnObject(int oid, ObjectDirection dir) {
		game.getObject(oid).setDirection(dir);
	}

	public void changeObjectKnown(int mapNr, int oid, boolean known) {
		game.getPlayer().getVariableControl().setObjectToKnown(mapNr, oid, known);
	}

	public void setObjectCharacterSprite(int oid, String sprite) {
		MapObject mo = game.getObject(oid);
		mo.setGraphic(game.getData().getGraphics().getCharImage(sprite));
	}

	public void setObjectTilesetSprite(int oid, int tileX, int tileY) {
		MapObject mo = game.getObject(oid);
		Image image = game.getData().getGraphics().getTileSet().getSprite(tileX, tileY);
		mo.setFixGraphic(image);
	}

	public void setFixCharGraphic(int oid, int x, int y) {
		MapObject mo = game.getObject(oid);
		mo.setFixedChar(x, y);
	}

	public void walkObjectToPlayer(int oid) {
		MapObject object = game.getObject(oid);
		int x = (int) object.getXpos();
		int y = (int) object.getYpos();
		int px = (int) game.getPlayer().getOverworld().getXpos();
		int py = (int) game.getPlayer().getOverworld().getYpos();
		if (px == x) {
			// move up or down
			int dist = py - y;
			ObjectDirection dir = null;
			if (dist > 0) {
				dir = ObjectDirection.DOWN;
			} else if (dist < 0) {
				dir = ObjectDirection.UP;
			}
			moveObject(object, dir, Math.abs(dist) - 1);
		} else if (py == y) {
			// move left or right
			int dist = px - x;
			ObjectDirection dir = null;
			if (dist > 0) {
				dir = ObjectDirection.RIGHT;
			} else if (dist < 0) {
				dir = ObjectDirection.LEFT;
			}
			moveObject(object, dir, Math.abs(dist) - 1);
		}
	}

	private void moveObject(final MapObject object, final ObjectDirection dir,final int count) {
		invoke(new Invoke() {
			public void invoke() {
				object.move(dir, false, count, new MoveCommandListener() {
					@Override
					public void stoppedMoving(boolean success) {
						resume();
					}
				});
			}
		});
	}

	public int getObjectX(int oid) {
		return (int) game.getObject(oid).getXpos();
	}

	public int getObjectY(int oid) {
		return (int) game.getObject(oid).getYpos();
	}

	public ObjectDirection getDirection(int oid) {
		return game.getObject(oid).getDirection();
	}

	public void setDirection(int oid, ObjectDirection dir) {
		game.getObject(oid).setDirection(dir);
	}

	public int getObjectId() {
		return object.getObjectId();
	}

	public void resetKnown() {
		game.getPlayer().getVariableControl().setObjectToKnown(game.getMap().getMapId(), object.getObjectId(), false);
	}

	public void setKnown() {
		game.getPlayer().getVariableControl().setObjectToKnown(game.getMap().getMapId(), object.getObjectId(), true);
	}

	public boolean isObjectKnown(int mapNr, int oid) {
		return game.getPlayer().getVariableControl().isKnownobject(mapNr, oid);
	}

	public boolean isKnown() {
		return game.getPlayer().getVariableControl().isKnownobject(game.getMap().getMapId(), object.getObjectId());
	}

	public boolean isObjectVisible(int oid) {
		return game.getObject(oid).isVisible();
	}

	public boolean isObjectTriggerActive(int oid) {
		return game.getObject(oid).isTriggerActive();
	}

}
