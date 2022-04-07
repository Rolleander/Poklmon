package com.broll.poklmon.battle.process.effects;

import com.broll.pokllib.animation.Animation;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.poklmon.states.StatusChangeCheck;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.message.BattleMessages;

public class EffectProcessInflict extends BattleProcessControl {

    public static int HEAL_ANIMATION_ID = 31;
    public static int EGELSAMEN_ANIMATION_ID = 32;
    public static int STRONG_GENES_ANIMATION_ID = 41;

    public EffectProcessInflict(BattleManager manager, BattleProcessCore handler) {
        super(manager, handler);
    }

    public synchronized boolean canSetStatusChange(FightPoklmon target, MainFightStatus status) {
        if (StatusChangeCheck.isStatusChangeAllowed(status, target)) {
            MainFightStatus old = target.getStatusChanges().getMainStatus();
            // just change if ice or sleep or fainting status when not normal
            if (old != null) {
                if (old == status) {
                    // Cant set same status again
                    return false;
                }
                if (old == MainFightStatus.FAINTED || old == MainFightStatus.ICE) {
                    // cant override fainted or ice
                    return false;
                } else {
                    if (status != MainFightStatus.SLEEPING) {
                        // nur überschreiben wenn neuer zustand schlaf ist,
                        // ansonsten bleibt alter zustand
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public synchronized void setStatusChange(FightPoklmon target, MainFightStatus status) {
        if (status == null) { // heal status
            core.getEffectProcess().getHealProcess().healMainChangeStatus(target);
            return;
        }
        if (target.isFainted()) {
            return;
        }
        // check if status change allowed
        if (canSetStatusChange(target, status)) {

            target.setMainStatus(status);
            // show animation
            showEffectAnimation(target, status);
            // show text
            String entry = status.getEntryName();
            if (entry != null) {
                String text = BattleMessages.putName(entry, target.getName());
                showText(text);
            }
        }
    }

    public synchronized void addEffectStatus(FightPoklmon target, EffectStatus status) {
        if (target.isFainted()) {
            return;
        }
        PoklmonStatusChanges stats = target.getStatusChanges();
        boolean hasEffect = stats.hasEffectChange(status);
        stats.addEffectChange(status);
        if (!hasEffect) {
            // show animation
            showEffectAnimation(target, status);
            String text = status.getEntryName();
            if (text != null) {
                text = BattleMessages.putName(text, target.getName());
                showText(text);
            }
        }
    }

    private synchronized boolean isTargetInFight(FightPoklmon poklmon) {
        if (poklmon == manager.getParticipants().getEnemy() || poklmon == manager.getParticipants().getPlayer()) {
            return true;
        }
        return false;
    }

    private synchronized boolean isAnimationMirrored(FightPoklmon poklmon) {
        if (poklmon == manager.getParticipants().getEnemy()) {
            return true;
        }
        return false;
    }

    private void showEffectAnimation(FightPoklmon target, MainFightStatus status) {
        int animation = status.getAnimationID();
        showAnimation(target, animation);
    }

    private void showEffectAnimation(FightPoklmon target, EffectStatus status) {
        int animation = status.getAnimationID();
        showAnimation(target, animation);
    }

    public void showAnimation(FightPoklmon target, int animationID) {
        if (animationID != -1) {
            boolean mirrored = isAnimationMirrored(target);
            Animation showAnimation = manager.getData().getAnimations().getAnimation(animationID);
            core.getAttackProcess().showAnimation(showAnimation, mirrored);
        }
    }

    public synchronized void damagePoklmon(FightPoklmon target, int animationID, int damage) {
        this.damagePoklmon(target, null, animationID, damage);
    }

    public synchronized void damagePoklmon(FightPoklmon target, String text, int animationID, int damage) {
        if (damage == 0 || target.isFainted()) {
            return;
        }
        boolean mirrored = isAnimationMirrored(target);
        if (animationID != -1) {
            Animation showAnimation = manager.getData().getAnimations().getAnimation(animationID);
            core.getAttackProcess().showAnimation(showAnimation, mirrored);
        }
        // do damage
        core.getAttackProcess().doDamage(target, text, damage);
    }

    public synchronized void healPoklmon(FightPoklmon target, String text, int heal) {
        if (heal == 0 || target.isFullHealth()) {
            return;
        }
        if (isTargetInFight(target)) {
            manager.getData().getSounds().playSound("b_heal");
            boolean mirrored = isAnimationMirrored(target);
            Animation showAnimation = manager.getData().getAnimations().getAnimation(HEAL_ANIMATION_ID);
            core.getAttackProcess().showAnimation(showAnimation, mirrored);
        }
        core.getAttackProcess().doHeal(target, text, heal);
    }

    private synchronized FightPoklmon getTargetEnemy(FightPoklmon player) {
        if (player == manager.getParticipants().getPlayer()) {
            return manager.getParticipants().getEnemy();
        } else {
            return manager.getParticipants().getPlayer();
        }
    }

    public synchronized void doEffectDamage(FightPoklmon poklmon) {
        PoklmonStatusChanges state = poklmon.getStatusChanges();
        String name = poklmon.getName();
        int maxKp = poklmon.getAttributes().getMaxhealth();

        // check main stats damage
        if (state.hasMainStateChangeEffect()) {
            MainFightStatus status = state.getMainStatus();
            String effect = status.getDurName();
            int animationID = status.getAnimationID();
            int damage = -1;
            switch (status) {
                case BURNING:
                    damage = StateEffectCalc.getBurnDamage(maxKp);
                    break;
                case POISON:
                    damage = StateEffectCalc.getPoisonDamage(maxKp);
                    break;
                case TOXIN:
                    int toxinRounds = state.getMainStatusRounds() + 1;
                    damage = StateEffectCalc.getToxinDamage(maxKp, toxinRounds);
                    break;
            }
            if (damage != -1) {
                if (effect != null) {
                    String text = BattleMessages.putName(effect, name);
                    showText(text);
                }
                damagePoklmon(poklmon, animationID, damage);
            }
        }

        // check effects

        // Kräutergarten
        if (state.hasEffectChange(EffectStatus.HEALPLANTS)) {
            // heal target
            String text = BattleMessages.putName(EffectStatus.HEALPLANTS.getDurName(), name);
            // showText(text);
            healPoklmon(poklmon, text, StateEffectCalc.getWeakHeal(maxKp));
        }

        // Egelsamen
        if (state.hasEffectChange(EffectStatus.ABSORBEFFECT)) {
            String text = BattleMessages.putName(EffectStatus.ABSORBEFFECT.getDurName(), name);
            showText(text);
            FightPoklmon enemy = getTargetEnemy(poklmon);
            // egelsamen
            int damage = StateEffectCalc.getEgelsamenDamage(maxKp);
            // show animation from enemy
            showAnimation(enemy, EGELSAMEN_ANIMATION_ID);
            // do damage to self
            core.getAttackProcess().doDamage(poklmon, null, damage);
            // heal player
            healPoklmon(enemy, null, damage);
        }

        // Untergang
        if (state.hasEffectChange(EffectStatus.DOOM)) {
            int rounds = state.getEffectChangeDuration(EffectStatus.DOOM);
            if (rounds >= 5) {
                // kill poklmon
                int damage = maxKp;
                doPoklmonEffectDamage(poklmon, EffectStatus.DOOM, damage);
            }
        }

        // Fluch
        if (state.hasEffectChange(EffectStatus.CURSE)) {
            // do curse damage
            int damage = StateEffectCalc.getCurseDamage(maxKp);
            doPoklmonEffectDamage(poklmon, EffectStatus.CURSE, damage);
        }

        // einschläfern
        if (state.hasEffectChange(EffectStatus.SLEEPY)) {
            int round = state.getEffectChangeDuration(EffectStatus.SLEEPY);
            if (round >= 1) {
                // remove effect
                state.removeEffectChange(EffectStatus.SLEEPY);
                // add sleep
                setStatusChange(poklmon, MainFightStatus.SLEEPING);
            }
        }

        // klingenstrudel schaden
        if (state.hasEffectChange(EffectStatus.KLINGENSTRUDEL)) {
            int damage = StateEffectCalc.getPercentDamage(maxKp, (1.5 / 16.0));
            doPoklmonEffectDamage(poklmon, EffectStatus.KLINGENSTRUDEL, damage);
        }

        // wickel schaden
        if (state.hasEffectChange(EffectStatus.WICKEL)) {
            int damage = StateEffectCalc.getPercentDamage(maxKp, (1.0 / 16.0));
            doPoklmonEffectDamage(poklmon, EffectStatus.WICKEL, damage);
        }

        state.nextRound();
    }

    private void doPoklmonEffectDamage(FightPoklmon poklmon, EffectStatus effect, int damage) {
        String name = poklmon.getName();
        String text = BattleMessages.putName(effect.getDurName(), name);
        showText(text);
        int animation = effect.getAnimationID();
        damagePoklmon(poklmon, animation, damage);
    }

}
