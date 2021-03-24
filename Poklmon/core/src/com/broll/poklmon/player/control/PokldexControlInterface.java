package com.broll.poklmon.player.control;

public interface PokldexControlInterface {

	public String getSeenTimestamp(int poklmonID);
	
	public boolean hasSeenPoklmon(int poklmonID);
	
	public boolean hasCachedPoklmon(int poklmonID);
	
	public int getCachedCount(int poklmonID);
	
	public int getDifferentPoklmonSeen();
	
	public int getDifferentPoklmonCached();
	
	public void foundNewPoklmon(int poklmonID);
	
	public void cachedPoklmon(int poklmonID);
}
