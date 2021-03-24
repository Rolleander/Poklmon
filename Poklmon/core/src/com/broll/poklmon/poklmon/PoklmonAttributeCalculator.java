package com.broll.poklmon.poklmon;

import com.broll.pokllib.poklmon.AttributeCalculator;
import com.broll.pokllib.poklmon.AttributeType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.save.PoklmonData;

public class PoklmonAttributeCalculator
{

    public static int getKP(DataContainer data, PoklmonData poklmon)
    {
        int id = poklmon.getPoklmon();
        int level = poklmon.getLevel();
        int dv = poklmon.getDv()[0];
        int fp = poklmon.getFp()[0];
        Poklmon pokl = data.getPoklmons().getPoklmon(id);
        return getKP(pokl, level, dv, fp);
    }

    public static int getAttack(DataContainer data, PoklmonData poklmon)
    {
        int id = poklmon.getPoklmon();
        int level = poklmon.getLevel();
        int dv = poklmon.getDv()[1];
        int fp = poklmon.getFp()[1];
        PoklmonWesen wesen = poklmon.getWesen();
        Poklmon pokl = data.getPoklmons().getPoklmon(id);
        return getAttack(pokl, level, dv, fp, wesen);
    }

    public static int getDefence(DataContainer data, PoklmonData poklmon)
    {
        int id = poklmon.getPoklmon();
        int level = poklmon.getLevel();
        int dv = poklmon.getDv()[2];
        int fp = poklmon.getFp()[2];
        PoklmonWesen wesen = poklmon.getWesen();
        Poklmon pokl = data.getPoklmons().getPoklmon(id);
        return getDefence(pokl, level, dv, fp, wesen);
    }

    public static int getSpecialAttack(DataContainer data, PoklmonData poklmon)
    {
        int id = poklmon.getPoklmon();
        int level = poklmon.getLevel();
        int dv = poklmon.getDv()[3];
        int fp = poklmon.getFp()[3];
        PoklmonWesen wesen = poklmon.getWesen();
        Poklmon pokl = data.getPoklmons().getPoklmon(id);
        return getSpecialAttack(pokl, level, dv, fp, wesen);
    }

    public static int getSpecialDefence(DataContainer data, PoklmonData poklmon)
    {
        int id = poklmon.getPoklmon();
        int level = poklmon.getLevel();
        int dv = poklmon.getDv()[4];
        int fp = poklmon.getFp()[4];
        PoklmonWesen wesen = poklmon.getWesen();
        Poklmon pokl = data.getPoklmons().getPoklmon(id);
        return getSpecialDefence(pokl, level, dv, fp, wesen);
    }

    public static int getInitiative(DataContainer data, PoklmonData poklmon)
    {
        int id = poklmon.getPoklmon();
        int level = poklmon.getLevel();
        int dv = poklmon.getDv()[5];
        int fp = poklmon.getFp()[5];
        PoklmonWesen wesen = poklmon.getWesen();
        Poklmon pokl = data.getPoklmons().getPoklmon(id);
        return getInitiative(pokl, level, dv, fp, wesen);
    }


    public static int getKP(Poklmon poklmon, int level, int DV, int FP)
    {
        int base = poklmon.getAttributes().getBaseKP();
        return AttributeCalculator.calcKP(base, level, DV, FP);
    }

    public static int getAttack(Poklmon poklmon, int level, int DV, int FP, PoklmonWesen wesen)
    {
        float w = getWesenMult(wesen, AttributeType.ATTACK);
        int base = poklmon.getAttributes().getBaseAttack();
        return AttributeCalculator.calcAttribute(base, level, DV, FP, w);
    }

    public static int getDefence(Poklmon poklmon, int level, int DV, int FP, PoklmonWesen wesen)
    {
        float w = getWesenMult(wesen, AttributeType.DEFENCE);
        int base = poklmon.getAttributes().getBaseDefence();
        return AttributeCalculator.calcAttribute(base, level, DV, FP, w);
    }

    public static int getSpecialAttack(Poklmon poklmon, int level, int DV, int FP, PoklmonWesen wesen)
    {
        float w = getWesenMult(wesen, AttributeType.SPECIAL_ATTACK);
        int base = poklmon.getAttributes().getBaseSpecialAttack();
        return AttributeCalculator.calcAttribute(base, level, DV, FP, w);
    }

    public static int getSpecialDefence(Poklmon poklmon, int level, int DV, int FP, PoklmonWesen wesen)
    {
        float w = getWesenMult(wesen, AttributeType.SPECIAL_DEFENCE);
        int base = poklmon.getAttributes().getBaseSpecialDefence();
        return AttributeCalculator.calcAttribute(base, level, DV, FP, w);
    }

    public static int getInitiative(Poklmon poklmon, int level, int DV, int FP, PoklmonWesen wesen)
    {
        float w = getWesenMult(wesen, AttributeType.INITIATIVE);
        int base = poklmon.getAttributes().getBaseInitiative();
        return AttributeCalculator.calcAttribute(base, level, DV, FP, w);
    }

    private static float getWesenMult(PoklmonWesen wesen, AttributeType type)
    {
        if (wesen.getTypeInc() == type)
        {
            return 1.1f;
        }
        else if (wesen.getTypeDec() == type)
        {
            return 0.9f;
        }
        return 1;
    }
}
