package com.broll.poklmon.battle.process;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.calc.AttackDamageBonus;
import com.broll.poklmon.battle.calc.AttackStatsManipulation;
import com.broll.poklmon.battle.calc.EffectParameterBonus;
import com.broll.poklmon.battle.field.GlobalEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.StatusChangeCheck;
import com.broll.poklmon.battle.process.effects.EffectProcessHandicap;
import com.broll.poklmon.battle.process.effects.EffectProcessHeal;
import com.broll.poklmon.battle.process.effects.EffectProcessInflict;
import com.broll.poklmon.battle.process.effects.GlobalEffectProcess;
import com.broll.poklmon.battle.process.effects.TeamEffectProcess;
import com.broll.poklmon.battle.process.effects.WeatherEffectProcess;
import com.broll.poklmon.battle.process.special.RoundBasedEffectAttacks;
import com.broll.poklmon.network.NetworkException;

public class BattleProcessEffects extends BattleProcessControl
{
    //special case: animation for selfhit
    public static final int CONFUSION_SELFHIT_ANIMATION_ID = 11;
    private EffectProcessHeal healProcess;
    private EffectProcessInflict inflictProcess;
    private EffectProcessHandicap handicapProcess;
    private GlobalEffectProcess globalEffectProcess;
    private TeamEffectProcess teamEffectProcess;
    private WeatherEffectProcess weatherEffectProcess;
    private RoundBasedEffectAttacks roundBasedEffectAttacks;

    public BattleProcessEffects(BattleManager manager, BattleProcessCore handler)
    {
        super(manager, handler);
        healProcess = new EffectProcessHeal(manager, handler);
        inflictProcess = new EffectProcessInflict(manager, handler);
        handicapProcess = new EffectProcessHandicap(manager, handler);
        globalEffectProcess = new GlobalEffectProcess(manager, handler);
        teamEffectProcess = new TeamEffectProcess(manager, handler);
        weatherEffectProcess = new WeatherEffectProcess(manager, handler);
        roundBasedEffectAttacks = new RoundBasedEffectAttacks(manager, handler);
        //init static  field effects
        EffectParameterBonus.init(manager.getFieldEffects());
        AttackDamageBonus.init(manager.getFieldEffects());
        StatusChangeCheck.init(manager.getFieldEffects());
        AttackStatsManipulation.init(manager.getFieldEffects());

    }

    public synchronized void doAttackEffects(UseAttack attack, FightPoklmon user, FightPoklmon target)
    {
        // check main status change
        MainFightStatus status = attack.getChangeStatusTarget();
        if (status != null)
        {
            inflictProcess.setStatusChange(target, status);
        }
        status = attack.getChangeStatusUser();
        if (status != null)
        {
            inflictProcess.setStatusChange(user, status);
        }
        // check effect status
        EffectStatus effect = attack.getEffectStatusTarget();
        if (effect != null)
        {
            inflictProcess.addEffectStatus(target, effect);
        }
        effect = attack.getEffectStatusUser();
        if (effect != null)
        {
            inflictProcess.addEffectStatus(user, effect);
        }
        //check global effects
        GlobalEffect globalEffect = attack.getGlobalEffect();
        if (globalEffect != null)
        {
            globalEffectProcess.addGlobalEffect(user, globalEffect);
        }
        //check weather effect
        WeatherEffect weatherEffect = attack.getWeatherEffect();
        if (weatherEffect != null)
        {
            weatherEffectProcess.addWeatherEffect(weatherEffect);
        }
        //check user team effect
        TeamEffect teamEffect = attack.getTeamEffectUser();
        if (teamEffect != null)
        {
            teamEffectProcess.addTeamEffect(user, teamEffect);
        }
        //check target team effect
        teamEffect = attack.getTeamEffectTarget();
        if (teamEffect != null)
        {
            teamEffectProcess.addTeamEffect(target, teamEffect);
        }
    }

    public synchronized void preAttackRound()
    {
        //check global weather and team effect healing
        weatherEffectProcess.preRound();
        globalEffectProcess.preRound();
        teamEffectProcess.preRound();
        // check auto healing from states
        healProcess.checkAutoHealing(manager.getParticipants().getPlayer());
        healProcess.checkAutoHealing(manager.getParticipants().getEnemy());
    }


    public synchronized void postAttackRound() throws NetworkException
    {
        //check global weather and team effect damage
        weatherEffectProcess.postRound();
        globalEffectProcess.postRound();
        teamEffectProcess.postRound();

        manager.getFieldEffects().nextRound();

        // do state damage
        FightPoklmon player = manager.getParticipants().getPlayer();
        FightPoklmon enemy = manager.getParticipants().getEnemy();

        //handicaps auto healing
        handicapProcess.checkAutoHealing(player);
        handicapProcess.checkAutoHealing(enemy);

        //effect healing
        inflictProcess.doEffectDamage(enemy);
        if (enemy.isFainted())
        {
            // enemy killed
            core.getAttackProcess().doPokmlonDefeated();
        }
        // do while battle is still running
        if (core.isBattleRunning())
        {
            inflictProcess.doEffectDamage(player);
            if (player.isFainted())
            {
                // player killed
                core.getAttackProcess().doPokmlonDefeated();
            }

        }
    }


    public EffectProcessHandicap getHandicapProcess()
    {
        return handicapProcess;
    }

    public EffectProcessHeal getHealProcess()
    {
        return healProcess;
    }

    public EffectProcessInflict getInflictprocess()
    {
        return inflictProcess;
    }

    public GlobalEffectProcess getGlobalEffectProcess()
    {
        return globalEffectProcess;
    }

    public TeamEffectProcess getTeamEffectProcess()
    {
        return teamEffectProcess;
    }

    public WeatherEffectProcess getWeatherEffectProcess()
    {
        return weatherEffectProcess;
    }

    public RoundBasedEffectAttacks getRoundBasedEffectAttacks()
    {
        return roundBasedEffectAttacks;
    }
}
