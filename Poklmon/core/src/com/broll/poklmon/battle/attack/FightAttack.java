package com.broll.poklmon.battle.attack;

import com.broll.pokllib.attack.Attack;

public class FightAttack {

	private Attack attack;
	private int ap;
	
	public FightAttack() {
	}
	
	public FightAttack(Attack attack, int ap)
	{
	    this.attack=attack;
	    this.ap=ap;
	}
	
	public FightAttack(Attack attack)
	{
	    this.attack=attack;
	    this.ap=attack.getDamage().getAp();
	}
	
	public void use()
	{
	    ap-=1;
	}
	
	public int getAp() {
		return ap;
	}
	
	public Attack getAttack() {
		return attack;
	}

	public void setAp(byte newAp) {
		this.ap=newAp;
	}
}
