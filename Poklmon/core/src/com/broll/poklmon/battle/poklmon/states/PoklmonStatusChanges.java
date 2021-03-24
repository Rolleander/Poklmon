package com.broll.poklmon.battle.poklmon.states;

import java.util.HashMap;

public class PoklmonStatusChanges
{

    private MainFightStatus mainStatus = null;
    private HashMap<EffectStatus, EffectDurationCount> effectChanges = new HashMap<EffectStatus, EffectDurationCount>();
    private int mainStatusRounds;

    public PoklmonStatusChanges()
    {

    }
    
    public boolean hasMainStatusChange(MainFightStatus status)
    {
        return mainStatus==status;
    }

    public boolean hasEffectChange(EffectStatus effect)
    {
        return effectChanges.containsKey(effect);
    }

    public int getEffectChangeDuration(EffectStatus effect)
    {
        return effectChanges.get(effect).getDuration();
    }

    public void removeEffectChange(EffectStatus effect)
    {
        effectChanges.remove(effect);
    }

    public void addEffectChange(EffectStatus effect)
    {
        if (effectChanges.containsKey(effect))
        {
            effectChanges.remove(effect);
        }
        effectChanges.put(effect, new EffectDurationCount());
    }

    public void setStatus(MainFightStatus mainStatus)
    {
        this.mainStatus = mainStatus;
        mainStatusRounds = 0;
    }

    public void healStatus()
    {
        setStatus(null);
    }

    public boolean hasMainStateChangeEffect()
    {
        return mainStatus != null;
    }

    public void nextRound()
    {
        mainStatusRounds++;
        for (EffectDurationCount duration : effectChanges.values())
        {
            duration.increment();
        }
    }

    public MainFightStatus getMainStatus()
    {
        return mainStatus;
    }

    public int getMainStatusRounds()
    {
        return mainStatusRounds;
    }

}
