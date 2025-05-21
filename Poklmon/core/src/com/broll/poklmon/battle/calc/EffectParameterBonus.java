package com.broll.poklmon.battle.calc;

import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.PoklmonTeamEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;

public class EffectParameterBonus
{

    private static FieldEffects fieldEffects;

    public static void init(FieldEffects fieldEffects)
    {
        EffectParameterBonus.fieldEffects = fieldEffects;
    }


    public static double getGenauigkeit(FightPoklmon poklmon, double value)
    {
        //wenn nebel genauigkeit schlechter
        if (fieldEffects.isWeatherEffect(WeatherEffect.FOG))
        {
            value *= 0.7;
        }
        return value;
    }

    public static double getFluchtwert(FightPoklmon poklmon, double fluchtwert)
    {

        return fluchtwert;
    }

    public static int getAttack(FightPoklmon poklmon, int attack)
    {

        return attack;
    }

    public static int getDefence(FightPoklmon poklmon, int defence)
    {
        //wenn vereist, verteidigung verdoppeln
        PoklmonStatusChanges status = poklmon.getStatusChanges();
        if (status.hasMainStatusChange(MainFightStatus.ICE))
        {
            defence *= 2;
        }
        return defence;
    }

    public static int getInitiative(FightPoklmon poklmon, int intiative)
    {
        //wenn paralysiert, initative nur noch 25%
        PoklmonStatusChanges status = poklmon.getStatusChanges();
        if (status.hasMainStatusChange(MainFightStatus.PARALYZE))
        {
            intiative *= 0.25;
        }
        
        //bei rückenwind init verdoppeln
        PoklmonTeamEffect poklmonTeam = fieldEffects.getTeamEffects(poklmon);
        if(poklmonTeam.hasTeamEffect(TeamEffect.INITWIND))
        {
            intiative*=2;
        }
        
        return intiative;
    }

    public static int getSpecialAttack(FightPoklmon poklmon, int specialattack)
    {

        return specialattack;
    }

    public static int getSpecialDefence(FightPoklmon poklmon, int specialdefence)
    {

        return specialdefence;
    }

}
