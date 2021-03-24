package com.broll.poklmon.map.areas.util;

import com.broll.poklmon.map.areas.MapArea;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WildPoklmonOccurCalc {

	public static int NO_POKLMON_OCCURED = -1;

	private Random random;

	public WildPoklmonOccurCalc() {
		random = new Random();
	}

	public RandomBattle calc(MapArea area) {
		return calc(area.getWildPoklmons());
	}

	public RandomBattle calc(List<WildPoklmonEntry> entries) {
		int poklmonID = NO_POKLMON_OCCURED;
		int poklmonLevel = 0;
		double r = random.nextDouble();
		if (r < 0.3) {
			r = random.nextDouble();
			// check for poklmons
			double bot = 0;
			for (WildPoklmonEntry entry : entries) {
				double chance = entry.getChance();
				if (r >= bot && r < bot + chance) {
					poklmonID = entry.getPoklmonID();
					int min = entry.getMinLevel();
					int max = entry.getMaxLevel();
					poklmonLevel = getRandomLevel(min, max);
					break;
				}
				bot += chance;
			}
		}

		// randomize entries after
		Collections.shuffle(entries);
		if (poklmonID == NO_POKLMON_OCCURED) {
			return null;
		}
		RandomBattle rb = new RandomBattle(poklmonID, poklmonLevel);
		return rb;
	}

	public int getRandomLevel(int min, int max) {
		double diff = (max + 1 - min);
		return (int) ((Math.random() * diff) + min);
	}

}
