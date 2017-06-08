package com.broll.poklmon.battle.field;

import java.util.HashMap;

import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectDurationCount;


public class FieldEffects
{
    private EffectDurationCount weatherEffectCount = new EffectDurationCount();
    //special global effect (just one at a time => weather)
    private WeatherEffect weatherEffect;
    //global effects for both poklmon teams
    private HashMap<GlobalEffect, EffectDurationCount> globalEffects = new HashMap<GlobalEffect, EffectDurationCount>();
    //global effect for one poklmon team
    private PoklmonTeamEffect[] teamEffects = new PoklmonTeamEffect[2];

    public FieldEffects(BattleParticipants battleParticipants)
    {
        teamEffects[0] = new PoklmonTeamEffect(battleParticipants.getPlayerTeam());
        teamEffects[1] = new PoklmonTeamEffect(battleParticipants.getEnemyTeam());
        
        //debug
       // setWeatherEffect(WeatherEffect.STORM);
    }


    public PoklmonTeamEffect getTeamEffects(FightPoklmon poklmon)
    {
        for (PoklmonTeamEffect team : teamEffects)
        {

            if (team.isPoklmonInTeam(poklmon))
            {
                return team;
            }
        }
        return null;
    }
    
    public boolean isWeatherEffect(WeatherEffect effect)
    {
        return weatherEffect==effect;
    }

    public WeatherEffect getWeatherEffect()
    {
        return weatherEffect;
    }

    public boolean hasWeatherEffect()
    {
        return weatherEffect != null;
    }

    public void setWeatherEffect(WeatherEffect weatherEffect)
    {
        this.weatherEffect = weatherEffect;
        weatherEffectCount.reset();
    }


    public boolean hasGlobalEffect(GlobalEffect effect)
    {
        return globalEffects.containsKey(effect);
    }

    public int getGlobalEffectDuration(GlobalEffect effect)
    {
        return globalEffects.get(effect).getDuration();
    }

    public void removeGlobalEffect(GlobalEffect effect)
    {
        globalEffects.remove(effect);
    }

    public void addGlobalEffect(GlobalEffect effect)
    {
        if (globalEffects.containsKey(effect))
        {
            globalEffects.remove(effect);
        }
        globalEffects.put(effect, new EffectDurationCount());
    }

    public void nextRound()
    {
        weatherEffectCount.increment();
        for (EffectDurationCount duration : globalEffects.values())
        {
            duration.increment();
        }
        for (PoklmonTeamEffect team : teamEffects)
        {
            team.nextRound();
        }

    }

    public int getWeatherEffectDuration()
    {
        return weatherEffectCount.getDuration();
    }


}
