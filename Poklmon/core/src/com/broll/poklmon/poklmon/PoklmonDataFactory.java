package com.broll.poklmon.poklmon;

import com.broll.pokllib.poklmon.EXPCalculator;
import com.broll.pokllib.poklmon.EXPLearnTypes;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

public class PoklmonDataFactory {

	private DataContainer data;

	public PoklmonDataFactory(DataContainer data) {
		this.data = data;
	}
	
	public PoklmonData getPoklmon(WildPoklmon poklmon) {
		PoklmonData pd = new PoklmonData();
		pd.setDateCaught(CaughtPoklmonMeasurement.getCaughtDayInfo());
		pd.setDv(poklmon.getDv());
		
		int level = poklmon.getLevel();
		pd.setLevel(level);
		pd.setLevelCaught(level);

		int id = data.getPoklmons().getPoklmonID(poklmon.getPoklmon());
		pd.setPoklmon(id);

		short[] fp = { 0, 0, 0, 0, 0, 0 };
		pd.setFp(fp);
		pd.setKp(poklmon.getAttributes().getHealth());
		pd.setName(null);
		pd.setPoklball(0);
		pd.setWesen(poklmon.getWesen());
		calcEXP(pd);
		calcAttacks(pd, poklmon.getAttacks());
		return pd;
	}

	private void calcEXP(PoklmonData pd) {

		int level = pd.getLevel();
		EXPLearnTypes type = data.getPoklmons().getPoklmon(pd.getPoklmon()).getExpLearnType();
		int minexp = EXPCalculator.calcEXP(type, level);
		int maxexp = EXPCalculator.calcEXP(type, level+1)-1;
        //int exp=minexp;
        int exp=(int)(minexp+(maxexp-minexp)*Math.random());
		pd.setExp(exp);
	}

	private void calcAttacks(PoklmonData pd, FightAttack[] attacks) {
		AttackData[] ad = new AttackData[4];
		for (int i = 0; i < 4; i++) {
			int attack = -1;
			byte ap = 0;
			FightAttack atk = attacks[i];
			if (atk != null) {
				ap = (byte) atk.getAp();
				attack = data.getAttacks().getAttackID(atk.getAttack());
			}
			ad[i] = new AttackData(attack, ap);
		}
		pd.setAttacks(ad);
	}

}
