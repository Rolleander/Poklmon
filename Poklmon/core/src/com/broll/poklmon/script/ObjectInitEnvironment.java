package com.broll.poklmon.script;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.script.commands.InitCommands;
import com.broll.poklmon.script.commands.PathingCommands;
import com.broll.poklmon.script.commands.VariableCommands;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.CharacterWorldState;
import com.broll.poklmon.script.commands.SystemCommands;

public class ObjectInitEnvironment extends ScriptingEnvironment {

	private InitCommands initCommands;

	@Override
	protected void addControllers(GameManager game, MapObject caller) {
		initCommands = new InitCommands(game);
		addController(caller, "self");
		addController(initCommands, "init");
		addController(new VariableCommands(game), "game");
		addController(new PathingCommands(game), "path");
		addController(new SystemCommands(game), "system");
	}

	@Override
	protected void addImports() {
		addPackage(ObjectDirection.class);
		addPackage(CharacterWorldState.class);

	}

	public InitCommands getInitCommands() {
		return initCommands;
	}
}
