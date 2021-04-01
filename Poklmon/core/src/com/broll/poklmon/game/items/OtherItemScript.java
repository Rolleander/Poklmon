package com.broll.poklmon.game.items;

import com.broll.poklmon.script.commands.DialogCommands;
import com.broll.poklmon.script.commands.MenuCommands;
import com.broll.poklmon.script.commands.PlayerCommands;
import com.broll.poklmon.script.commands.SystemCommands;
import com.broll.poklmon.script.commands.VariableCommands;

public interface OtherItemScript {

	public void reclaimItem();
	
	public PlayerCommands getPlayerCmd();
	
	public SystemCommands getSystemCmd();
	
	public DialogCommands getDialogCmd();
	
	public VariableCommands getVariableCmd();
	
	public MenuCommands getMenuCmd();
	
}
