package com.broll.pokllib.poklmon;



public class AttributeCalculator
{

    
    public static int calcAttribute(int base, int level, int DV, int FP, float wesen )
    {
        int wert=2*base;
        wert+=DV;
        wert+=(int)((float)FP/4f);
        wert=(int)(wert*level/100f)+5;
        wert=(int)(wert*wesen);
        return wert;
    }
    
    public static int calcKP(int base, int level, int DV, int FP)
    {
        int wert=2*base;
        wert+=DV;
        wert+=(int)((float)FP/4f);
        wert+=100;
        wert=(int)(wert*((float)level/100f));
        wert+=10;
        return wert;
    }
    
    public static float getWesenFactor(AttributeType attribute, PoklmonWesen wesen)
    {
        AttributeType typeInc = wesen.getTypeInc();
        AttributeType typeDec = wesen.getTypeDec();
        if(typeInc==attribute)
        {
           return 1.1f;
        }
        if(typeDec==attribute)
        {
           return 0.9f;
        }
        return 1;        
    }
}
