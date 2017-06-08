package com.broll.poklmon.battle.calc;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDamage;
import com.broll.pokllib.attack.AttackPriority;
import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.util.BattleRandom;

public class StateEffectCalc
{

    
    public  static int getChance(double[] chances)
    {
        double r = BattleRandom.random();
        for (int i = 0; i < chances.length; i++)
        {
            double chance = chances[i];
            if (r < chance)
            {
                return i;
            }
            r -= chance;
        }
        return 0;
    }
    
    public static int rint(double r)
    {
      return rint((int)r);
    }

    public static int rint(int r)
    {
        if (r < 1)
        {
            return 1;
        }
        return r;
    }

    public static int getPercentDamage(int maxKp, double d)
    {
        return rint((int)((float)maxKp * d));
    }

    public static int getEgelsamenDamage(int maxKp)
    {
        return rint((int)((float)maxKp * (2f / 16f)));
    }

    public static int getSnowDamage(int maxKp)
    {
        return rint((int)((float)maxKp * (1.5f / 16f)));
    }

    public static int getPoisonDamage(int maxKp)
    {
        return rint((int)((float)maxKp * (2f / 16f)));
    }

    public static int getWeakHeal(int maxKp)
    {
        return rint((int)((float)maxKp * (1f / 16f)));
    }

    public static int getMiddleHeal(int maxKp)
    {
        return rint((int)((float)maxKp * (2f / 16f)));
    }

    public static int getStrongHeal(int maxKp)
    {
        return rint((int)((float)maxKp * (8f / 16f)));
    }

    public static int getBurnDamage(int maxKp)
    {
        return rint((int)((float)maxKp * (1.5f / 16f)));
    }

    public static int getToxinDamage(int maxKp, int toxinDuration)
    {
        return rint((int)((float)maxKp * ((float)toxinDuration / 16f)));
    }

    public static boolean isConfused()
    {
        return BattleRandom.random() <= 0.5;
    }

    public static boolean stopSleepingChance(int duration)
    {
        int r = (int)(BattleRandom.random() * (4 + duration));
        return r >= 4;
    }

    public static boolean stopConfusionChance()
    {
        return BattleRandom.random() * 4 < 1;
    }

    public static UseAttack getConfusedAttack()
    {
        UseAttack attack = new UseAttack();
        attack.setType(AttackType.PHYSICAL);
        attack.setDamage(40);
        attack.setHitchance(1);
        attack.setElement(null);
        attack.setVolltrefferChance(0);
        return attack;
    }

    public static FightAttack getDesperationAttack()
    {
        Attack atk = new Attack();
        atk.setAnimationID(0);
        atk.setAttackType(AttackType.PHYSICAL);
        AttackDamage dmg = new AttackDamage();
        dmg.setDamage(50);
        dmg.setHitchance(1);
        dmg.setPriority(AttackPriority.STANDARD);
        atk.setDamage(dmg);
        //50% rückstoß schaden
        atk.setEffectCode("atk.setSelfDamagePercent(0.5)");
        atk.setElementType(ElementType.NORMAL);
        atk.setName("Verzweifler");
        FightAttack attack = new FightAttack(atk);
        return attack;
    }

    public static boolean isParalyzed()
    {
        //25% chance paralyze
        return BattleRandom.random() <= 0.25f;
    }

    public static boolean stopFrozenChance()
    {
        // 5% to heal ice
        return 0.05 > BattleRandom.random();
    }

    public static boolean nightSleepChance()
    {
        return 0.2 > BattleRandom.random();
    }

    public static int getCurseDamage(int maxKp)
    {
        return rint((int)(maxKp * 0.25f));
    }

    public static int getStormDamage(int maxKp)
    {
        return rint((int)((float)maxKp * (2.5f / 16f)));
    }

}
