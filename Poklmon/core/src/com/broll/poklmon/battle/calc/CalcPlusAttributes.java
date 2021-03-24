package com.broll.poklmon.battle.calc;

public class CalcPlusAttributes
{


    public static int calcAttributePlus(int v, int plus)
    {

        if (plus == 0)
        {
            return v;
        }
        else if (plus > 0)
        {
            // add power
            float addP = (2 + (float)plus) / 2;
            return (int)(v * addP);
        }
        else
        {
            // sub power
            float subP = 2 / (2 - (float)plus);
            return (int)(v * subP);
        }
    }

    public static float getGenauigkeit(int genauigkeitsPlus)
    {
        return AttackHitCalculator.getGenauigkeitTable(genauigkeitsPlus);
    }
    
    public static float getFluchtwert(int fluchtwertPlus)
    {
        return AttackHitCalculator.getGenauigkeitTable(fluchtwertPlus);
    }
    
}
