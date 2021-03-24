package com.broll.poklmon.map.areas.util;

public class RandomBattle {

	private int poklmon,level;
	
	public RandomBattle(int id, int level) {
		this.poklmon=id;
		this.level=level;
	}
	
	public int getPoklmon() {
		return poklmon;
	}
	
	public int getLevel() {
		return level;
	}
}
