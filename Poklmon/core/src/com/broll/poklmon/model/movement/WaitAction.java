package com.broll.poklmon.model.movement;

import com.broll.poklmon.model.OverworldCharacter;

public class WaitAction extends PathAction {

	private float time;
	private float min, max;
	private boolean random = false;

	public WaitAction(float time) {
		this.time = time;
	}

	public WaitAction(float min, float max) {
		random = true;
		this.max = max;
		this.min = min;
	}

	@Override
	public void start(OverworldCharacter character, boolean blocking) {
		if (random) {
			time = (float) (Math.random() * (max - min)) + min;
		}
		startTimer(time);
	}

	@Override
	public boolean isOver() {
		return isTimerDone();
	}

}
