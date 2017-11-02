package com.broll.poklmon.map.areas;

import com.broll.poklmon.map.areas.util.WildPoklmonEntry;

import java.util.ArrayList;
import java.util.List;

public class MapArea {

	private List<WildPoklmonEntry> wildPoklmons = new ArrayList<WildPoklmonEntry>();
	private List<WildPoklmonEntry> fishingPoklmons = new ArrayList<WildPoklmonEntry>();
	private AreaType type = AreaType.GRASS;
	private boolean isWater;

	public void addWildPoklmon(WildPoklmonEntry entry) {
		wildPoklmons.add(entry);
	}
	
	public void addFishingPoklmon(WildPoklmonEntry entry) {
		fishingPoklmons.add(entry);
	}
	
	public List<WildPoklmonEntry> getWildPoklmons() {
		return wildPoklmons;
	}
	
	public List<WildPoklmonEntry> getFishingPoklmons() {
		return fishingPoklmons;
	}

	public void setType(AreaType type) {
		this.type = type;
	}

	public AreaType getType() {
		return type;
	}

	public void setWater(boolean isWater) {
		this.isWater = isWater;
	}
	
	public boolean isWater() {
		return isWater;
	}
}
