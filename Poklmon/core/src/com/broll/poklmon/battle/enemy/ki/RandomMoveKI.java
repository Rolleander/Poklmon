package com.broll.poklmon.battle.enemy.ki;

import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.enemy.EnemyKIProcess;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.BattleMove;

public class RandomMoveKI extends EnemyKIProcess {



	public RandomMoveKI(BattleProcessCore core, BattleParticipants participants) {
		super(core, participants);
		
	}

	@Override
	public FightPoklmon sendNextPoklmon() {
		return findBestPoklmonToSwap();
	}

	@Override
	public BattleMove process() {
		return new BattleMove(getRandomAttack());
	}

}
