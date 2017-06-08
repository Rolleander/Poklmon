package com.broll.poklmon.model.movement;

import com.broll.pokllib.object.ObjectDirection;

public abstract class MovementUtil {

	public static int getMissingSteps(int x, int y, int tx, int ty) {
		if (tx == x) {
			// move up or down
			int dist = y - ty;
			return Math.abs(dist) ;
		} else if (ty == y) {
			// move left or right
			int dist = x - tx;
			return Math.abs(dist) ;
		}
		//should not happen!
		return 0;
	}

	public static int getDirectionX(ObjectDirection direction) {
		switch (direction) {
		case LEFT:
			return -1;
		case RIGHT:
			return 1;
		default:
			return 0;
		}
	}

	public static ObjectDirection turn(ObjectDirection direction, int rotation) {
		if (rotation > 0) {
			return turnRight(direction, rotation);
		} else if (rotation < 0) {
			return turnLeft(direction, rotation * -1);
		}
		return direction;
	}

	public static ObjectDirection random() {
		int r = (int) (Math.random() * 4);
		return ObjectDirection.values()[r];
	}

	public static ObjectDirection turnRight(ObjectDirection direction, int times) {
		for (int i = 0; i < times; i++) {
			switch (direction) {
			case DOWN:
				direction = ObjectDirection.LEFT;
				break;
			case LEFT:
				direction = ObjectDirection.UP;
				break;
			case UP:
				direction = ObjectDirection.RIGHT;
				break;
			case RIGHT:
				direction = ObjectDirection.DOWN;
				break;
			}
		}
		return direction;
	}

	public static ObjectDirection turnLeft(ObjectDirection direction, int times) {
		for (int i = 0; i < times; i++) {
			switch (direction) {
			case DOWN:
				direction = ObjectDirection.RIGHT;
				break;
			case LEFT:
				direction = ObjectDirection.DOWN;
				break;
			case UP:
				direction = ObjectDirection.LEFT;
				break;
			case RIGHT:
				direction = ObjectDirection.UP;
				break;
			}
		}
		return direction;
	}

	public static ObjectDirection getOppositeDirection(ObjectDirection direction) {
		switch (direction) {
		case DOWN:
			return ObjectDirection.UP;
		case LEFT:
			return ObjectDirection.RIGHT;
		case RIGHT:
			return ObjectDirection.LEFT;
		case UP:
			return ObjectDirection.DOWN;
		}
		return null;
	}

	public static int getDirectionY(ObjectDirection direction) {
		switch (direction) {
		case DOWN:
			return 1;
		default:
			return 0;
		case UP:
			return -1;
		}
	}

}
