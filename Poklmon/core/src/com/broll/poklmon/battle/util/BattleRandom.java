package com.broll.poklmon.battle.util;

import java.util.Random;

public class BattleRandom {

	private static Random random;

	public static void init() {
		random = new Random();
	}

	public static void init(long seed) {
		random = new Random(seed);
	}

	public static double random() {
		return random.nextDouble();
	}

	public static float randomFloat() {
		return random.nextFloat();
	}

	public static Random getRandom() {
		return random;
	}

}
