package com.broll.poklmon.battle.calc;

import com.broll.pokllib.attack.AttackPriority;
import com.broll.pokllib.attack.AttackType;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.battle.util.BattleMoveType;
import com.broll.poklmon.battle.util.BattleRandom;

public class AttackHitCalculator
{


    public static boolean hitsAttack(FightPoklmon user, FightPoklmon target, UseAttack attack)
    {
        if (attack.isHitsAlways())
        {
            return true;
        }
        double genauigkeit = FightPoklmonParameterCalc.getGenauigkeit(user);
        double fluchtwert = FightPoklmonParameterCalc.getFluchtwert(target);
        if (attack.getType() == AttackType.STATUS)
        {
            //status attacken gehen nicht öfter/seltener daneben
            genauigkeit = 1;
            fluchtwert = 1;
        }
        double p = attack.getHitchance() * (genauigkeit / fluchtwert);
        double r = BattleRandom.random();
        return p >= r;
    }

    public static boolean isVolltreffer( UseAttack attack)
    {
        float chance = 0;


        int v = attack.getVolltrefferChance();
        switch (v)
        {
            case 1:
                chance = 0.05f;
                break;
            case 2:
                chance = 0.15f;
                break;
            case 3:
                chance = 0.3f;
                break;
            case 4:
                chance = 0.5f;
                break;
            case 5:
                chance = 0.75f;
                break;
        }
        if (v > 5)
        {
            return true;
        }
        return chance >= BattleRandom.random();
    }


    public static float getGenauigkeitTable(int plus)
    {
        if (plus > 0)
        {
            return (3 + (float)plus) / 3f;
        }
        else if (plus < 0)
        {
            return 3f / (3 - (float)plus);
        }
        return 1;
    }

    public static boolean attacksPlayerFirst(BattleMove playerMove, BattleMove enemyMove, FightPoklmon player, FightPoklmon enemy)
    {
        boolean playerFirst = false;
        int prio1 = 0;
        int prio2 = 0;

        if (playerMove.getMoveType() == BattleMoveType.ATTACK)
        {
            //calc attack prio
            prio1 = playerMove.getAttack().getAttack().getDamage().getPriority().getPriority();
        }
        else
        {
            //move priority
            prio1 = AttackPriority.HIGHER2.getPriority();
        }

        if (enemyMove.getMoveType() == BattleMoveType.ATTACK)
        {
            //calc attack prio
            prio2 = enemyMove.getAttack().getAttack().getDamage().getPriority().getPriority();
        }
        else
        {
            //move priority
            prio2 = AttackPriority.HIGHER2.getPriority();
        }
        // prio vergleich
        if (prio1 == prio2)
        {
            // init vergleich 
            int init1 = FightPoklmonParameterCalc.getInitiative(player);
            int init2 = FightPoklmonParameterCalc.getInitiative(enemy);

            if (init1 == init2)
            {
                // check level
                int level1 = player.getLevel();
                int level2 = enemy.getLevel();
                if (level1 == level2)
                {
                    // alles gleich => random
                    if (BattleRandom.random() < 0.5)
                    {
                        playerFirst = true;
                    }
                }
                else if (level1 > level2)
                {
                    playerFirst = true;
                }
            }
            else if (init1 > init2)
            {
                playerFirst = true;
            }
        }
        else if (prio1 > prio2)
        {
            playerFirst = true;
        }
        return playerFirst;
    }


}
