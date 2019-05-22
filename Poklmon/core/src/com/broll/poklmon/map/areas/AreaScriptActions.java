package com.broll.poklmon.map.areas;

import com.broll.poklmon.map.areas.util.WildPoklmonEntry;

public class AreaScriptActions  {

	private MapArea area;

	public AreaScriptActions(MapArea area) {
		this.area = area;
	}
	
	public void addWildPoklmon(int poklId, double chance, int min, int max)
	{
		WildPoklmonEntry entry = new WildPoklmonEntry(poklId, chance, min, max);
		area.addWildPoklmon(entry);
	}
	
	public void addFishingPoklmon(int poklId,double chance,  int min, int max)
	{
		WildPoklmonEntry entry = new WildPoklmonEntry(poklId, chance, min, max);
		area.addFishingPoklmon(entry);
	}
	
	public void setType(AreaType type){
		area.setType(type);
	}
	
	public void setWater(){
		area.setType(AreaType.WATER);
		area.setWater(true);
	}
}
