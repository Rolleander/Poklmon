package com.broll.poklmon.battle.poklmon.states;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;

public class StatusChangeCheck
{
    private static FieldEffects fieldEffects;
    
    public static void init(FieldEffects fieldEffects)
    {
        StatusChangeCheck.fieldEffects = fieldEffects;
    }

    public static boolean isStatusChangeAllowed(MainFightStatus status, FightPoklmon poklmon)
    {
        ElementType type1 = poklmon.getPoklmon().getBaseType();
        ElementType type2 = poklmon.getPoklmon().getSecondaryType();

        switch (status)
        {
            case BURNING: // fire poklmon cant burn
                if (type1 == ElementType.FIRE || type2 == ElementType.FIRE)
                {
                    return false;
                }
                //wenn es regnet kann niemand brennen
                if(fieldEffects.isWeatherEffect(WeatherEffect.RAIN))
                {
                    return false;
                }
                
                break;
            case ICE: // ice poklmon cant freeze
                if (type1 == ElementType.ICE || type2 == ElementType.ICE)
                {
                    return false;
                }
                
                //wenn die sonne scheint kann niemand einfrieren
                if(fieldEffects.isWeatherEffect(WeatherEffect.SUN))
                {
                    return false;
                }
                
                break;
            case POISON: // fallthrough to toxin
            case TOXIN: // poison pooklmon cant get poisoned
                if (type1 == ElementType.POISON || type2 == ElementType.POISON)
                {
                    return false;
                }
                //steel poklmon no posion
                if (type1 == ElementType.STEEL || type2 == ElementType.STEEL)
                {
                    return false;
                }
                break;
        }
        return true;
    }



}
