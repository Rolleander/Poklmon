package com.broll.poklmon.battle.process;

import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.network.transfer.BattleMoveTransfer;

public class NetworkUtil {

	public static BattleMove convert(BattleParticipants participants, BattleMoveTransfer transfer) {
		int switchto = transfer.switchTo;
		int attack = transfer.useAttack;
		if (switchto > -1) {
			return new BattleMove(participants.getEnemyTeam().get(switchto));
		} else if (attack > -1) {
			if (attack == 5) {
				// verzweifler
				return new BattleMove(StateEffectCalc.getDesperationAttack());
			}
			return new BattleMove(participants.getEnemy().getAttacks()[attack]);
		}
		return null;
	}

	public static BattleMoveTransfer convert(BattleParticipants participants, BattleMove move) {
		BattleMoveTransfer transfer = new BattleMoveTransfer();
		FightAttack atk = move.getAttack();
		if (atk != null) {
			int nr = 5; // default -> verzweifler
			for (int i = 0; i < 4; i++) {
				if (atk == participants.getPlayer().getAttacks()[i]) {
					nr = i;
				}
			}
			transfer.useAttack = nr;
		}
		FightPoklmon switchto = move.getSwitchTo();
		if (switchto != null) {
			int nr = 0;
			for (int i = 0; i < participants.getPlayerTeam().size(); i++) {
				if (participants.getPlayerTeam().get(i) == switchto) {
					nr = i;
				}
			}
			transfer.switchTo = nr;
		}
		return transfer;
	}

}
