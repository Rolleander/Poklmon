package com.broll.poklmon.battle.enemy;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.TypeCompare;
import com.broll.pokllib.poklmon.TypeComperator;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.item.TrainerItem;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.battle.util.BattleRandom;
import com.broll.poklmon.battle.util.PoklmonTeamCheck;
import com.broll.poklmon.network.NetworkException;

public abstract class EnemyKIProcess {

	protected boolean isTrainerKI;
	protected BattleParticipants participants;
	protected FightPoklmon poklmon;
	protected BattleProcessCore core;

	public EnemyKIProcess(BattleProcessCore core,BattleParticipants participants) {
		this.participants = participants;
		this.core=core;
		this.isTrainerKI = participants.isTrainerFight();
		poklmon = participants.getEnemy();
	}

	public void update() {
		poklmon = participants.getEnemy();
	}

	public BattleMove processMove() throws NetworkException {
		// check if no attacks
		// no attack with more than 0 ap => use desperate attack
		if (!hasAttacks(poklmon) || hasNoApLeft()) {
			// use verzweiflung
			return new BattleMove(StateEffectCalc.getDesperationAttack());
		} else {
			return this.process();
		}
	}

	protected boolean hasAttacks(FightPoklmon poklmon) {
		boolean hasAttack = false;
		for (FightAttack fa : poklmon.getAttacks()) {
			if (fa != null) {
				hasAttack = true;
				break;
			}
		}
		return hasAttack;
	}

	protected abstract BattleMove process();

	public abstract FightPoklmon sendNextPoklmon() throws NetworkException;

	public boolean chance(float chance) {
		return BattleRandom.random() <= chance;
	}

	public boolean hasNoApLeft() {
		return poklmon.noApLeft();
	}

	public int getNextItemInList() {
		return participants.getTrainerItems().get(0).getId();
	}

	public boolean hasItems() {
		return participants.getTrainerItems().size() > 0;
	}

	public boolean hasItem(int itemId) {
		for(TrainerItem item: participants.getTrainerItems()){
			if(item.getId()==itemId){
				return true;
			}
		}
		return false;
	}

	public boolean isTrainerKI() {
		return isTrainerKI;
	}

	public boolean hasPoklmonsToChange() {
		return PoklmonTeamCheck.hasPoklmonsToChange(participants.getEnemyTeam());
	}

	public FightAttack getRandomAttack() {
		// find random attack with ap >0
		while (true) {
			int r = (int) (BattleRandom.random() * 4);
			FightAttack a = poklmon.getAttacks()[r];
			if (a != null) {
				if (a.getAp() > 0) {
					return a;
				}
			}
		}
	}

	// find best swap target with given min-KP
	protected FightPoklmon findSwapTarget(float minKp) {
		FightPoklmon swapPoklmon = null;
		double elementPower = -1;

		for (FightPoklmon p : participants.getEnemyTeam()) {
			if (!p.isFainted() && p.getAttributes().getHealthPercent() >= minKp && p != participants.getEnemy()) {
				double power = calcElementPower(p, participants.getPlayer());
				if (power > elementPower) {
					elementPower = power;
					swapPoklmon = p;
				}
			}
		}
		return swapPoklmon;
	}

	public FightPoklmon findBestPoklmonToSwap() {
		return findSwapTarget(0);
	}

	public double calcElementPower(FightPoklmon user, FightPoklmon target) {
		double elementPower = 1;

		Poklmon userPokl = user.getPoklmon();
		Poklmon enemyPokl = target.getPoklmon();
		ElementType userBaseType = userPokl.getBaseType();
		ElementType userSecondType = userPokl.getSecondaryType();
		ElementType enemyBaseType = enemyPokl.getBaseType();
		ElementType enemySecondType = enemyPokl.getSecondaryType();

		// compare base base
		TypeCompare comp = TypeComperator.getTypeCompare(userBaseType, enemyBaseType);
		elementPower = comp.getMultiplicator();

		// compare base to second
		if (enemySecondType != null) {
			comp = TypeComperator.getTypeCompare(userBaseType, enemySecondType);
			elementPower *= comp.getMultiplicator();
		}

		if (userSecondType != null) {
			// compare second to base
			comp = TypeComperator.getTypeCompare(userSecondType, enemyBaseType);
			elementPower *= comp.getMultiplicator();

			// compare second to second
			if (enemySecondType != null) {
				comp = TypeComperator.getTypeCompare(userSecondType, enemySecondType);
				elementPower *= comp.getMultiplicator();
			}
		}
		return elementPower;
	}

	public double calcElementPower(FightAttack attack, FightPoklmon target) {
		double power = 1;
		ElementType attackElement = attack.getAttack().getElementType();
		ElementType baseElement = target.getPoklmon().getBaseType();
		ElementType secondElement = target.getPoklmon().getSecondaryType();
		power = TypeComperator.getTypeCompare(attackElement, baseElement).getMultiplicator();
		if (secondElement != null) {
			power *= TypeComperator.getTypeCompare(attackElement, secondElement).getMultiplicator();
		}
		return power;
	}

	public boolean canEscape() {
		return isTrainerKI == false;
	}

	public boolean rand(double chance) {
		return BattleRandom.random() <= chance;
	}

}
