package com.broll.poklmon.battle.calc;

import com.broll.poklmon.battle.poklmon.FightPoklmon;

public class FightPoklmonParameterCalc
{


    public static double getFluchtwert(FightPoklmon poklmon)
    {
        int plus = poklmon.getAttributes().getFluchtwertPlus();
        double value = CalcPlusAttributes.getFluchtwert(plus);
        return EffectParameterBonus.getFluchtwert(poklmon, value);
    }

    public static double getGenauigkeit(FightPoklmon poklmon)
    {
        int plus = poklmon.getAttributes().getGenauigkeitsPlus();
        double value = CalcPlusAttributes.getGenauigkeit(plus);
        return EffectParameterBonus.getGenauigkeit(poklmon, value);
    }

    public static int getAttack(FightPoklmon poklmon)
    {
        int value = getAttackBase(poklmon);
        int plus = poklmon.getAttributes().getAttackPlus();
        value = CalcPlusAttributes.calcAttributePlus(value, plus);
        return value;
    }

    public static int getSpecialAttack(FightPoklmon poklmon)
    {
        int value = getSpecialAttackBase(poklmon);
        int plus = poklmon.getAttributes().getSpecial_attackPlus();
        value = CalcPlusAttributes.calcAttributePlus(value, plus);
        return value;
    }

    public static int getInitiative(FightPoklmon poklmon)
    {
        int value = getInitiativeBase(poklmon);
        int plus = poklmon.getAttributes().getInitPlus();
        value = CalcPlusAttributes.calcAttributePlus(value, plus);
        return value;
    }

    public static int getDefence(FightPoklmon poklmon)
    {
        int value = getDefenceBase(poklmon);
        int plus = poklmon.getAttributes().getDefencePlus();
        value = CalcPlusAttributes.calcAttributePlus(value, plus);
        return value;
    }

    public static int getSpecialDefence(FightPoklmon poklmon)
    {
        int value = getSpecialDefenceBase(poklmon);
        int plus = poklmon.getAttributes().getSpecial_defencePlus();
        value = CalcPlusAttributes.calcAttributePlus(value, plus);
        return value;
    }


    public static int getAttackBase(FightPoklmon poklmon)
    {
        int value = poklmon.getAttributes().getAttack();
        value = EffectParameterBonus.getAttack(poklmon, value);
        return value;
    }

    public static int getInitiativeBase(FightPoklmon poklmon)
    {
        int value = poklmon.getAttributes().getInit();
        value = EffectParameterBonus.getInitiative(poklmon, value);
        return value;
    }

    public static int getSpecialAttackBase(FightPoklmon poklmon)
    {
        int value = poklmon.getAttributes().getSpecial_attack();
        value = EffectParameterBonus.getSpecialAttack(poklmon, value);
        return value;
    }


    public static int getDefenceBase(FightPoklmon poklmon)
    {
        int value = poklmon.getAttributes().getDefence();
        value = EffectParameterBonus.getDefence(poklmon, value);
        return value;
    }


    public static int getSpecialDefenceBase(FightPoklmon poklmon)
    {
        int value = poklmon.getAttributes().getSpecial_defence();
        value = EffectParameterBonus.getSpecialDefence(poklmon, value);
        return value;
    }



}
