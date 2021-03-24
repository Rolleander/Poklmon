package com.broll.poklmon.game.items;

import com.broll.poklmon.game.scene.script.commands.DialogCommands;
import com.broll.poklmon.game.scene.script.commands.MenuCommands;
import com.broll.poklmon.game.scene.script.commands.PlayerCommands;
import com.broll.poklmon.game.scene.script.commands.SystemCommands;
import com.broll.poklmon.game.scene.script.commands.VariableCommands;

public interface AttackItemScript {

	
	public void standardLearnAttack(int id);
	
	public PlayerCommands getPlayerCmd();
	
	public SystemCommands getSystemCmd();
	
	public DialogCommands getDialogCmd();
	
	public VariableCommands getVariableCmd();
	
	public MenuCommands getMenuCmnd();
	
	public void reclaimItem();
}
