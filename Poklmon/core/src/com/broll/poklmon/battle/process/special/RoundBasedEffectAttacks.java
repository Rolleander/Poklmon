package com.broll.poklmon.battle.process.special;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDamage;
import com.broll.pokllib.attack.AttackPriority;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.BattleRandom;

public class RoundBasedEffectAttacks extends BattleProcessControl
{
    private String dispText;

    public RoundBasedEffectAttacks(BattleManager manager, BattleProcessCore handler)
    {
        super(manager, handler);
    }

    public boolean canDoNormalAttack(FightPoklmon poklmon)
    {
        PoklmonStatusChanges status = poklmon.getStatusChanges();

        if (status.hasEffectChange(EffectStatus.SOLARBEAM))
        {
            return false;
        }
        if (status.hasEffectChange(EffectStatus.FUCHTLER))
        {
            return false;
        }
        if (status.hasEffectChange(EffectStatus.FLUTWELLE))
        {
            return false;
        }
        if (status.hasEffectChange(EffectStatus.DRAGONSTORM))
        {
            return false;
        }
        return true;
    }

    public boolean mustWaitRound(FightPoklmon poklmon)
    {
        return false;
    }

    public FightAttack useSpecialAttack(FightPoklmon poklmon)
    {
        PoklmonStatusChanges status = poklmon.getStatusChanges();
        
        if (status.hasEffectChange(EffectStatus.SOLARBEAM))
        {
            status.removeEffectChange(EffectStatus.SOLARBEAM);

            //use solarbeam attack
            Attack solarbeam = copyAttack(104, null);
            //set animation
            solarbeam.setAnimationID(27);
            return new FightAttack(solarbeam);

        }

        if (status.hasEffectChange(EffectStatus.FLUTWELLE))
        {
            status.removeEffectChange(EffectStatus.FLUTWELLE);
            //use strong flutwelle attack
            Attack flutwelle = copyAttack(110, null);
            //welle greift mit niedriger priorität an
            flutwelle.getDamage().setPriority(AttackPriority.LOWER);
            int damage = flutwelle.getDamage().getDamage();
            damage *= 2; //schaden verdoppeln der großen welle
            flutwelle.getDamage().setDamage(damage);
            return new FightAttack(flutwelle);

        }

        if (status.hasEffectChange(EffectStatus.FUCHTLER))
        {
            int duration = status.getEffectChangeDuration(EffectStatus.FUCHTLER);
            // heal from fuchtler nach 1-2 folgerunden
            String script = null;
            if (isOver(duration, 1, 2))
            {
                status.removeEffectChange(EffectStatus.FUCHTLER);
                //verwirre anwender
                script = "util.addUserEffectChance(\"confusion\", 1)";
            }
            //use fuchtler attack
            Attack fuchtler = copyAttack(109, script);
            return new FightAttack(fuchtler);
        }
        
        if (status.hasEffectChange(EffectStatus.DRAGONSTORM))
        {
            int duration = status.getEffectChangeDuration(EffectStatus.DRAGONSTORM);
            // heal from drachensturm nach 1-2 folgerunden
            String script = null;
            if (isOver(duration, 1, 2))
            {
                status.removeEffectChange(EffectStatus.DRAGONSTORM);
                //verwirre anwender
                script = "util.addUserEffectChance(\"confusion\", 1)";
            }
            //use drachensturm attack
            Attack dragonstorm = copyAttack(120, script);
            return new FightAttack(dragonstorm);
        }

        return null;
    }

    public boolean isOver(int rounds, int minRounds, int maxRounds)
    {
        if (rounds >= minRounds)
        {
            int dif = (maxRounds - minRounds) + 1;
            return rounds > (int)(BattleRandom.random() * dif);
        }
        return false;
    }

    private Attack copyAttack(int nr, String effect)
    {
        Attack a = new Attack();
        Attack old = manager.getData().getAttacks().getAttack(nr);
        a.setAnimationID(old.getAnimationID());
        a.setAttackType(old.getAttackType());
        AttackDamage d = new AttackDamage();
        d.setHitchance(old.getDamage().getHitchance());
        d.setDamage(old.getDamage().getDamage());
        d.setPriority(old.getDamage().getPriority());
        a.setDamage(d);
        a.setDescription(old.getDescription());
        a.setElementType(old.getElementType());
        a.setEffectCode(effect);
        a.setName(old.getName());
        return a;
    }

    public String getDispText()
    {
        return dispText;
    }

}
