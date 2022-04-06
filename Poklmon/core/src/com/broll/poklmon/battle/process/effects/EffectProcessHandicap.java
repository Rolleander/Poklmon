package com.broll.poklmon.battle.process.effects;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.process.callbacks.SkipAttackCallback;
import com.broll.poklmon.battle.process.callbacks.SkipMoveCallback;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.battle.util.message.BattleMessages;

public class EffectProcessHandicap extends BattleProcessControl {

    public EffectProcessHandicap(BattleManager manager, BattleProcessCore handler) {
        super(manager, handler);
    }

    private boolean confusionStrikes;

    public synchronized boolean canAttack(FightPoklmon poklmon, UseAttack atk) {
        // callbacks
        for (SkipAttackCallback script : manager.getScriptCalls(SkipAttackCallback.class)) {
            if (script.call(poklmon, atk)) {
                return false;
            }
        }
        confusionStrikes = false;
        PoklmonStatusChanges state = poklmon.getStatusChanges();
        String name = poklmon.getName();
        String dispText = null;
        boolean canAttack = true;
        int animationID = -1;
        // check main states
        if (state.hasMainStateChangeEffect()) {
            MainFightStatus status = state.getMainStatus();
            dispText = BattleMessages.putName(status.getDurName(), name);
            animationID = status.getAnimationID();
            switch (status) {
                case ICE:
                    if (!atk.isIgnoreIce()) {
                        canAttack = false;
                    }
                    break;
                case SLEEPING:
                    if (!atk.isIgnoreSleep()) {
                        canAttack = false;
                    }
                    break;
                case PARALYZE:
                    if (!atk.isIgnoreParalyze()) {
                        canAttack = !StateEffectCalc.isParalyzed();
                    }
                    break;
            }
            if (canAttack) {
                dispText = null;
            }
        }
        if (canAttack == true) {
            // check status effects
            if (state.hasEffectChange(EffectStatus.PAUSE)) {
                dispText = BattleMessages.putName(EffectStatus.PAUSE.getDurName(), name);
                canAttack = false;
            } else if (state.hasEffectChange(EffectStatus.AFRAID)) {
                dispText = BattleMessages.putName(EffectStatus.AFRAID.getDurName(), name);
                canAttack = false;

            } else if (state.hasEffectChange(EffectStatus.CONFUSION)) {
                if (!atk.isIgnoreConfusion()) {
                    // calc confused chance
                    if (StateEffectCalc.isConfused()) {
                        confusionStrikes = true;
                        canAttack = false;
                        dispText = BattleMessages.putName(EffectStatus.CONFUSION.getDurName(), name);
                    }
                }
            } else if (state.hasEffectChange(EffectStatus.CRINGE)) {
                dispText = BattleMessages.putName(EffectStatus.CRINGE.getDurName(), name);
                canAttack = false;
            } else {
                //check round based attack state
                canAttack = !core.getEffectProcess().getRoundBasedEffectAttacks().mustWaitRound(poklmon);
                if (canAttack == false) {
                    dispText = core.getEffectProcess().getRoundBasedEffectAttacks().getDispText();
                }
            }
        }
        if (dispText != null) {
            // display text
            showText(dispText);
            if (animationID != -1) {
                core.getEffectProcess().getInflictprocess().showAnimation(poklmon, animationID);
            }
        }
        return canAttack;
    }

    public synchronized boolean canSelectMove(FightPoklmon poklmon) {
        PoklmonStatusChanges statusChanges = poklmon.getStatusChanges();
        if (statusChanges.hasEffectChange(EffectStatus.PAUSE)) {
            //cant select attack when resting
            return false;
        }
        if (statusChanges.hasEffectChange(EffectStatus.AFRAID)) {
            //cant when afraid
            return false;
        }
        // callbacks
        for (SkipMoveCallback script : manager.getScriptCalls(SkipMoveCallback.class)) {
            if (script.call(poklmon)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void checkAutoHealing(FightPoklmon poklmon) {
        //       PoklmonStatusChanges state = poklmon.getStatusChanges();
        EffectProcessHeal heal = core.getEffectProcess().getHealProcess();

        //heal effects after rounds
        heal.tryRemoveEffect(poklmon, EffectStatus.AFRAID, 1);
        heal.tryRemoveEffect(poklmon, EffectStatus.PAUSE, 1);

    }

    public boolean isConfusionStrikes() {
        return confusionStrikes;
    }


}
