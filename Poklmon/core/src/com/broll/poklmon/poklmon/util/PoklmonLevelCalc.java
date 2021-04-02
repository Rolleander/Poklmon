package com.broll.poklmon.poklmon.util;

import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.EXPCalculator;
import com.broll.pokllib.poklmon.EXPLearnTypes;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

import java.util.List;

public class PoklmonLevelCalc {

	private LevelCalcListener levelCalcListener;
	private DataContainer data;

	public PoklmonLevelCalc(DataContainer data) {
		this.data = data;
	}

	public void setLevelCalcListener(LevelCalcListener levelCalcListener) {
		this.levelCalcListener = levelCalcListener;
	}

	public int getExpToNextLevel(PoklmonData poklmon) {
		int level = poklmon.getLevel();
		int poklID = poklmon.getPoklmon();
		Poklmon pokl = data.getPoklmons().getPoklmon(poklID);
		EXPLearnTypes learnType = pokl.getExpLearnType();
		int exp = poklmon.getExp();
		int nextexp = EXPCalculator.calcEXP(learnType, level + 1);
		if (level == 100) {
			return 0;
		} else {
			return nextexp - exp;
		}
	}

	public void addEXP(PoklmonData poklmon, int earnEXP) {
		int level = poklmon.getLevel();
		int exp = poklmon.getExp();
		int poklID = poklmon.getPoklmon();
		Poklmon pokl = data.getPoklmons().getPoklmon(poklID);
		EXPLearnTypes learnType = pokl.getExpLearnType();

		exp += earnEXP;
		// set exp
		poklmon.setExp(exp);
		// check new levels
		int newl = level + 1;
		int xp;
		do {
			if (newl > 100) {
				break;
			}
			xp = EXPCalculator.calcEXP(learnType, newl);
			if (exp >= xp) {
				reachedLevel(poklmon, newl);
			}
			newl++;
		} while (exp >= xp);

	}

	private void reachedLevel(PoklmonData pd, int level) {
		pd.setLevel(level);
		levelCalcListener.newLevel(level);
		// check for new attacks
		Poklmon poklmon = data.getPoklmons().getPoklmon(pd.getPoklmon());
		List<AttackLearnEntry> attacks = poklmon.getAttackList().getAttacks();
		for (AttackLearnEntry atk : attacks) {
			if (level == atk.getLearnLevel()) {
				// check if atk is not learned
				int atkID = atk.getAttackNumber();
				AttackData[] poklAttacks = pd.getAttacks();
				boolean attackNotLearned = true;
				for (int i = 0; i < 4; i++) {
					if (poklAttacks[i].getAttack() == atkID) {
						attackNotLearned = false;
						break;
					}
				}

				if (attackNotLearned) {
					levelCalcListener.canLearnAttack(atkID);
				}
			}
		}

		// check for evolving
		int evolveTo = poklmon.getEvolveIntoPoklmon();
		if (evolveTo != -1) {
			int evolveLevel = poklmon.getEvolveLevel();
			if (level >= evolveLevel) {
				levelCalcListener.evolvesTo(evolveTo);
			}
		}
	}

}
