package com.broll.poklmon.save;

import java.util.HashMap;

public class PokldexData 
{

    private HashMap<Integer, PokldexEntry> pokldex=new HashMap<Integer,PokldexEntry>();
    
    public PokldexData()
    {
        
    }
    
    public HashMap<Integer, PokldexEntry> getPokldex()
    {
        return pokldex;
    }
    
    
}
