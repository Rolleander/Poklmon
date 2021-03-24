package com.broll.poklmon.battle.field;

public enum WeatherEffect
{

    RAIN("Es beginnt zu regnen!","Der Regen lässt nach!"),
    SUN("Die Sonne strahlt hell!","Die Sonnenstrahlen ziehen sich zurück!"),
    SNOW("Ein Schneesturm zieht auf!","Der Schneesturm zieht vorüber!"),
    FOG("Ein dichter Nebel erscheint!","Der Nebel lässt nach!"),
    STORM("Ein Sturmgewitter zieht auf!","Das Gewitter lässt nach!"),
    NIGHT("Der Himmel verdunkelt sich!","Die Dunkelheit löst sich auf!");
    
    
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
