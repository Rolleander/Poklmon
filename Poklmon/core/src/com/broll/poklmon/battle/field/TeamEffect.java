package com.broll.poklmon.battle.field;

public enum TeamEffect
{
    // # = Team Trainer Name
    // Bei wildem Poklmon => Poklmonname
    REFLECTOR("# wird von einem Reflektor umgeben!",null,"Der Reflektor von # lässt nach!"),
    LUCKBARRIERE("# wird vor gefährlichen Angriffen geschützt!",null,"Der Angriffs-Schutz von # lässt nach!"),
    INITWIND("# erhält Rückenwind!",null,"Der Rückenwind von # lässt nach!"),
    STATEBARRIERE("# wird vor Statusänderungen geschützt!",null,"Der Bodyguard von # lässt nach!"),
    VALUEBARRIERE("# wird vor Statuswertvänderungen durch den Gegner geschützt!",null,"Der Weißnebel von # lässt nach!"),
    ENERGYBLOCK("# wird von einer Energiewand umgeben!",null,"Die Energiewand von # lässt nach!");
    
    
    
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
