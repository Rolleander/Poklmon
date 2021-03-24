package com.broll.poklmon.poklmon.util;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

public class PoklmonAttackLearning
{

    private DataContainer data;
    private int learnedPlace;
    
    public PoklmonAttackLearning(DataContainer data)
    {
        this.data = data;
    }

    public boolean tryLearnAttack(PoklmonData poklmon, int attackID)
    {
        AttackData[] attacks = poklmon.getAttacks();
        for (int i = 0; i < 4; i++)
        {
            AttackData atk = attacks[i];
            if (atk.getAttack() == AttackData.NO_ATTACK)
            {
                //learn attack
                learnAttack(poklmon, attackID, i);
                return true;
            }
        }
        return false;
    }

    public void learnAttack(PoklmonData poklmon, int attackID, int place)
    {
        AttackData[] attacks = poklmon.getAttacks();
        AttackData atk = attacks[place];
        atk.setAttack(attackID);
        //get full ap
        int ap = data.getAttacks().getAttack(attackID).getDamage().getAp();
        atk.setAp((byte)ap);
        learnedPlace=place;
    }
    
    public int getLearnedPlace() {
		return learnedPlace;
	}

}
