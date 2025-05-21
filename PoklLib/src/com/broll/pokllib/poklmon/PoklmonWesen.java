package com.broll.pokllib.poklmon;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum PoklmonWesen
{

    ERNST("Ernst"),KAUZIG("Kauzig"),ROBUST("Robust"),SANFT("Sanft"),ZAGHAFT("Zaghaft"),
    SOLO("Solo",AttributeType.ATTACK,AttributeType.DEFENCE),HART("Hart",AttributeType.ATTACK,AttributeType.SPECIAL_ATTACK),
    FRECH("Frech",AttributeType.ATTACK,AttributeType.SPECIAL_DEFENCE),MUTIG("Mutig",AttributeType.ATTACK,AttributeType.INITIATIVE),
    KUEHN("Kühn",AttributeType.DEFENCE,AttributeType.ATTACK),PFIFFIG("Pfiffig",AttributeType.DEFENCE,AttributeType.SPECIAL_ATTACK),
    LASCH("Lasch",AttributeType.DEFENCE,AttributeType.SPECIAL_DEFENCE),LOCKER("Locker",AttributeType.DEFENCE,AttributeType.INITIATIVE),
    MAESSIG("Mäßig",AttributeType.SPECIAL_ATTACK,AttributeType.ATTACK),MILD("Mild",AttributeType.SPECIAL_ATTACK,AttributeType.DEFENCE),
    HITZIG("Hitzig",AttributeType.SPECIAL_ATTACK,AttributeType.SPECIAL_DEFENCE),RUHIG("Ruhig",AttributeType.SPECIAL_ATTACK,AttributeType.INITIATIVE),
    STILL("Still",AttributeType.SPECIAL_DEFENCE,AttributeType.ATTACK),ZART("Zart",AttributeType.SPECIAL_DEFENCE,AttributeType.DEFENCE),
    SACHT("Sacht",AttributeType.SPECIAL_DEFENCE,AttributeType.SPECIAL_ATTACK),FORSCH("Forsch",AttributeType.SPECIAL_DEFENCE,AttributeType.INITIATIVE),
    SCHEU("Scheu",AttributeType.INITIATIVE,AttributeType.ATTACK),HASTIG("Hastig",AttributeType.INITIATIVE,AttributeType.DEFENCE),
    FROH("Froh",AttributeType.INITIATIVE,AttributeType.SPECIAL_ATTACK),NAIV("Naiv",AttributeType.INITIATIVE,AttributeType.SPECIAL_DEFENCE);
    
    private String name;
    private AttributeType typeInc;
    private AttributeType typeDec;
    
    private PoklmonWesen(String name)
    {
        this.name=name;
        typeInc=null;
        typeDec=null;
    }
    
    private PoklmonWesen(String name, AttributeType inc, AttributeType dec)
    {
        this.name=name;
        typeInc=inc;
        typeDec=dec;
    }
    
    public String getName()
    {
        return name;
    }
    
    public AttributeType getTypeDec()
    {
        return typeDec;
    }
    
    public AttributeType getTypeInc()
    {
        return typeInc;
    }
    
}
