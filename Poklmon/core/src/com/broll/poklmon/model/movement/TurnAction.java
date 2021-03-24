package com.broll.poklmon.model.movement;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.model.OverworldCharacter;

public class TurnAction extends PathAction {

	private final static float WAIT_TIME = 0.2f;
	private ObjectDirection direction;
	private int rotation;
	private boolean random = false;

	public TurnAction() {
		this(null, WAIT_TIME);
		random = true;
	}

	public TurnAction(float time) {
		this(null, time);
		random = true;
	}

	public TurnAction(int rotate) {
		this(null, WAIT_TIME);
		this.rotation = rotate;
	}

	public TurnAction(int rotate, float time) {
		this(null, time);
		this.rotation = rotate;
	}

	public TurnAction(ObjectDirection direction) {
		this(direction, WAIT_TIME);
	}

	public TurnAction(ObjectDirection direction, float time) {
		this.direction = direction;
		startTimer(time);
	}

	@Override
	public void start(OverworldCharacter character, boolean blocking) {
		if (direction != null) {
			character.setDirection(direction);
		} else {
			if (random) {
				character.setDirection(MovementUtil.random());
			} else {
				character.setDirection(MovementUtil.turn(character.getDirection(), rotation));
			}
		}
	}

	@Override
	public boolean isOver() {
		return isTimerDone();
	}

}
