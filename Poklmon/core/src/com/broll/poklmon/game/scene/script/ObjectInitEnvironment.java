package com.broll.poklmon.game.scene.script;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.commands.InitCommands;
import com.broll.poklmon.game.scene.script.commands.PathingCommands;
import com.broll.poklmon.game.scene.script.commands.SystemCommands;
import com.broll.poklmon.game.scene.script.commands.VariableCommands;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.CharacterWorldState;

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
