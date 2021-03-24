package com.broll.poklmon.battle.field;

public enum TeamEffect
{
    // # = Team Trainer Name
    // Bei wildem Poklmon => Poklmonname
    REFLECTOR("# wird von einem Reflektor umgeben!",null,"Der Reflektor von # l�sst nach!"),
    LUCKBARRIERE("# wird vor gef�hrlichen Angriffen gesch�tzt!",null,"Der Angriffs-Schutz von # l�sst nach!"),
    INITWIND("# erh�lt R�ckenwind!",null,"Der R�ckenwind von # l�sst nach!"),
    STATEBARRIERE("# wird vor Status�nderungen gesch�tzt!",null,"Der Bodyguard von # l�sst nach!"),
    VALUEBARRIERE("# wird vor Statuswertv�nderungen durch den Gegner gesch�tzt!",null,"Der Wei�nebel von # l�sst nach!"),
    ENERGYBLOCK("# wird von einer Energiewand umgeben!",null,"Die Energiewand von # l�sst nach!");
    
    
    
    private String entryName;
    private String durName;
    private String exitName;

    private TeamEffect( String entryName,String durName, String exitName)
    {
    
        this.entryName=entryName;
        this.durName=durName;
        this.exitName=exitName;
    }
    
    public String getDurName()
    {
        return durName;
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
