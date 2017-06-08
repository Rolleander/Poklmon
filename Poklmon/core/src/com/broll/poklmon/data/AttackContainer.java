package com.broll.poklmon.data;

import java.util.List;

import com.broll.pokllib.attack.Attack;

public class AttackContainer
{

    private List<Attack> attack;
    

    public AttackContainer(List<Attack> attacks)
    {
        this.attack=attacks;
    }
    
    public Attack getAttack(int id)
    {
        if(id>-1&&id<attack.size())
        {
            return attack.get(id);
        }
        else
        {
            try
            {
                throw new DataException("False Attack ID! ("+id+")");
            }
            catch (DataException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    public int getAttackID(Attack a)
    {
    	return attack.indexOf(a);
    }
    
    public int getNumberOfAttacks()
    {
        return attack.size();
    }
    
}
