package com.broll.poklmon.map;

import com.broll.pokllib.object.ObjectDirection;

public class MapTile
{

    private int areaID;
    private int tileID;
    private boolean blocked=false;
    private ObjectDirection ledge;

    public MapTile(int tile, int area)
    {
        this.tileID = tile;
        this.areaID = area;
    }
    
    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }
    
    public void setLedge(ObjectDirection ledge) {
		this.ledge = ledge;
	}

    public void update(int tile, int area)
    {
        this.tileID = tile;
        this.areaID = area;
    }

    public boolean isBlocked()
    {
        return areaID == 1||blocked;
    }

    public boolean hasScript()
    {
        return areaID > 1;
    }

    public int getScriptID()
    {
        return areaID;
    }

    public int getTileID()
    {
        return tileID;
    }

    public ObjectDirection getLedge() {
		return ledge;
	}
}
