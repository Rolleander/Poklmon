package com.broll.poklmon.battle.poklmon.states;

public class EffectDurationCount
{
    private int duration;

    public EffectDurationCount()
    {
        duration = 0;
    }

    public void reset()
    {
        duration=0;
    }

    public int getDuration()
    {
        return duration;
    }

    public void increment()
    {
        duration++;
    }

}
