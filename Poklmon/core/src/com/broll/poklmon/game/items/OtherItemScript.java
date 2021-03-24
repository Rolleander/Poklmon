package com.broll.poklmon.game.items;

import com.broll.poklmon.game.scene.script.commands.DialogCommands;
import com.broll.poklmon.game.scene.script.commands.MenuCommands;
import com.broll.poklmon.game.scene.script.commands.PlayerCommands;
import com.broll.poklmon.game.scene.script.commands.SystemCommands;
import com.broll.poklmon.game.scene.script.commands.VariableCommands;

public interface OtherItemScript {

	public void reclaimItem();
	
	public PlayerCommands getPlayerCmd();
	
	public SystemCommands getSystemCmd();
	
	public DialogCommands getDialogCmd();
	
	public VariableCommands getVariableCmd();
	
	public MenuCommands getMenuCmd();
	
}
