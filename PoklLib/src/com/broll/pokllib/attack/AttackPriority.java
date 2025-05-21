package com.broll.pokllib.attack;

public enum AttackPriority
{  
    
    STANDARD("Standard",0),LOWER("Weniger",-1),LOWER2("Gering",-2),LOWER3("Immer zuletzt",-3),
    HIGHER("Höher",1),HIGHER2("Hoch",2),HIGHER3("Immer zuerst",3);
    
    private int priority;
    private String name;

    private AttackPriority(String n,int p)
    {
        this.priority=p;
        this.name=n;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getPriority()
    {
        return priority;
    }
}
