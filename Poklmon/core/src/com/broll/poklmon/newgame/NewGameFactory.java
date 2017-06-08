package com.broll.poklmon.newgame;

import java.util.HashMap;
import java.util.List;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.EXPCalculator;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.GameVariables;
import com.broll.poklmon.save.PlayerData;
import com.broll.poklmon.save.PoklmonData;

public class NewGameFactory {

	public static PlayerData createNewPlayer(int character, String name) {
		PlayerData pd = new PlayerData();
		pd.setCharacter(character);
		
		pd.setName(name);
		pd.setView(ObjectDirection.UP.ordinal());
		
		// start map and position
		pd.setMapNr(1);
		pd.setXpos(6);
		pd.setYpos(4);
		return pd;
	}

	public static GameVariables initGameVariables() {
		GameVariables gv = new GameVariables();
		gv.setBooleans(new HashMap<String, Boolean>());
		gv.setFloats(new HashMap<String, Float>());
		gv.setIntegers(new HashMap<String, Integer>());
		gv.setStrings(new HashMap<String, String>());
		return gv;
	}

	public static PoklmonData createStarterPoklmon(DataContainer data, int poklmonID, String name) {
		PoklmonData pd = new PoklmonData();
		Poklmon poklmon = data.getPoklmons().getPoklmon(poklmonID);
		int level = 5;
		pd.setLevel(level);
		int exp = EXPCalculator.calcEXP(poklmon.getExpLearnType(), level);
		pd.setExp(exp);
		// random genes
		short[] dv = CaughtPoklmonMeasurement.generateRandomDVs();
		short[] fp = new short[6];
		pd.setDv(dv);
		pd.setFp(fp);
		// full kp
		pd.setKp(PoklmonAttributeCalculator.getKP(poklmon, level, dv[0], fp[0]));
		pd.setDateCaught(CaughtPoklmonMeasurement.getCaughtDayInfo());
		// learn first attacks
		List<AttackLearnEntry> atks = poklmon.getAttackList().getAttacks();
		int place = 0;
		AttackData[] atkData = new AttackData[4];
		for(int i=0; i<4; i++){
			atkData[i]=new AttackData(-1, (byte)0);
		}
		for (AttackLearnEntry atk : atks) {
			if (atk.getLearnLevel() <= level) {
				int ap = data.getAttacks().getAttack(atk.getAttackNumber()).getDamage().getAp();
				atkData[place] = new AttackData(atk.getAttackNumber(), (byte) ap);
				place++;
				if (place == 4) {
					break;
				}
			}
		}
		pd.setAttacks(atkData);
		pd.setLevelCaught(5);
		pd.setPoklball(0);
		pd.setPoklmon(poklmonID);
		pd.setTeamPlace(0);
		pd.setWesen(CaughtPoklmonMeasurement.getRandomWesen());
		pd.setName(name);
		pd.setCarryItem(-1);
		return pd;
	}
}
