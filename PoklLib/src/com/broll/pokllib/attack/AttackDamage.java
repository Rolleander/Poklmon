package com.broll.pokllib.attack;

public class AttackDamage {

	private int damage;
	private AttackPriority priority;
	private float hitchance;
	private int ap;


	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setPriority(AttackPriority priority)
    {
        this.priority = priority;
    }
	
	public AttackPriority getPriority()
    {
        return priority;
    }
	
	public float getHitchance() {
		return hitchance;
	}

	public void setHitchance(float hitchance) {
		this.hitchance = hitchance;
	}

	public int getAp() {
		return ap;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}


}
