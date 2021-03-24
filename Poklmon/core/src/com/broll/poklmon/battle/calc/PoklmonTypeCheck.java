package com.broll.poklmon.battle.calc;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.poklmon.FightPoklmon;

public class PoklmonTypeCheck
{

    public static boolean hasType(FightPoklmon poklmon, ElementType type)
    {
        Poklmon p = poklmon.getPoklmon();
        return p.getBaseType() == type|| p.getSecondaryType() == type;
    }

    
}
