package com.broll.poklmon.battle.field;

public enum GlobalEffect
{

    DUMP("# verstreut giftigen Abfall!","# ist von giftigem Abfall umgeben!","Der giftige Abfall ist verschwunden!");
    
    private String entryName;
    private String durName;
    private String exitName;
    
    private GlobalEffect( String entryName, String durName,String exitName)
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
