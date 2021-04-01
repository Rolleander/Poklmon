package com.broll.poklmon.game.items;

import com.broll.poklmon.script.commands.BattleCommands;
import com.broll.poklmon.script.commands.DialogCommands;
import com.broll.poklmon.script.commands.MenuCommands;
import com.broll.poklmon.script.commands.PlayerCommands;
import com.broll.poklmon.script.commands.SystemCommands;
import com.broll.poklmon.script.commands.VariableCommands;

public interface BasisItemScript {
	
	public PlayerCommands getPlayerCmd();
	
	public SystemCommands getSystemCmd();
	
	public DialogCommands getDialogCmd();
	
	public VariableCommands getVariableCmd();
	
	public MenuCommands getMenuCmd();
	
	public BattleCommands getBattleCmd();
	
}
