package com.broll.poklmon.battle.field;

public enum WeatherEffect
{

    RAIN("Es beginnt zu regnen!","Der Regen l�sst nach!"),
    SUN("Die Sonne strahlt hell!","Die Sonnenstrahlen ziehen sich zur�ck!"),
    SNOW("Ein Schneesturm zieht auf!","Der Schneesturm zieht vor�ber!"),
    FOG("Ein dichter Nebel erscheint!","Der Nebel l�sst nach!"),
    STORM("Ein Sturmgewitter zieht auf!","Das Gewitter l�sst nach!"),
    NIGHT("Der Himmel verdunkelt sich!","Die Dunkelheit l�st sich auf!");
    
    
    private String entryName;
    private String exitName;
    
    
    private WeatherEffect( String entryName, String exitName)
    {
     
        this.entryName=entryName;
        this.exitName=exitName;
    }
    
    public String getEntryName()
    {
        return entryName;
    }
    
    public String getExitName()
    {
        return exitName;
    }
    
   
    
}
