package com.broll.poklmon.battle.enemy;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.enemy.ki.RandomMoveKI;
import com.broll.poklmon.battle.enemy.ki.SmartTrainerKI;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.network.NetworkException;

public class EnemyMoveSelection {

	private BattleMove nextMove;
	private BattleManager battle;
	private EnemyKIProcess kiProcess;

	public EnemyMoveSelection(BattleManager battleManager) {
		this.battle = battleManager;
	}

	public void initKI(BattleProcessCore core,BattleParticipants participants) {
		switch (participants.getEnemyKI()) {
		case RANDOM:
			kiProcess = new RandomMoveKI(core,participants);
			break;
		case TRAINER:
			kiProcess = new SmartTrainerKI(core,participants);
			break;
		}
		if(core.isNetworkBattle()){
			kiProcess = new NetworkPlayer(core,participants);
		}
	}

	public void processKI() throws NetworkException {
		nextMove = null;
		kiProcess.update();
	
		// calc next move
		nextMove = kiProcess.processMove();
	}

	public FightPoklmon sendNextPoklmon() throws NetworkException {
		return kiProcess.sendNextPoklmon();
	}

	public BattleMove getNextMove() {
		return nextMove;
	}
}
