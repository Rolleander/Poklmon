package com.broll.poklmon.poklmon.util;

public interface LevelCalcListener
{
    
    public void newLevel(int level);
    
    public boolean canLearnAttack(int attack);

    public void evolvesTo(int poklmonID);
}
