package com.broll.poklmon.game.scene;

import java.util.List;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.map.MapContainer;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.movement.MovementUtil;
import com.broll.poklmon.player.Player;

public abstract class ObjectTriggerCheck {

	private static int objectDistance;

	public static MapObject checkClickTrigger(Player player, List<MapObject> objects, MapContainer map) {
		int xpos = (int) player.getOverworld().getXpos();
		int ypos = (int) player.getOverworld().getYpos();
		ObjectDirection dir = player.getOverworld().getDirection();
		// forward playerpos in view direction
		int fxpos = xpos + MovementUtil.getDirectionX(dir);
		int fypos = ypos + MovementUtil.getDirectionY(dir);

		for (MapObject object : objects) {
			// only objects with click trigger
			if (!object.isTouchTrigger()) {
				int x = (int) object.getXpos();
				int y = (int) object.getYpos();
				if (!object.isBlocking()) {
					// check possible object bellow player (foottraps etc)
					if (x == xpos && y == ypos) {
						return object;
					}
				} else {
					// check object in players direction
					if (x == fxpos && y == fypos) {
						return object;
					}
				}
			}
		}
		return null;
	}

	public static boolean objectFacesPlayer(Player player, MapObject object) {
		int xposp = (int) player.getOverworld().getXpos();
		int yposp = (int) player.getOverworld().getYpos();
		int xpos = (int) object.getXpos();
		int ypos = (int) object.getYpos();
		ObjectDirection dir = object.getDirection();
		if (xpos != xposp && ypos != yposp) {
			return false;
		}
		if (xpos > xposp && dir == ObjectDirection.LEFT) {
			return true;
		}
		if (xpos < xposp && dir == ObjectDirection.RIGHT) {
			return true;
		}
		if (ypos > yposp && dir == ObjectDirection.UP) {
			return true;
		}
		if (ypos < yposp && dir == ObjectDirection.DOWN) {
			return true;
		}
		return false;
	}

	public static int getObjectDistance(Player player, MapObject object) {
		int xposp = (int) player.getOverworld().getXpos();
		int yposp = (int) player.getOverworld().getYpos();
		int xpos = (int) object.getXpos();
		int ypos = (int) object.getYpos();
		if (xposp == xpos) {
			if (yposp > ypos) {
				return yposp - ypos;
			} else {
				return ypos - yposp;
			}
		} else if (yposp == ypos) {
			if (xposp > xpos) {
				return xposp - xpos;
			} else {
				return xpos - xposp;
			}
		}
		return Integer.MAX_VALUE;
	}

	public static boolean isDistancePassable(MapObject object, ObjectDirection dir, int length, MapContainer map) {
		int xpos = (int) object.getXpos();
		int ypos = (int) object.getYpos();
		for (int i = 0; i < length; i++) {
			switch (dir) {
			case DOWN:
				ypos++;
				break;
			case LEFT:
				xpos--;
				break;
			case RIGHT:
				xpos++;
				break;
			case UP:
				ypos--;
				break;
			}
			if (!map.isPassable(xpos, ypos,dir,object.getWorldState())) {
				return false;
			}
		}
		return true;
	}

	public static MapObject checkViewTrigger(Player player, List<MapObject> objects, MapContainer map) {
		for (MapObject object : objects) {
			int view = object.getViewTriggerRange();
			if (view > 0) {
				if (objectFacesPlayer(player, object)) {
					int dist = getObjectDistance(player, object);
					if (dist <= view) {
						objectDistance = dist - 1;
						if (objectDistance == 0) {
							return object;
						} else if (isDistancePassable(object, object.getDirection(), objectDistance, map)) {
							return object;
						}
					}
				}

			}
		}
		return null;
	}

	public static int getObjectViewTriggerDistance() {
		return objectDistance;
	}

	public static MapObject checkTouchTrigger(Player player, List<MapObject> objects, MapContainer map) {
		int xpos = (int) player.getOverworld().getXpos();
		int ypos = (int) player.getOverworld().getYpos();

		for (MapObject object : objects) {
			// only objects with touch trigger
			if (object.isTouchTrigger()) {
				int x = (int) object.getXpos();
				int y = (int) object.getYpos();
				if (!object.isBlocking()) {
					// check possible object bellow player (foottraps etc)
					if (x == xpos && y == ypos) {
						return object;
					}
				}
			}
		}
		return null;
	}

}
