package com.broll.poklmon.battle.process.effects;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.message.BattleMessages;

public class EffectProcessHeal extends BattleProcessControl
{

    public EffectProcessHeal(BattleManager manager, BattleProcessCore handler)
    {
        super(manager, handler);
    }

    public synchronized void healMainChangeStatus(FightPoklmon poklmon)
    {
        MainFightStatus status = poklmon.getStatusChanges().getMainStatus();
        if (status != null)
        {
            String healText = status.getExitName();
            if (healText != null)
            {
                healText = BattleMessages.putName(healText, poklmon.getName());
                // disp message for healing
                showText(healText);
            }
            poklmon.healMainStatus();
        }
    }

    public synchronized void checkAutoHealing(FightPoklmon poklmon)
    {
        PoklmonStatusChanges state = poklmon.getStatusChanges();
        if (state.hasMainStateChangeEffect())
        {
            MainFightStatus status = state.getMainStatus();
            int duration = state.getMainStatusRounds();
            if (duration > 1)
            {
                switch (status)
                {
                    case SLEEPING:
                        if (StateEffectCalc.stopSleepingChance(duration))
                        {
                            healMainChangeStatus(poklmon);
                        }
                        break;
                    case ICE:
                        if (StateEffectCalc.stopFrozenChance())
                        {
                            healMainChangeStatus(poklmon);
                        }

                        break;
                }
            }
        }
        if (state.hasEffectChange(EffectStatus.CONFUSION))
        {
            if (StateEffectCalc.stopConfusionChance())
            {
                removeStateEffect(poklmon, EffectStatus.CONFUSION);
            }

        }
        else if (state.hasEffectChange(EffectStatus.CRINGE))
        {
            // always heal recoil
            removeStateEffect(poklmon, EffectStatus.CRINGE);
        }
     
        
        tryRemoveEffect(poklmon, EffectStatus.KLINGENSTRUDEL, 3, 5);
        tryRemoveEffect(poklmon, EffectStatus.WICKEL, 4, 5);
        tryRemoveEffect(poklmon, EffectStatus.ENERGYFOCUS, 3, 4);
        
    }



    public synchronized void tryRemoveEffect(FightPoklmon poklmon, EffectStatus status, int minRounds)
    {
        PoklmonStatusChanges state = poklmon.getStatusChanges();
        if (state.hasEffectChange(status))
        {
            if (state.getEffectChangeDuration(status) >= minRounds)
            {
                removeStateEffect(poklmon, status);
            }
        }
    }
    
    public synchronized void tryRemoveEffect(FightPoklmon poklmon, EffectStatus status, int minRounds, int maxrounds)
    {
        PoklmonStatusChanges state = poklmon.getStatusChanges();
        if (state.hasEffectChange(status))
        {
        	int dur=state.getEffectChangeDuration(status);
            if (core.getEffectProcess().getRoundBasedEffectAttacks().isOver(dur,minRounds	,maxrounds))
            {
                removeStateEffect(poklmon, status);
            }
        }
    }

    public synchronized void removeStateEffect(FightPoklmon poklmon, EffectStatus status)
    {
        PoklmonStatusChanges state = poklmon.getStatusChanges();
        state.removeEffectChange(status);
        // show message
        String name = poklmon.getName();
        String exitName = status.getExitName();
        if (exitName != null)
        {
            String text = BattleMessages.putName(exitName, name);
            showText(text);
        }
    }

}
