package com.broll.poklmon.battle.util.flags;

public class FightPoklmonFlags
{
    
    private int lastAttackID=BattleEventFlags.NONE;
    private DamageTaken lastDamageTaken=null;

    public FightPoklmonFlags()
    {
        
    }
    
    public void setLastAttackID(int lastAttackID)
    {
        this.lastAttackID = lastAttackID;
    }
    
    public void setLastDamageTaken(DamageTaken lastDamageTaken)
    {
        this.lastDamageTaken = lastDamageTaken;
    }
    
    public int getLastAttackID()
    {
        return lastAttackID;
    }
    
    public DamageTaken getLastDamageTaken()
    {
        return lastDamageTaken;
    }
    
}
