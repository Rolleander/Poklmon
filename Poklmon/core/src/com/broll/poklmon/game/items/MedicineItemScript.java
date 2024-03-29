package com.broll.poklmon.game.items;

import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.script.commands.VariableCommands;

public interface MedicineItemScript {
	
	public void heal(int kp);
	
	public void healPercent(double percent);
	
	public boolean isFullHealth();
	
	public boolean isFainted();
	
	public boolean hasStatusChange();
	
	public MainFightStatus getStatus();
	
	public void healStatus();
	
	public boolean hasFullAp();
	
	public void giveApToSingleAttack(int ap);
	
	public void giveFullApToSingleAttack();
	
	public void giveApToAll(int ap);
	
	public void giveFullApToAll();
	
	public void cancel();

	VariableCommands getVariableCmd();
}
