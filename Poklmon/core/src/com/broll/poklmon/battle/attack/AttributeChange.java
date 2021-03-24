package com.broll.poklmon.battle.attack;


public class AttributeChange {

	private int strength;
	private AttackAttributePlus attribute;
	
	public AttributeChange(AttackAttributePlus type, int strength)
	{
		this.attribute=type;
		this.strength=strength;	
	}
	
	public AttackAttributePlus getAttribute() {
		return attribute;
	}
	
	public int getStrength() {
		return strength;
	}
	
}
