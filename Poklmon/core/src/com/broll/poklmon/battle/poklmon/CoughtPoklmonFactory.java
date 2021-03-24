package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.poklmon.EXPCalculator;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

public class CoughtPoklmonFactory {

	public static PoklmonData caughtPoklmon(DataContainer data, Player player, WildPoklmon wildPoklmon) {
		PoklmonData pd = new PoklmonData();
		int poklmonID = wildPoklmon.getPoklmon().getId();
		Poklmon poklmon = data.getPoklmons().getPoklmon(poklmonID);
		int level = wildPoklmon.getLevel();
		pd.setLevel(level);
		int exp = EXPCalculator.calcEXP(poklmon.getExpLearnType(), level);
		pd.setExp(exp);
		// random genes
		short[] dv = wildPoklmon.getDv();
		short[] fp = new short[6];
		pd.setDv(dv);
		pd.setFp(fp);
		// full kp
		pd.setKp(PoklmonAttributeCalculator.getKP(poklmon, level, dv[0], fp[0]));
		pd.setDateCaught(CaughtPoklmonMeasurement.getCaughtDayInfo());
		AttackData[] atkData = new AttackData[4];
		FightAttack[] atks = wildPoklmon.getAttacks();

		for (int i = 0; i < 4; i++) {
			if (atks[i] != null) {
				atkData[i] = new AttackData(atks[i].getAttack().getId(), (byte) atks[i].getAp());
			} else {
				atkData[i] = new AttackData(-1, (byte) 0);
			}
		}

		pd.setAttacks(atkData);
		pd.setLevelCaught(wildPoklmon.getLevel());
		pd.setPoklball(0);
		pd.setPoklmon(poklmonID);
		pd.setWesen(wildPoklmon.getWesen());
		pd.setName(null);
		pd.setCarryItem(-1);
		return pd;
	}

}
