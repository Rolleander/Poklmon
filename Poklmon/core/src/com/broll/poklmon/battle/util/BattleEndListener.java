package com.broll.poklmon.battle.util;

public interface BattleEndListener {

	//won!
	public void battleWon();
	
	//game over
	public void battleLost();
	
	//use for escape 
	public void battleEnd();
	
}
