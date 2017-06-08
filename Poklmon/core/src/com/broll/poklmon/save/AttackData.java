package com.broll.poklmon.save;


public class AttackData  {

  public static int NO_ATTACK=-1;
    private int attack;
	private byte ap;
	
	public AttackData() {
		this.ap=0;
		this.attack=NO_ATTACK;
	}
	
	public AttackData(int attack, byte ap)
	{
		this.attack=attack;
		this.ap=ap;
	}
	
	public byte getAp() {
		return ap;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public void setAp(byte ap)
    {
        this.ap = ap;
    }
	
	public void setAttack(int attack)
    {
        this.attack = attack;
    }
}
