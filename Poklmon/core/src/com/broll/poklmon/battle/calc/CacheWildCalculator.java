package com.broll.poklmon.battle.calc;

import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.util.BattleRandom;

public class CacheWildCalculator {
	private static int wobble;
	
	public static boolean cacheWildPoklmon(FightPoklmon poklmon, float ballPower) {
		float kpMax = poklmon.getAttributes().getMaxhealth();
		float kp = poklmon.getAttributes().getHealth();
		float ff = poklmon.getPoklmon().getCatchRate();

		float statev = getStateBonus(poklmon.getStatusChanges());

		float x = (((3 * kpMax - 2 * kp) * ff * ballPower) / (3 * kpMax)) * statev;
		float chance = x / 255f;
		//umso eher gefangen => länger wackeln
		wobble=(int) (BattleRandom.random()*2+(((chance)/255)*4))+1;
		
		return (chance >= BattleRandom.random());
	}
	
	public static int getWobbleCount() {
		return wobble;
	}

	private static float getStateBonus(PoklmonStatusChanges poklmonStatusChanges) {
		if (poklmonStatusChanges.hasMainStateChangeEffect()) {
			switch (poklmonStatusChanges.getMainStatus()) {
			case SLEEPING: 
			case ICE:
				return 2.5f;
			case PARALYZE:
			case BURNING:
			case POISON:
			case TOXIN:
				return 1.5f;
			default:
				break;
			}
		}
		return 1;
	}

}
