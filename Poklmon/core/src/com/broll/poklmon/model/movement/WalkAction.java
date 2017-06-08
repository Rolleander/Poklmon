package com.broll.poklmon.model.movement;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.model.OverworldCharacter;

public class WalkAction extends PathAction {

	private boolean done = false;
	private ObjectDirection direction;
	private int steps;
	private boolean random = false;
	private int tx, ty;
	private OverworldCharacter character;
	private int continueSteps = 0;
	private int doneSteps;
	private boolean blocking = true;

	public WalkAction(boolean random, int steps) {
		this(null, steps);
		this.random = random;
	}

	public WalkAction(ObjectDirection direction, int steps) {
		this.direction = direction;
		this.steps = steps;
	}

	@Override
	public void start(OverworldCharacter character, boolean blocking) {
		this.character = character;
		this.blocking = blocking;
		doneSteps = 0;
		start(steps);
	}

	private void start(int steps) {
		done = false;
		continueSteps = 0;
		if (random) {
			direction = MovementUtil.random();
			character.move(direction, false, steps, new MoveCommandListener() {
			@Override
				public void stoppedMoving(boolean success) {
					done = true;
				}
			});
		} else {
			ObjectDirection moveDirection = direction;
			if (direction == null) {
				moveDirection = character.getDirection();
			}
			tx = (int) character.getXpos() + MovementUtil.getDirectionX(moveDirection) * steps;
			ty = (int) character.getYpos() + MovementUtil.getDirectionY(moveDirection) * steps;
			character.move(moveDirection, false, steps, new MoveCommandListener() {
				@Override
				public void stoppedMoving(boolean success) {
					checkForTarget();
				}
			});
		}
	}

	private void checkForTarget() {
		int x = (int) character.getXpos();
		int y = (int) character.getYpos();
		doneSteps++;
		continueSteps = MovementUtil.getMissingSteps(x, y, tx, ty);

		done = continueSteps <= 0; // wait when blocked
		if (done == false && !blocking && doneSteps >= steps) {
			done = true;
			continueSteps = 0;
		}
	}

	@Override
	public boolean update(float time) {
		if (done == false && continueSteps > 0) {
			start(continueSteps);
		}
		return super.update(time);
	}

	@Override
	public boolean isOver() {

		return done;
	}

}
