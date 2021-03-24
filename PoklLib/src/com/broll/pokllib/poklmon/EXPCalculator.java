package com.broll.pokllib.poklmon;

public class EXPCalculator
{


    public static int calcEXP(EXPLearnTypes type, int level)
    {
        if (level > 1)
        {
            switch (type)
            {
                case FAST:
                    return (int)((4 * Math.pow(level, 3)) / 5);
                case MIDDLE_FAST:
                    return (int)Math.pow(level, 3);
                case SLOW_MIDDLE:
                    return (int)(6d / 5d * Math.pow(level, 3) - 15 * Math.pow(level, 2) + 100 * level - 140);
                case SLOW:
                    return (int)((5 * Math.pow(level, 3)) / 4);
            }
        }
        return 1;

    }


}
