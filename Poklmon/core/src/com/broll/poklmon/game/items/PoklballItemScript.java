package com.broll.poklmon.game.items;

import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.script.commands.VariableCommands;

public interface PoklballItemScript {

	public void setBallStrength(float strength);
	
	public void setCatchAlways(boolean always);
	
	public void setBallIcon(int nr);
	
	public WildPoklmon getTarget();
	
	public PlayerPoklmon getActivePoklmon();

	VariableCommands getVariableCmd();
	
}
