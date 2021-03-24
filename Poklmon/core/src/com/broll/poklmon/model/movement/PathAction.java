package com.broll.poklmon.model.movement;

import com.broll.poklmon.model.OverworldCharacter;

public abstract class PathAction {

	protected float timeRemaining;

	protected void startTimer(float time) {
		timeRemaining = time;
	}

	private void updateTimer(float time) {
		timeRemaining -= time;
	}

	protected boolean isTimerDone() {
		return timeRemaining <= 0;
	}

	protected abstract void start(OverworldCharacter character, boolean blocking);

	protected abstract boolean isOver();

	public boolean update(float time) {
		updateTimer(time);
		return isOver();
	}

}
