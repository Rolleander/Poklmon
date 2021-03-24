package com.broll.poklmon.player.control;

import com.broll.pokllib.object.ObjectDirection;

public interface PlayerControlInterface {

	
	public void move(ObjectDirection direction);
	
	public void look(ObjectDirection direction);
	
	public void teleportTo(float x, float y);
	
	public void saveCurrentLocation(int currentMapNr);
}
