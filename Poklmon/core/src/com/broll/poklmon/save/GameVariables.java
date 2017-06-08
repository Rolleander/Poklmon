package com.broll.poklmon.save;

import java.util.HashMap;

public class GameVariables {

	/**
     * 
     */
	private HashMap<String, Integer> integers= new HashMap<String, Integer>() ;
	private HashMap<String, Boolean> booleans=new HashMap<String, Boolean>();
	private HashMap<String, String> strings=new HashMap<String, String>();
	private HashMap<String, Float> floats=new HashMap<String, Float>();
	private HashMap<Integer, KnownPeopleInMap> knownPeople = new HashMap<Integer, KnownPeopleInMap>();
	private PokldexData pokldex = new PokldexData();
	private InventarData inventar = new InventarData();
	private int playTime;

	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	public InventarData getInventar() {
		return inventar;
	}

	public PokldexData getPokldex() {
		return pokldex;
	}

	public HashMap<String, Integer> getIntegers() {
		return integers;
	}

	public void setIntegers(HashMap<String, Integer> integers) {
		this.integers = integers;
	}

	public HashMap<String, Boolean> getBooleans() {
		return booleans;
	}

	public void setBooleans(HashMap<String, Boolean> booleans) {
		this.booleans = booleans;
	}

	public HashMap<String, String> getStrings() {
		return strings;
	}

	public void setStrings(HashMap<String, String> strings) {
		this.strings = strings;
	}

	public HashMap<String, Float> getFloats() {
		return floats;
	}

	public void setFloats(HashMap<String, Float> floats) {
		this.floats = floats;
	}

	public HashMap<Integer, KnownPeopleInMap> getKnownPeople() {
		return knownPeople;
	}
}
