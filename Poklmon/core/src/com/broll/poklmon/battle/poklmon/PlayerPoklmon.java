package com.broll.poklmon.battle.poklmon;

import java.util.ArrayList;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.EXPCalculator;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.save.PoklmonData;

public class PlayerPoklmon extends FightPoklmon {

	private int exp, minExp, maxExp;
	private ArrayList<FightPoklmon> expSources = new ArrayList<FightPoklmon>();
	private PoklmonData poklmonData;
	private boolean usedInBattle = false;

	public PlayerPoklmon(PoklmonData poklmon) {
		this.poklmonData = poklmon;
	}

	@Override
	public void setCarryItem(int carryItem) {
		super.setCarryItem(carryItem);
		poklmonData.setCarryItem(carryItem);
	}

	@Override
	public void setMainStatus(MainFightStatus mainStatus) {
		super.setMainStatus(mainStatus);
		poklmonData.setStatus(mainStatus);
	}

	@Override
	public void healMainStatus() {
		super.healMainStatus();
		poklmonData.setStatus(statusChanges.getMainStatus());
	}

	public void setUsedInBattle(boolean usedInBattle) {
		this.usedInBattle = usedInBattle;
	}

	public boolean isUsedInBattle() {
		return usedInBattle;
	}

	public void gainExp(int exp) {

	}

	@Override
	protected void updateHealth(int kp) {
		super.updateHealth(kp);
		poklmonData.setKp(kp);
	}

	public void setAttack(Attack attack, int place) {
		attacks[place] = new FightAttack(attack);
	}

	@Override
	public void useAttack(int nr) {
		super.useAttack(nr);
		byte ap = (byte) attacks[nr].getAp();
		poklmonData.getAttacks()[nr].setAp(ap);
	}

	public PoklmonData getPoklmonData() {
		return poklmonData;
	}

	public void addEXPSource(FightPoklmon poklmon) {
		if (!expSources.contains(poklmon)) {
			expSources.add(poklmon);
		}
	}

	public ArrayList<FightPoklmon> getExpSources() {
		return expSources;
	}

	public float getEXPPercent() {
		float p = (float) (exp - minExp) / (float) (maxExp - minExp);
		if (p < 0) {
			p = 0;
		} else if (p > 1) {
			p = 1;
		}
		return p;
	}

	public int getExp() {
		return exp;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public int getMinExp() {
		return minExp;
	}

	public void updateExp() {
		this.exp = poklmonData.getExp();
		maxExp = EXPCalculator.calcEXP(poklmon.getExpLearnType(), poklmonData.getLevel() + 1);
		minExp = EXPCalculator.calcEXP(poklmon.getExpLearnType(), poklmonData.getLevel());
		this.level = poklmonData.getLevel();
	}

}
