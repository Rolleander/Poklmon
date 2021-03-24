package com.broll.poklmon.battle.item;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.game.items.PoklballItemScript;

public class PoklballItemRunner extends BattleProcessControl implements PoklballItemScript {

	private float ballStrength;
	private boolean catchAlways;
	private int ballIcon;

	public PoklballItemRunner(BattleManager manager, BattleProcessCore handler) {
		super(manager, handler);
	}

	public void init() {
		ballIcon=0;
		ballStrength = 1;
		catchAlways = false;
	}

	@Override
	public void setBallStrength(float strength) {
		ballStrength = strength;
	}

	@Override
	public void setCatchAlways(boolean always) {
		catchAlways = always;
	}

	@Override
	public WildPoklmon getTarget() {
		return (WildPoklmon) manager.getParticipants().getEnemy();
	}

	@Override
	public PlayerPoklmon getActivePoklmon() {
		return (PlayerPoklmon) manager.getParticipants().getPlayer();
	}

	public float getBallStrength() {
		return ballStrength;
	}

	public boolean isCatchAlways() {
		return catchAlways;
	}

	@Override
	public void setBallIcon(int nr) {
		this.ballIcon=nr;
	}
	
	public int getBallIcon() {
		return ballIcon;
	}

}
