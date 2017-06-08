package com.broll.poklmon.battle.enemy.ki;

import com.broll.pokllib.attack.AttackType;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.enemy.EnemyKIProcess;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.BattleMove;

public class SmartTrainerKI extends EnemyKIProcess {

	public SmartTrainerKI(BattleProcessCore core, BattleParticipants participants) {
		super(core, participants);
		
	}

	private final static float HEAL_USE = 0.5f;
	private final static float SWITCH_USE = 0.25f;
	private final static float SWAP_CHANCE = 0.7f;
	private final static float ITEM_CHANCE = 0.7f;
	private final static float STRONG_ATTACK_CHANCE = 0.7f;
	

	

	@Override
	protected BattleMove process() {
		// default move = random attack
		BattleMove move = new BattleMove(getRandomAttack());

		float health = poklmon.getAttributes().getHealthPercent();
		boolean found = false;

		if (hasItems()) {
			if (health <= HEAL_USE) {
				// maybe heal with item
				if (chance(ITEM_CHANCE)) {
					move = new BattleMove(poklmon, getNextItemInList());
					found = true;
				}
			}

		} else {
			if (health <= SWITCH_USE) {
				// maybe switch
				if (chance(SWAP_CHANCE)) {
				
					FightPoklmon swap = findSwapTarget(SWITCH_USE*1.1f);
					if (swap != null) {
						move = new BattleMove(swap);
						found = true;
					}
				}
			}
		}

		if (!found) {
			// maybe strong attack
			if (chance(STRONG_ATTACK_CHANCE)) {
				FightAttack atk = findStrongAttack();
				if (atk != null) {
					move = new BattleMove(atk);
				}
			}
		}

		return move;
	}

	// no status attacks!
	private FightAttack findStrongAttack() {
		FightAttack attack = null;
		double power = -1;
		for (FightAttack atk : poklmon.getAttacks()) {
			if (atk != null) {
				if (atk.getAp() > 0 && atk.getAttack().getAttackType() != AttackType.STATUS) {
					double p = calcElementPower(atk, participants.getPlayer());
					if (p > power) {
						attack = atk;
						power = p;
						// sometimes give random atk
						if (chance(0.05f)) {
							return atk;
						}
					}
				}
			}
		}
		return attack;
	}

	@Override
	public FightPoklmon sendNextPoklmon() {
		return findSwapTarget(0);
	}

}
