package com.broll.pokleditor.pokldex;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.AttackList;
import com.broll.pokllib.poklmon.EXPLearnTypes;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonAttributes;

public class PoklmonGenerator {

	
	public static Poklmon newPoklmon()
	{
		Poklmon poklmon=new Poklmon();
		
		poklmon.setName("New Poklmon");
		poklmon.setBaseType(ElementType.NORMAL);
		poklmon.setEvolveIntoPoklmon(-1);
		poklmon.setEvolveLevel(0);
		poklmon.setExpBasePoints(getBaseValue()+30);
		poklmon.setExpLearnType(EXPLearnTypes.MIDDLE_FAST);
		poklmon.setPokldexNumber(0);
		poklmon.setSecondaryType(null);
		poklmon.setCatchRate(getMaxValue());
		poklmon.setGraphicName("todo.png");
		poklmon.setDescription("#TODO");
		
		PoklmonAttributes attributes=new PoklmonAttributes();
		attributes.setBaseKP(getBaseValue()+10);
		attributes.setBaseAttack(getBaseValue());
		attributes.setBaseDefence(getBaseValue());
		attributes.setBaseInitiative(getBaseValue());
		attributes.setBaseSpecialAttack(getBaseValue());
		attributes.setBaseSpecialDefence(getBaseValue());
		
		poklmon.setAttributes(attributes);	
		
		AttackList attackList=new AttackList();
		List<AttackLearnEntry> attacks=new ArrayList<AttackLearnEntry>();
		
		attackList.setAttacks(attacks);
		poklmon.setAttackList(attackList);
		return poklmon;
	}
	
	private static int getBaseValue()
	{
		return (int) (35+Math.random()*20);
	}
	
	private static int getMaxValue()
	{
		return (int) (150+Math.random()*50);
	}
}
