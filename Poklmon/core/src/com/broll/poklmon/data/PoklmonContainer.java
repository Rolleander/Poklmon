package com.broll.poklmon.data;

import java.util.List;

import com.broll.pokllib.poklmon.Poklmon;

public class PoklmonContainer
{
    
    private List<Poklmon> poklmons;
    

    public PoklmonContainer(List<Poklmon> poklmons)
    {
        this.poklmons=poklmons;
       
    }
    
    public Poklmon getPoklmon(int id)
    {
        if(id>-1&&id<poklmons.size())
        {
            return poklmons.get(id);
        }
        else
        {
            try
            {
                throw new DataException("False Poklmon ID! ("+id+")");
            }
            catch (DataException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    public int getPoklmonID(Poklmon p)
    {
    	return poklmons.indexOf(p);
    }
    
    public int getNumberOfPoklmons()
    {
        return poklmons.size();
    }
    
    public List<Poklmon> getPoklmons() {
		return poklmons;
	}
}
