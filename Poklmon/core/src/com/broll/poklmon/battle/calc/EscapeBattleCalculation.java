package com.broll.poklmon.battle.calc;

import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.BattleRandom;

public class EscapeBattleCalculation {

	// see http://bulbapedia.bulbagarden.net/wiki/Escape
	public static boolean canEscape(FightPoklmon player, FightPoklmon enemy, int retries) {
		double A = FightPoklmonParameterCalc.getInitiativeBase(player);
		double B = FightPoklmonParameterCalc.getInitiativeBase(player);
		double C = retries;

		double F = ((A * 128) / B + 30 * C) % 256;
		double chance = F / 255;
		return (chance >= BattleRandom.random());
	}

}
