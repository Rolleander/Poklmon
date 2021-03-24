package com.broll.poklmon.battle.calc;

import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.PoklmonTeamEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;

public class AttackDamageBonus
{

    private static FieldEffects fieldEffects;
    
    public static void init(FieldEffects fieldEffects)
    {
        AttackDamageBonus.fieldEffects = fieldEffects;
    }
    
    
    public static int getAttackBonus(UseAttack attack, FightPoklmon user, FightPoklmon target, float damage)
    {
        
        damage=getElementBonus(attack.getElement(), damage);
        damage=getTypeBonus(attack.getType(),user,target, damage);
        
        return (int)damage;
    }
    
    private static int getTypeBonus(AttackType type, FightPoklmon user, FightPoklmon target, float damage)
    {
    	//wenn target schild hat angriff verringern
		PoklmonTeamEffect targetTeamEffects = fieldEffects.getTeamEffects(target);
    
		if(type==AttackType.PHYSICAL)
    	{  
    		if(targetTeamEffects.hasTeamEffect(TeamEffect.REFLECTOR))
    		{
    			damage/=2;
    		}
    	}
		
		if(type==AttackType.SPECIAL)
		{
			if(targetTeamEffects.hasTeamEffect(TeamEffect.ENERGYBLOCK))
    		{
				damage/=2;
    		}
		}
    	return (int)damage;
    }
    
    private static int getElementBonus(ElementType attackType, float damage)
    {
        //check weather effects
        if(fieldEffects.isWeatherEffect(WeatherEffect.RAIN)){
            if(attackType==ElementType.WATER)
            {
                damage*=1.5;
            }
            else if(attackType==ElementType.FIRE)
            {
                damage*=0.5;
            }
        }
        else if(fieldEffects.isWeatherEffect(WeatherEffect.SUN))
        {
            if(attackType==ElementType.WATER)
            {
                damage*=0.5;
            }
            else if(attackType==ElementType.FIRE)
            {
                damage*=1.5;
            }
        }
        else if(fieldEffects.isWeatherEffect(WeatherEffect.STORM))
        {
            if(attackType==ElementType.ELECTRO)
            {
                damage*=1.5;
            }
        }
        return (int)damage;
    }
    
}
