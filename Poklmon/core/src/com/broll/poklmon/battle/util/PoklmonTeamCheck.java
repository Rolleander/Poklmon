package com.broll.poklmon.battle.util;

import com.broll.poklmon.battle.poklmon.FightPoklmon;

import java.util.List;

public class PoklmonTeamCheck
{

    public static boolean isTeamDefeated(List<FightPoklmon> team)
    {
        return getNumberOfUnfaintedPoklmons(team) == 0;
    }

    public static boolean hasPoklmonsToChange(List<FightPoklmon> team)
    {
        return getNumberOfUnfaintedPoklmons(team) > 1;
    }

    public static FightPoklmon getLivingPoklmon(List<FightPoklmon> team)
    {
        for (FightPoklmon pokl : team)
        {
            if (!pokl.isFainted())
            {
                return pokl;
            }
        }
        return null;
    }

    public static int getNumberOfUnfaintedPoklmons(List<FightPoklmon> team)
    {
        int numPoklmons = 0;
        for (FightPoklmon pokl : team)
        {
            if (!pokl.isFainted())
            {
                numPoklmons++;
            }
        }
        return numPoklmons;
    }
}
