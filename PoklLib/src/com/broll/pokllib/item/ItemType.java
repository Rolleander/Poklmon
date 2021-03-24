package com.broll.pokllib.item;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum ItemType
{

    BASIS_ITEM("Basis Item"),MEDICIN("Medizin"),POKLBALL("Poklball"),ATTACK("Angriff"),OTHER("Andere"),WEARABLE("Tragbar");
    
    private String name;
    
    private ItemType(String n)
    {
        name=n;
    }
    
    public String getName()
    {
        return name;
    }
    
}
