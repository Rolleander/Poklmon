package com.broll.poklmon.battle.calc;

public class EXPGainCalculator
{
    
    public static int getEXPValue(int expBase, int level, boolean trainerBattle)
    {
        float a=1;
        if(trainerBattle)
        {
            a=1.5f;
        }
        float b=expBase;
        float l=level;
        
        return (int)((a*b*l)/7);       
    }

}
