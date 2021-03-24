package com.broll.pokllib.poklmon;

public enum TypeCompare {

	STANDARD("",1f),EFFECTIVE("Das ist sehr effektiv!",2f),NOTEFFECTIVE("Das ist nicht sehr effektiv...",0.5f),NOEFFECT("Keine Wirkung!",0);
	
	private String name;
	private float multiplicator;
	
	private TypeCompare(String n, float m) {
		name=n;
		multiplicator=m;
	}
	
	public String getName() {
		return name;
	}
		
	public float getMultiplicator() {
		return multiplicator;
	}
}
