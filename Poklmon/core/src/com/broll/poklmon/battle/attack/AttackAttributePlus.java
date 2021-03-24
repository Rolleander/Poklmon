package com.broll.poklmon.battle.attack;

public enum AttackAttributePlus {

	ATTACK("Der Angriff"),DEFENCE("Die Verteidigung"),INITIATIVE("Die Initiative"),FLUCHTWERT("Der Fluchtwert"),
	GENAUIGKEIT("Die Genauigkeit"),SPECIALATTACK("Der Sepzial-Angriff"),SPECIALDEFENCE("Die Spezial-Verteidigung");
	
	
	private String displayName;
	
	private AttackAttributePlus(String disp)
	{
		displayName=disp;
	}
	

	
	public String getDisplayName()
    {
        return displayName;
    }
}
