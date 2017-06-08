package com.broll.poklmon.battle.item;

import java.util.HashMap;

import com.broll.poklmon.battle.poklmon.FightPoklmon;

public class WearableItemVariables {

	private HashMap<FightPoklmon, ItemVariables> variables = new HashMap<FightPoklmon, ItemVariables>();

	public WearableItemVariables() {

	}

	private ItemVariables get(FightPoklmon carrier) {
		if (!variables.containsKey(carrier)) {
			variables.put(carrier, new ItemVariables());
		}
		return variables.get(carrier);
	}

	public int getInt(FightPoklmon carrier) {
		return get(carrier).valueInt;
	}

	public float getFloat(FightPoklmon carrier) {
		return get(carrier).valueFloat;
	}

	public boolean getBool(FightPoklmon carrier) {
		return get(carrier).valueBoolean;
	}

	public void setInt(FightPoklmon carrier, int value) {
		get(carrier).valueInt = value;
	}

	public void setFloat(FightPoklmon carrier, float value) {
		get(carrier).valueFloat = value;
	}

	public void setBool(FightPoklmon carrier, boolean value) {
		get(carrier).valueBoolean = value;
	}

	private class ItemVariables {
		public int valueInt;
		public float valueFloat;
		public boolean valueBoolean;
	}
}
