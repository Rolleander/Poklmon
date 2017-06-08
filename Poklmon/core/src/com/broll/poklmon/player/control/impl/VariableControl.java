package com.broll.poklmon.player.control.impl;

import com.broll.poklmon.player.PlayerGameData;
import com.broll.poklmon.player.control.VariableControlInterface;
import com.broll.poklmon.save.GameVariables;
import com.broll.poklmon.save.KnownPeopleInMap;

public class VariableControl implements VariableControlInterface {

	private GameVariables variables;

	public VariableControl(PlayerGameData data) {
		variables = data.getGameVariables();

	}

	@Override
	public boolean isKnownobject(int mapNr, int objectNr) {
		if (variables.getKnownPeople().containsKey(mapNr)) {
			KnownPeopleInMap known = variables.getKnownPeople().get(mapNr);
			return known.getIsKnown().contains(objectNr);
		}
		return false;
	}

	@Override
	public void setObjectToKnown(int mapNr, int objectNr, boolean isKnown) {
		if (variables.getKnownPeople().containsKey(mapNr)) {
			KnownPeopleInMap known = variables.getKnownPeople().get(mapNr);
			if (isKnown) {
				// add to list
				if (!known.getIsKnown().contains(objectNr)) {
					known.getIsKnown().add(objectNr);
				}
			} else {
				// remove from list
				int index = known.getIsKnown().indexOf(objectNr);
				if (index != -1) {
					known.getIsKnown().remove(index);
				}
			}
		} else {
			// add new map
			if (isKnown) {
				KnownPeopleInMap knownPeopleInMap = new KnownPeopleInMap();
				knownPeopleInMap.getIsKnown().add(objectNr);
				variables.getKnownPeople().put(mapNr, knownPeopleInMap);
			}
		}
	}

	@Override
	public boolean getBoolean(String no) {
		if (variables.getBooleans().containsKey(no)) {
			return variables.getBooleans().get(no);
		}
		return false;
	}

	@Override
	public int getInt(String no) {
		if (variables.getIntegers().containsKey(no)) {
			return variables.getIntegers().get(no);
		}
		return 0;
	}

	@Override
	public float getFloat(String no) {
		if (variables.getFloats().containsKey(no)) {
			return variables.getFloats().get(no);
		}
		return 0;
	}

	@Override
	public String getString(String no) {
		if (variables.getStrings().containsKey(no)) {
			return variables.getStrings().get(no);
		}
		return null;
	}

	@Override
	public void setBoolean(String no, boolean value) {
		variables.getBooleans().put(no, value);
	}

	@Override
	public void setInt(String no, int value) {
		variables.getIntegers().put(no, value);
	}

	@Override
	public void setFloat(String no, float value) {
		variables.getFloats().put(no, value);
	}

	@Override
	public void setString(String no, String value) {
		variables.getStrings().put(no, value);
	}

}
