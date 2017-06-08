package com.broll.poklmon.poklmon.util;

import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.save.PoklmonData;

public class PoklmonBattleUpdate
{

    public static void update(DataContainer data, PlayerPoklmon poklmon)
    {
        //do fight updates to data

        PoklmonData pd = poklmon.getPoklmonData();

        //update attacks and ap
    /*    for (int i = 0; i < 4; i++)
        {
            AttackData attack = pd.getAttacks()[i];
            FightAttack atk = poklmon.getAttacks()[i];
            int atkno = AttackData.NO_ATTACK;
            byte ap = 0;
            if (atk != null)
            {
                atkno = data.getAttacks().getAttackID(atk.getAttack());
                ap = (byte)atk.getAp();
            }
            attack.setAttack(atkno);
            attack.setAp(ap);
        }
*/
        //update exp,level and kp
        pd.setExp(poklmon.getExp());
        pd.setLevel(poklmon.getLevel());
        pd.setKp(poklmon.getAttributes().getHealth());
    }

}
