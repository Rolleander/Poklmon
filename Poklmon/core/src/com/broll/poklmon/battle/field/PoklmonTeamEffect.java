package com.broll.poklmon.battle.field;

import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectDurationCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PoklmonTeamEffect
{
    private HashMap<TeamEffect, EffectDurationCount> teamEffects = new HashMap<TeamEffect, EffectDurationCount>();
    private List<FightPoklmon> poklmonTeam;

    public PoklmonTeamEffect(ArrayList<FightPoklmon> arrayList)
    {
        this.poklmonTeam = arrayList;
    }

    public boolean isPoklmonInTeam(FightPoklmon poklmon)
    {
        return poklmonTeam.contains(poklmon);
    }

    public boolean hasTeamEffect(TeamEffect effect)
    {
        return teamEffects.containsKey(effect);
    }

    public int getTeamEffectDuration(TeamEffect effect)
    {
        return teamEffects.get(effect).getDuration();
    }

    public void removeTeamEffect(TeamEffect effect)
    {
        teamEffects.remove(effect);
    }

    public void addTeamEffect(TeamEffect effect)
    {
        if (teamEffects.containsKey(effect))
        {
            teamEffects.remove(effect);
        }
        teamEffects.put(effect, new EffectDurationCount());
    }

    public void nextRound()
    {
        for (EffectDurationCount duration : teamEffects.values())
        {
            duration.increment();
        }
    }
}
