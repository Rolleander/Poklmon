package com.broll.pokllib.poklmon;

public enum AttributeType
{

    KP("KP"),ATTACK("Angriff"),DEFENCE("Verteidigung"),SPECIAL_ATTACK("Spezial Angriff"),SPECIAL_DEFENCE("Spezial Verteidigung"),INITIATIVE("Initiative");
    
    private String name;
    
    private AttributeType(String name)
    {
        this.name=name;
    }
    
    public String getName()
    {
        return name;
    }
}
