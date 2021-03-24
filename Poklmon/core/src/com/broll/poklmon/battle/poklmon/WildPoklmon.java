package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.poklmon.PoklmonWesen;

public class WildPoklmon extends FightPoklmon
{
    private short[] dv;
    private PoklmonWesen wesen;
    
    public short[] getDv()
    {
        return dv;
    }


    public void setDv(short[] dv)
    {
        this.dv = dv;
    }
    
    public void setWesen(PoklmonWesen wesen)
    {
        this.wesen = wesen;
    }

    public PoklmonWesen getWesen()
    {
        return wesen;
    }
}
