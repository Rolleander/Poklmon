package com.broll.poklmon.map;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.map.areas.MapArea;
import com.broll.poklmon.model.CharacterWorldState;
import com.broll.poklmon.model.movement.MovementUtil;

public class MapTilePassableCheck {

	private CurrentMap map;

	public MapTilePassableCheck(CurrentMap map) {
		this.map = map;
	}
	
	public boolean isLedge(int x, int y, ObjectDirection moveDir) {
		MapTile tile = map.getTile(x, y);
		if (tile != null) {
			ObjectDirection ledge = tile.getLedge();
			if (ledge != null) {
				if (ledge == moveDir) {
					return true;
				}
			}
		}
		return false;
	}

	private MapArea getArea(int x, int y) {
		return map.getArea(x, y);
	}

	private boolean isStandardPassable(int x, int y, ObjectDirection moveDir) {
		MapTile tile = map.getTile(x, y);
		// check blocked
		
		boolean blocked = tile.isBlocked();
		if (blocked) {
			return false;
		}
		// check ledge
		boolean ledgeBlocked = false;
		if (moveDir != null) {
			ObjectDirection ledge = tile.getLedge();
			if (ledge != null) {
				ObjectDirection dir = MovementUtil.getOppositeDirection(moveDir);
				ledgeBlocked = dir == ledge;
				if (ledgeBlocked) {
					return false;
				}
			}
		}
		// check area script for (water)
		MapArea area = getArea(x, y);
		if (area != null) {
			if (area.isWater()) {
				return false;
			}
		}
		return true;

	}

	private boolean isSwimmingPassable(int x, int y, ObjectDirection moveDir) {
		// only water passable
		MapArea area = getArea(x, y);
		if (area != null) {
			if (area.isWater()) {
				return true;
			}
		}
		return false;

	}

	public boolean isPassable(int x, int y, ObjectDirection moveDir, CharacterWorldState worldState) {
		MapTile tile = map.getTile(x, y);
		if (tile == null) {
			return false;
		}
		if (worldState == CharacterWorldState.STANDARD) {
			return isStandardPassable(x, y, moveDir);
		} else if (worldState == CharacterWorldState.SWIMMING) {
			return isSwimmingPassable(x, y, moveDir);
		}
		return false;
	}

}
