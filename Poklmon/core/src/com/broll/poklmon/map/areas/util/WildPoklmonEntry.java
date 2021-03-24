package com.broll.poklmon.map.areas.util;

public class WildPoklmonEntry
{

    private int poklmonID;
    private int minLevel;
    private int maxLevel;
    private double chance;

    public WildPoklmonEntry(int poklmonID, double chance, int minLevel, int maxLevel)
    {
        this.poklmonID = poklmonID;
        this.chance = chance;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
    }

    public double getChance()
    {
        return chance;
    }

    public int getMaxLevel()
    {
        return maxLevel;
    }

    public int getMinLevel()
    {
        return minLevel;
    }

    public int getPoklmonID()
    {
        return poklmonID;
    }

}
